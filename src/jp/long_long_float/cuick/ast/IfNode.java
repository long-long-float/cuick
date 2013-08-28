package jp.long_long_float.cuick.ast;

public class IfNode extends StmtNode {
    
    protected ExprNode cond;
    protected StmtNode thenBody;
    protected StmtNode elseBody;
    
    private boolean isIf;
    
    public IfNode(Location loc, String name, ExprNode c, StmtNode t, StmtNode e) {
        super(loc);
        this.isIf = name.equals("if");
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
    
    public void setCond(ExprNode cond) {
        this.cond = cond;
    }

    public StmtNode thenBody() {
        return thenBody;
    }

    public StmtNode elseBody() {
        return elseBody;
    }

    public boolean isUnless() {
        return !isIf;
    }
}
