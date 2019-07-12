package gordon.study.snippet.jdk.optional;

import lombok.Data;

import java.util.List;

@Data
public class A {
    private String s;
    private int[] ia;
    private B b;
    private List<B> list;
}
