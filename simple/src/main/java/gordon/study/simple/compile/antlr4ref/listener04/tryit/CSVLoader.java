package gordon.study.simple.compile.antlr4ref.listener04.tryit;

import gordon.study.simple.compile.antlr4ref.listener04.CSVLexer;
import gordon.study.simple.compile.antlr4ref.listener04.CSVParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class CSVLoader {

    public static void main(String[] args) throws Exception {
        CSVLexer lexer = new CSVLexer(CharStreams.fromFileName("C:\\study\\codebase\\github\\gordonklg\\study\\simple\\src\\main\\java\\gordon\\study\\simple\\compile\\antlr4ref\\listener04\\t.csv"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CSVParser parser = new CSVParser(tokens);
        ParseTree tree = parser.file();
        ParseTreeWalker walker = new ParseTreeWalker();
        CSVLoadToMapListener listener = new CSVLoadToMapListener();
        walker.walk(listener, tree);
        System.out.println(listener.getRecords());
    }
}
