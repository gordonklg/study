package gordon.study.concurrent.geekbang.u28immutability;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ImmutabilitySample {

    private final List<String> students;

    public ImmutabilitySample(String... names) {
        this.students = new ArrayList();
        for (String s : names) {
            this.students.add(s);
        }
    }

    public List<String> getStudents() {
        return students;
    }

    public ImmutabilitySample addStudent(String name) {
        List<String> list = new LinkedList<>(students);
        list.add(name);
        return new ImmutabilitySample(list.toArray(new String[0]));
    }

    public static void main(String[] args) {
        ImmutabilitySample instance = new ImmutabilitySample("Linus", "Doug Lee");
        ImmutabilitySample instance2 = instance.addStudent("Dijikstra");
        System.out.println(instance2.getStudents());
        System.out.println(instance.getStudents()); // instance 不变
    }
}

