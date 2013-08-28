package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.type.Type;

public class BinaryOpNode extends ExprNode {

    protected String operator;
    protected ExprNode left, right;
    protected Type type;
    
    public BinaryOpNode(ExprNode left, String op, ExprNode right) {
        super();
        this.left = left;
        this.operator = op;
        this.right = right;
    }
    
    @Override
    public Location location() {
        return left.location();
    }
    
    @Override
    public String toString() {
        return "" + left + " " + operator + " " + right;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("operator", operator);
        d.printMember("left", left);
        d.printMember("right", right);
    }

    public ExprNode left() {
        return left;
    }

    public ExprNode right() {
        return right;
    }

    public String operator() {
        return operator;
    }
}
