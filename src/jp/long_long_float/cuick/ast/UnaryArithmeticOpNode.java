package jp.long_long_float.cuick.ast;

public class UnaryArithmeticOpNode extends UnaryOpNode {
    protected long amount;
    
    public UnaryArithmeticOpNode(String op, ExprNode expr) {
        super(op, expr);
        amount = 1;
    }
}
