package gordon.study.simple.compile.antlr4ref.starter01;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Test {

    public static void main(String[] args) throws Exception {
        ArrayInitLexer lexer = new ArrayInitLexer(CharStreams.fromString("{99,{1,3},8}"));
        ArrayInitParser parser = new ArrayInitParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.init();
        System.out.println(tree.toStringTree(parser));
    }
}
