package jp.long_long_float.cuick.ast;


public class CondExprNode extends ExprNode {
    protected ExprNode cond, thenExpr, elseExpr;
    
    public CondExprNode(ExprNode cond, ExprNode t, ExprNode e) {
        super();
        this.cond = cond;
        this.thenExpr = t;
        this.elseExpr = e;
    }
    
    @Override
    public Location location() {
        return cond.location();
    }
    
    @Override
    public String toString() {
        return "" + cond + " ? " + thenExpr + " : " + elseExpr;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("cond", cond);
        d.printMember("thenExpr", thenExpr);
        d.printMember("elseExpr", elseExpr);
    }

}
