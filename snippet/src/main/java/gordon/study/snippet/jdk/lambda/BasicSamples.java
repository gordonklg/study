/**
 * https://blog.csdn.net/bibiboyx/article/details/84257741
 */
package gordon.study.snippet.jdk.lambda;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;


public class BasicSamples {

    private static List<Employee> employeeList = Lists.newArrayList();

    static {
        employeeList.add(Employee.builder().name("Matt").salary(5000).office("New York").build());
        employeeList.add(Employee.builder().name("Steve").salary(6000).office("London").build());
        employeeList.add(Employee.builder().name("Carrie").salary(20000).office("New York").build());
        employeeList.add(Employee.builder().name("Peter").salary(7000).office("New York").build());
        employeeList.add(Employee.builder().name("Pat").salary(8000).office("London").build());
        employeeList.add(Employee.builder().name("Tammy").salary(29000).office("Shanghai").build());
    }

    public static void main(String[] args) {
        //-------------------List的forEach--------------------
        //Java8之前操作List
        for (Employee employee : employeeList) {
            System.out.print(employee.getName() + " ");
        }
        System.out.println();

        //Java8 lambda遍历list
        employeeList.forEach(emp -> System.out.print(emp.getName() + " "));
        System.out.println();

        employeeList.forEach(emp -> {
            if (emp.getSalary() > 10000) {
                System.out.print(emp.getName() + " ");
            }
        });
        System.out.println();

        //先过滤
        employeeList.stream().filter(emp -> emp.getSalary() > 10000).forEach(emp -> System.out.print(emp.getName() + " "));
        System.out.println();

        //-------------------stream.collect & Collectors--------------------
        //Groupingby
        Map<String, List<Employee>> collect = employeeList.stream().collect(Collectors.groupingBy(emp -> emp.getOffice()));
        //Java8遍历map
        collect.forEach((key, value) -> System.out.print(key + ":" + value.size() + " "));
        System.out.println();

        //Java8 List转换Map
        Map<String, Employee> employeeMap = employeeList.stream().collect(Collectors.toMap(key -> key.getName(), value -> value));
        employeeMap.forEach((key, value) -> System.out.print(key + ":" + value.getOffice() + " "));
        System.out.println();

        //-------------------stream.map 得到一个映射后的stream--------------------
        //返回姓名列表
        List<String> names = employeeList.stream().map(employee -> employee.getName()).collect(Collectors.toList());
        names.forEach(name -> System.out.print(name + " "));
        System.out.println();

        //List转换为Set
        Set<String> officeSet = employeeList.stream().map(employee -> employee.getOffice()).distinct().collect(Collectors.toSet());
        System.out.println(officeSet);

        //List转换为Set，不distinct，结果一致
        officeSet = employeeList.stream().map(employee -> employee.getOffice()).distinct().collect(Collectors.toSet());
        System.out.println(officeSet);

        //-------------------stream.filter 得到一个过滤后的stream--------------------
        //统计办公室是New York的个数
        long officeCount = employeeList.stream().filter(employee -> employee.getOffice().equals("New York")).count();
        System.out.println(officeCount);

        //返回办公室地点是New York的一个员工
        Optional<Employee> oneMatchedEmployee = employeeList.stream().filter(employee -> employee.getOffice().equals("New York")).findAny();
        oneMatchedEmployee.ifPresent(emp -> System.out.println(emp.getName()));

        //返回办公室地点是New York的所有员工
        List<Employee> allMatchedEmployees = employeeList.stream().filter(employee -> employee.getOffice().equals("New York")).collect(Collectors.toList());
        allMatchedEmployees.forEach(emp -> System.out.print(emp.getName() + " "));
        System.out.println();

        //-------------------stream比较、排序、分页（skip、limit）--------------------
        //找出工资最高
        Optional<Employee> hightestSalary = employeeList.stream().max((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(hightestSalary);

        //更好的写法
        Optional<Employee> lowestSalary = employeeList.stream().min(Comparator.comparingInt(Employee::getSalary));
        System.out.println(lowestSalary);

        //按照工资的升序来列出员工信息
        List<Employee> sortEmployeeList = employeeList.stream().sorted(Comparator.comparingInt(Employee::getSalary)).collect(Collectors.toList());
        sortEmployeeList.forEach(emp -> System.out.print(emp.getSalary() + " "));
        System.out.println();

        //按照城市降序和名字降序列出员工信息（非常tricky！只要调一次 reversed，且必须放在thenComparing后面！！！）
        List<Employee> sortEmployeeByName = employeeList.stream()
                .sorted(Comparator.comparing(Employee::getOffice).thenComparing(Employee::getName).reversed())
                .collect(Collectors.toList());
        sortEmployeeByName.forEach(emp -> System.out.print(emp.getOffice() + ":" + emp.getName() + " "));
        System.out.println();

        //获取工资第2第3的员工信息
        List<Employee> topEmployeeList = employeeList.stream()
                .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                .skip(1)
                .limit(2)
                .collect(Collectors.toList());
        topEmployeeList.forEach(emp -> System.out.print(emp.getSalary() + " "));
        System.out.println();

        //-------------------stream.mapToInt (IntStream) and then stat (count, average, sum)--------------------
        //-------------------similar as stream.mapToLong, stream.mapToDouble--------------------
        //获取平均工资
        OptionalDouble averageSalary = employeeList.stream().mapToInt(employee -> employee.getSalary()).average();
        averageSalary.ifPresent(avg -> System.out.println("平均工资：" + avg));

        //New York总工资
        int totalSalaryByOffice = employeeList.stream().filter(employee -> employee.getOffice().equals("New York"))
                .mapToInt(employee -> employee.getSalary())
                .sum();
        System.out.println("New York办公室总工资:" + totalSalaryByOffice);

        //-------------------reduce--------------------
        //总工资
        int totalSalary = employeeList.stream().map(employee -> employee.getSalary()).reduce(0, Integer::sum);
        System.out.println("总工资:" + totalSalary);


        int maxSalary = employeeList.parallelStream().map(employee -> employee.getSalary()).reduce(0, (a, b) -> a > b ? a : b);
        System.out.println("最高工资:" + maxSalary);

        //-------------------stream其它方法--------------------
        //anyMatch
        boolean isMatch = employeeList.stream().anyMatch(employee -> employee.getOffice().equals("London"));
        System.out.println(isMatch);

        //返回所有salary大于6000
        boolean matched = employeeList.stream().allMatch(employee -> employee.getSalary() > 6000);
        System.out.println(matched);

    }

}


