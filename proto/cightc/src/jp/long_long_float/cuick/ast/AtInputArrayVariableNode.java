package jp.long_long_float.cuick.ast;

public class AtInputArrayVariableNode extends AtInputAbstractVariableNode {

    protected String name;
    protected RangeNode range = null;
    protected ExprNode expr = null;
    
    public AtInputArrayVariableNode(Location loc, String name, RangeNode range) {
        super(loc);
        this.name = name;
        this.range = range;
    }
    
    public AtInputArrayVariableNode(Location loc, String name, ExprNode expr) {
        super(loc);
        this.name = name;
        this.expr = expr;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("name", name);
        if(range != null) d.printMember("range", range);
        if(expr != null) d.printMember("expr", expr);
    }

}
