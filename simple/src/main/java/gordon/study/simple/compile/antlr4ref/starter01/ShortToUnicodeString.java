package gordon.study.simple.compile.antlr4ref.starter01;

public class ShortToUnicodeString extends ArrayInitBaseListener {
    @Override
    public void enterInit(ArrayInitParser.InitContext ctx) {
        if (ctx.parent == null) {
            System.out.print("\"");
        }
    }

    @Override
    public void exitInit(ArrayInitParser.InitContext ctx) {
        if (ctx.parent == null) {
            System.out.print("\"");
        }
    }

    @Override
    public void enterValue(ArrayInitParser.ValueContext ctx) {
        if (ctx.INT() != null) {
            int value = Integer.valueOf(ctx.INT().getText());
            System.out.printf("\\u%04x", value);
        }
    }

}
