package gordon.study.simple.compile.antlr4ref.listener04_2.tryit;

import gordon.study.simple.compile.antlr4ref.listener04_2.JSONLexer;
import gordon.study.simple.compile.antlr4ref.listener04_2.JSONParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Json2Xml {

    public static void main(String[] args) throws Exception {
        JSONLexer lexer = new JSONLexer(CharStreams.fromFileName("C:\\study\\codebase\\github\\gordonklg\\study\\simple\\src\\main\\java\\gordon\\study\\simple\\compile\\antlr4ref\\listener04_2\\t.json"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);
        ParseTree tree = parser.json();
        ParseTreeWalker walker = new ParseTreeWalker();
        Json2XmlListener listener = new Json2XmlListener();
        walker.walk(listener, tree);
        System.out.println(listener.getXml());
    }
}
