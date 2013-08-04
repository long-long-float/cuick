package jp.long_long_float.cuick.ast;


public class PrefixOpNode extends UnaryArithmeticOpNode {
    public PrefixOpNode(String op, ExprNode expr) {
        super(op, expr);
    }
}
