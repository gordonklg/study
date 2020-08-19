package gordon.study.simple.compile.antlr4ref.tour02_2; /***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class InsertSerialID {
    public static void main(String[] args) throws Exception {
        CharStream input = null;
        if (args.length > 0) {
            input = CharStreams.fromFileName(args[0]);
        } else {
            input = CharStreams.fromFileName("C:\\study\\codebase\\github\\gordonklg\\study\\simple\\src\\main\\java\\gordon\\study\\simple\\compile\\antlr4ref\\tour02_2\\Demo.java");
        }

        JavaLexer lexer = new JavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit(); // parse

        ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
        InsertSerialIDListener extractor = new InsertSerialIDListener(tokens);
        walker.walk(extractor, tree); // initiate walk of tree with listener

        // print back ALTERED stream
        System.out.println(extractor.rewriter.getText());
    }
}
