package gordon.study.simple.compile.antlr4ref.starter01;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Translate {

    public static void main(String[] args) {
        ArrayInitLexer lexer = new ArrayInitLexer(CharStreams.fromString("{99,{1,3},8}"));
        ArrayInitParser parser = new ArrayInitParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.init();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new ShortToUnicodeString(), tree);
    }
}
