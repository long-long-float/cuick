package jp.long_long_float.cuick.ast;

public class ExprStmtNode extends StmtNode {

    protected ExprNode expr;
    
    public ExprStmtNode(Location loc, ExprNode expr) {
        super(loc);
        this.expr = expr;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
    }
    
    @Override
    public String toString() {
        return expr.toString() + ";";
    }

    public ExprNode expr() {
        return expr;
    }

    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

}
