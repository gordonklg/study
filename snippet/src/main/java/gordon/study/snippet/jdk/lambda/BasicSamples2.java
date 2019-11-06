/**
 * https://blog.csdn.net/ZYC88888/article/details/90377010
 *
 * 主要关于 flatMap方法。
 */
package gordon.study.snippet.jdk.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BasicSamples2 {

    public static void main(String[] args) {
        //////////////////// 想要得到所有单词中出现的字母列表
        String[] words = new String[]{"Hello", "World"};

        // 错误方式：map返回的流实际上是Stream<String[]> 类型的
        List<String[]> t1 = Arrays.stream(words).map(word -> word.split("")).distinct().collect(Collectors.toList());
        t1.forEach(System.out::print);
        System.out.println();

        // 正确方式：flatMap将多个Stream流合并成一个Stream流
        List<String> t2 = Arrays.stream(words).map(word -> word.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        t2.forEach(System.out::print);
        System.out.println();

        // 更新：实际上一次flatMap直接操作即可
        List<String> t3 = Arrays.stream(words).flatMap(word -> Arrays.stream(word.split(""))).distinct().collect(Collectors.toList());
        t3.forEach(System.out::print);
        System.out.println();

        //Demo1:给定数组，返回数组平方和
        //[1,2,3,4]=> 30  (1+4+9+16)
        Integer[] nums1 = {1, 2, 3, 4};
        int sum = Arrays.asList(nums1).stream().mapToInt(i -> i * i).sum();
        System.out.println(sum);

        //Demo2:给定两数组，返回数组对
        //[1,2,3],[3,4]=>[1,3],[1,4],[2,3],[2,4],[3,3],[3,4]
        Integer[] nums2 = {1, 2, 3};
        Integer[] nums3 = {3, 4};
        List<Integer> nums2List = Arrays.asList(nums2);
        List<Integer> nums3List = Arrays.asList(nums3);
        List<int[]> res2 = nums2List.stream().flatMap(i -> nums3List.stream().map(j -> new int[]{i, j})).collect(Collectors.toList());
        res2.forEach(i -> System.out.print(i[0] + "," + i[1] + " "));
        System.out.println();

        // 解释说明：map返回的流是Stream<Stream<int[]>> 类型的。Demo2的 flatMap返回的流就是Stream<int[]>的。
        long outerStreamSize = nums2List.stream().map(i -> nums3List.stream().map(j -> new int[]{i, j})).count();
        System.out.println(outerStreamSize);
        // 上面的流再 flatMap后，就和Demo2完全一致了。
        List<int[]> res2m = nums2List.stream().map(i -> nums3List.stream().map(j -> new int[]{i, j})).flatMap(i -> i).collect(Collectors.toList());
        res2m.forEach(i -> System.out.print(i[0] + "," + i[1] + " "));
        System.out.println();

        //Demo3:针对Demo2和Demo1组合返回总和能被3整除的数对
        //(2,4)和(3,3)是满足条件的
        List<int[]> res3 = nums2List.stream().flatMap(i -> nums3List.stream().filter(j -> (i + j) % 3 == 0).map(j -> new int[]{i, j})).collect(Collectors.toList());
        System.out.println(res3.size());
    }
}
