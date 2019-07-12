package gordon.study.snippet.jdk.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionalTest {
    public static void main(String[] args) {
        A a = new A();
        a.setS("aaa");
        a.setIa(new int[]{1, 2, 3});
        B b = new B("bbb");
        List<B> list = new ArrayList<>();
        list.add(b);
        a.setB(b);
        a.setList(list);

        A nul = new A();

        // of 方法构造 Optional，get 方法与普通调用行为一致（抛NPE）
        System.out.println(Optional.of(a.getB().getS()).get());
        try {
            System.out.println(Optional.of(nul.getB().getS()).get());
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + ". Message: " + e.getMessage());
        }
        try {
            System.out.println(nul.getB().getS());
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + ". Message: " + e.getMessage());
        }
        System.out.println();

        // ofNullable 方法构造 Optional，get 方法与普通调用行为一致（抛NPE）
        System.out.println(Optional.ofNullable(a.getS()).get());
        try {
            System.out.println(Optional.ofNullable(nul.getS()).get());
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + ". Message: " + e.getMessage());
        }
        System.out.println();

        // ofNullable 方法构造 Optional，orElse系列方法可以处理 null 情况
        System.out.println(Optional.ofNullable(a.getS()).orElse("nul"));
        System.out.println(Optional.ofNullable(nul.getS()).orElse("nul"));
        System.out.println(Optional.ofNullable(nul.getS()).orElseGet(a::getS));
        try {
            System.out.println(Optional.ofNullable(nul.getS()).orElseThrow(RuntimeException::new));
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + ". Message: " + e.getMessage());
        }
        System.out.println();

        // ofNullable 构造 Optional 本身也会抛 NPE
        try {
            System.out.println(Optional.ofNullable(nul.getB().getS()).orElse("nul"));
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + ". Message: " + e.getMessage());
        }
    }
}
