package jp.long_long_float.cuick.ast;

public class ArefNode extends LHSNode {
    private ExprNode expr, index;
    
    public ArefNode(ExprNode expr, ExprNode index) {
        this.expr = expr;
        this.index = index;
    }

    @Override
    public Location location() {
        return expr.location();
    }

    @Override
    protected void _dump(Dumper d) {
        //if(type != null) d.printMember("type", type);
        d.printMember("expr", expr);
        d.printMember("index", index);
    }

    public ExprNode index() {
        return index;
    }

    public ExprNode expr() {
        return expr;
    }
}
