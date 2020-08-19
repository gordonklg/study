package gordon.study.simple.compile.antlr4ref.example03;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDotStringPattern {

    /**
     * I think
     * STRING      :   '"' ('\\"'|.)*? '"' ;
     * in DOT.g4 is wrong
     *
     * But it's right >_<
     */
    public static void main(String[] args) {
        // 先测试一下  *？  的行为
        Pattern p = Pattern.compile("a(a)*?a");
        aaa(p, "a");
        aaa(p, "aa");
        aaa(p, "aaa");
        System.out.println();

        // 解释下为啥有这么多 \ 符号
        // 我们想要的Pattern是   "...\"...\"..."，即  "(\"|.)*?"
        // Java正则表达式的 \ 需要转义为 \\，即 "(\\"|.)*?"   这是正则表达式层面的要求（显然，这是设计约束，如果Java正则用 / 转义，那么 \ 就不用转义了）
        // 我们用Java的字符串字面量定义时又要再一次转义，" -> \"  \ -> \\
        p = Pattern.compile("\"(\\\\\"|.)*?\"");
        aaa(p, "\"aaa\"");
        aaa(p, "a\"aaa\"a");
        aaa(p, "\"aa\\\"a\"");
        aaa(p, "\"aa\"a\"");
        System.out.println();

        // 上面测试结果居然是想要的，推测 (a|b) 优先匹配 a，只要中了 a 备选项，就不管 b 而进行下一轮（依然先尝试匹配 a）
        p = Pattern.compile("(ab|abc|ca|ba)*");
        aaa(p, "abc");
        aaa(p, "ababcababababababababababababababababababab");

        // 但是 matches 方法不是这个逻辑
        System.out.println(p.matcher("abc").matches()); // true
        // 可以想象这次匹配要回退重试多少次
        System.out.println(p.matcher("ababcababababababababababababababababababab").matches()); // true

        // 这是我认为正确的写法。当然，它必然正确，但是没必要。
        p = Pattern.compile("\"(\\\\\"|[^\"])*\""); // 即  "(\"|[^"])*?"
        aaa(p, "\"aaa\"");
        aaa(p, "a\"aaa\"a");
        aaa(p, "\"aa\\\"a\"");
        aaa(p, "\"aa\"a\"");
        System.out.println();

        // 而如果反过来，就错了
        p = Pattern.compile("\"([^\"]|\\\\\")*\""); // 即  "([^"]|\")*?"
        aaa(p, "\"aa\\\"a\"");
    }

    private static void aaa(Pattern p, String s) {
        aaa(p, s, 0);
    }

    private static void aaa(Pattern p, String s, int group) {
        Matcher m = p.matcher(s);
        if (m.find()) {
            System.out.println(s + " matches " + p.pattern() + " by " + m.group(group));
        } else {
            System.out.println(s + " does not match " + p.pattern());
        }
    }
}
