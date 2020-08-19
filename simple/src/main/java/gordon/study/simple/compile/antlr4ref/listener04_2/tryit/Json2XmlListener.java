package gordon.study.simple.compile.antlr4ref.listener04_2.tryit;

import gordon.study.simple.compile.antlr4ref.listener04_2.JSONBaseListener;
import gordon.study.simple.compile.antlr4ref.listener04_2.JSONParser;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 * "description" : "An imaginary server config file",
 * "logs" : {"level":"verbose", "dir":"/var/log"},
 * "host" : "antlr.org",
 * "admin": ["parrt", "tombu"],
 * "aliases": []
 * }
 *
 * <description>An imaginary server config file</description>
 * <logs>
 * <level>verbose</level>
 * <dir>/var/log</dir>
 * </logs>
 * <host>antlr.org</host>
 * <admin>
 * <element>parrt</element>
 * <element>tombu</element>
 * </admin>
 * <aliases></aliases>
 */
public class Json2XmlListener extends JSONBaseListener {

    private Map<Object, String> map = new HashMap();

    private String xml;

    public String getXml() {
        return xml;
    }

    @Override
    public void exitJson(JSONParser.JsonContext ctx) {
        xml = map.get(ctx.getChild(0));
    }

    @Override
    public void exitAnObject(JSONParser.AnObjectContext ctx) {
        StringBuilder sb = new StringBuilder();
        ctx.getRuleContexts(JSONParser.PairContext.class).stream().forEach(pair -> { //// use ctx.pair() instead
            sb.append(map.get(pair)).append("\n");
        });
        map.put(ctx, sb.toString());
    }

    @Override
    public void exitEmptyObject(JSONParser.EmptyObjectContext ctx) {
        map.put(ctx, "");
    }

    @Override
    public void exitObjectValue(JSONParser.ObjectValueContext ctx) {
        map.put(ctx, "\n" + map.get(ctx.getChild(0))); //// use ctx.object() instead
    }

    @Override
    public void exitArrayOfValues(JSONParser.ArrayOfValuesContext ctx) {
        StringBuilder sb = new StringBuilder();
        ctx.getRuleContexts(JSONParser.ValueContext.class).stream().forEach(value -> { //// use ctx.pair() instead
            sb.append("<element>").append(map.get(value)).append("</element>\n");
        });
        map.put(ctx, sb.toString());
    }

    @Override
    public void exitEmptyArray(JSONParser.EmptyArrayContext ctx) {
        map.put(ctx, "");
    }

    @Override
    public void exitArrayValue(JSONParser.ArrayValueContext ctx) {
        map.put(ctx, "\n" + map.get(ctx.getChild(0))); //// use ctx.array() instead
    }

    @Override
    public void exitPair(JSONParser.PairContext ctx) {
        String tag = ctx.STRING().getText();
        tag = tag.substring(1, tag.length() - 1);
        map.put(ctx, String.format("<%s>%s</%s>", tag, map.get(ctx.value()), tag));
    }

    @Override
    public void exitString(JSONParser.StringContext ctx) {
        if (ctx.getRuleIndex() == JSONParser.RULE_value) {
            String value = ctx.getText();
            value = value.substring(1, value.length() - 1);
            map.put(ctx, value);
        }
    }

    @Override
    public void exitAtom(JSONParser.AtomContext ctx) {
        map.put(ctx, ctx.getText());
    }


}
