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

}