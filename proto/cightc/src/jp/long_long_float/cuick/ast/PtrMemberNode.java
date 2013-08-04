package jp.long_long_float.cuick.ast;

public class PtrMemberNode extends LHSNode {

    private ExprNode expr;
    private String member;
    
    public PtrMemberNode(ExprNode expr, String member) {
        this.expr = expr;
        this.member = member;
    }
    
    @Override
    public Location location() {
        return expr.location();
    }

    @Override
    protected void _dump(Dumper d) {
        //if (type != null)  d.printMember("type", type);
        d.printMember("expr", expr);
        d.printMember("member", member);
    }

}
