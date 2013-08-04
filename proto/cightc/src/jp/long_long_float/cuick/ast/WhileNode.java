package jp.long_long_float.cuick.ast;

public class WhileNode extends StmtNode {
    
    protected ExprNode cond;
    protected StmtNode body;
    
    public WhileNode(Location loc, ExprNode c, StmtNode b) {
        super(loc);
        this.cond = c;
        this.body = b;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("cond", cond);
        d.printMember("body", body);
    }

}
