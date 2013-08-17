package jp.long_long_float.cuick.ast;

public class SuffixOpNode extends UnaryArithmeticOpNode {
    public SuffixOpNode(String op, ExprNode expr) {
        super(op, expr);
    }
    
    @Override
    public String toString() {
        return expr + operator;
    }

    public ExprNode expr() {
        return expr;
    }
}
