package jp.long_long_float.cuick.ast;

public class ReturnNode extends StmtNode {

    protected ExprNode expr;
    
    public ReturnNode(Location loc, ExprNode expr) {
        super(loc);
        this.expr = expr;
    }

    @Override
    protected void _dump(Dumper d) {
    }

}
