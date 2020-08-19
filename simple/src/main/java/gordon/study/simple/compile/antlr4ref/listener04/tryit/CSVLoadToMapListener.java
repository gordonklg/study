package gordon.study.simple.compile.antlr4ref.listener04.tryit;

import gordon.study.simple.compile.antlr4ref.listener04.CSVBaseListener;
import gordon.study.simple.compile.antlr4ref.listener04.CSVParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVLoadToMapListener extends CSVBaseListener {

    private List<Map<String, String>> records = new ArrayList<>();

    private List<String> heads = new ArrayList<>();

    private List<String> rowItems = new ArrayList<>();

    public List<Map<String, String>> getRecords() {
        return this.records;
    }

    @Override
    public void exitHdr(CSVParser.HdrContext ctx) {
        heads.addAll(rowItems);
    }

    @Override
    public void enterRow(CSVParser.RowContext ctx) {
        rowItems.clear();
    }

    @Override
    public void exitRow(CSVParser.RowContext ctx) {
        if (ctx.getParent().getRuleIndex() != CSVParser.RULE_hdr) {
            Map<String, String> rowMap = new LinkedHashMap<>();
            for (int i = 0; i < heads.size(); i++) {
                rowMap.put(heads.get(i), rowItems.get(i));
            }
            records.add(rowMap);
        }
    }

    @Override
    public void exitText(CSVParser.TextContext ctx) {
        rowItems.add(ctx.TEXT().getText());
    }

    @Override
    public void exitString(CSVParser.StringContext ctx) {
        rowItems.add(ctx.STRING().getText());
    }

    @Override
    public void exitEmpty(CSVParser.EmptyContext ctx) {
        rowItems.add("");
    }
}
