package jp.long_long_float.cuick.ast;

public class ForNode extends StmtNode {

    protected StmtNode init;
    protected ExprNode cond;
    protected StmtNode incr;
    protected StmtNode body;
    
    public ForNode(Location loc, ExprNode init, ExprNode cond, ExprNode incr, StmtNode body) {
        super(loc);
        this.init = new ExprStmtNode(init.location(), init);
        this.cond = cond;
        this.incr = new ExprStmtNode(incr.location(), incr);
        this.body = body;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("init", init);
        d.printMember("cond", cond);
        d.printMember("incr", incr);
        d.printMember("body", body);
    }

}
