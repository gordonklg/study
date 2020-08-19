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

public class RowsColSelector {
    public static void main(String[] args) throws Exception {
        RowsLexer lexer = new RowsLexer(CharStreams.fromFileName("C:\\study\\codebase\\github\\gordonklg\\study\\simple\\src\\main\\java\\gordon\\study\\simple\\compile\\antlr4ref\\tour02_3\\t.rows"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        int col = Integer.valueOf(2);
        RowsParser parser = new RowsParser(tokens, col); // pass column number!
        parser.setBuildParseTree(false); // don't waste time bulding a tree
        parser.file(); // parse
    }
}
