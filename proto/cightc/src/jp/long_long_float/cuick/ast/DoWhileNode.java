package jp.long_long_float.cuick.ast;

public class DoWhileNode extends StmtNode {
    
    protected ExprNode cond;
    protected StmtNode body;
    
    public DoWhileNode(Location loc, StmtNode b, ExprNode c) {
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
