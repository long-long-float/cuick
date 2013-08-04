package jp.long_long_float.cuick.parser;

import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.Location;
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
    protected void _dump(Dumper d) {
        d.printMember("operator", operator);
        d.printMember("left", left);
        d.printMember("right", right);
    }

}
