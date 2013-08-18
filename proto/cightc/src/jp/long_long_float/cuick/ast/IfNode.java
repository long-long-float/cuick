package jp.long_long_float.cuick.ast;

public class IfNode extends StmtNode {
    
    protected ExprNode cond;
    protected StmtNode thenBody;
    protected StmtNode elseBody;
    
    public IfNode(Location loc, ExprNode c, StmtNode t, StmtNode e) {
        super(loc);
        this.cond = c;
        this.thenBody = t;
        this.elseBody = e;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("cond", cond);
        d.printMember("thenBody", thenBody);
        d.printMember("elseBody", elseBody);
    }

    public ExprNode cond() {
        return cond;
    }

    public StmtNode thenBody() {
        return thenBody;
    }

    public StmtNode elseBody() {
        return elseBody;
    }

}
