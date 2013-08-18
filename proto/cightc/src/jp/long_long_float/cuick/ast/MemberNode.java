package jp.long_long_float.cuick.ast;

public class MemberNode extends LHSNode {

    private ExprNode expr;
    private String member;
    
    public MemberNode(ExprNode expr, String member) {
        this.expr = expr;
        this.member = member;
    }
    
    @Override
    public Location location() {
        return expr.location();
    }
    
    @Override
    public String toString() {
        return expr + "." + member;
    }

    @Override
    protected void _dump(Dumper d) {
        //if (type != null)  d.printMember("type", type);
        d.printMember("expr", expr);
        d.printMember("member", member);
    }

    public ExprNode expr() {
        return expr;
    }

}
