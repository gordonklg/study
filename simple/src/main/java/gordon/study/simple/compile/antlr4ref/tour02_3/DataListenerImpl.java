package gordon.study.simple.compile.antlr4ref.tour02_3;

import gordon.study.simple.compile.antlr4ref.tour02_2.JavaParser;

public class DataListenerImpl extends DataBaseListener {

    int sum = 0;

    @Override
    public void enterGroup(DataParser.GroupContext ctx) {
        System.out.println("num: " + ctx.INT.getText());
    }

    @Override
    public void enterSequence(DataParser.SequenceContext ctx) {
        ctx.INT().stream().forEach(ele -> {
            int i = Integer.parseInt(ele.getText());
            System.out.print(i + " ");
            sum += i;
        });
    }

    @Override
    public void exitSequence(DataParser.SequenceContext ctx) {
        System.out.println("sum=" + sum);
        sum = 0;
    }
}
