package gordon.study.simple.compile.antlr4ref.tour02_3;
/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class DataProcessor {
    public static void main(String[] args) throws Exception {
        DataLexer lexer = new DataLexer(CharStreams.fromFileName("C:\\study\\codebase\\github\\gordonklg\\study\\simple\\src\\main\\java\\gordon\\study\\simple\\compile\\antlr4ref\\tour02_3\\t.data"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DataParser parser = new DataParser(tokens);
        ParseTree tree = parser.file();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new DataListenerImpl(), tree);
    }
}
