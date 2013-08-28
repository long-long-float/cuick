package jp.long_long_float.cuick.ast;

public class SizeofExprNode extends ExprNode {

    protected ExprNode expr;
    
    public SizeofExprNode(ExprNode expr) {
        this.expr = expr;
    }
    
    @Override
    public Location location() {
        return expr.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
    }

    public ExprNode expr() {
        return expr;
    }

}
