package jp.long_long_float.cuick.ast;

public class AbstractAssignNode extends ExprNode {

    ExprNode lhs, rhs;
    
    public AbstractAssignNode(ExprNode lhs, ExprNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    
    @Override
    public Location location() {
        return lhs.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("lhs", lhs);
        d.printMember("rhs", rhs);
    }

}
