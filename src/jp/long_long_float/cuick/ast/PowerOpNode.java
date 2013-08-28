package jp.long_long_float.cuick.ast;

public class PowerOpNode extends BinaryOpNode {

    public PowerOpNode(ExprNode left, ExprNode right) {
        super(left, "**", right);
    }
    
    @Override
    public String toString() {
        return "std::pow((double)" + left + ", (double)" + right + ")";
    }
    
}
