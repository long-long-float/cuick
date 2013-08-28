package jp.long_long_float.cuick.ast;

public class AddressNode extends LHSNode {

    protected ExprNode expr;
    
    public AddressNode(ExprNode expr) {
        this.expr = expr;
    }
    
    public ExprNode expr() {
        return expr;
    }
    
    @Override
    public Location location() {
        return expr.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
    }

}
