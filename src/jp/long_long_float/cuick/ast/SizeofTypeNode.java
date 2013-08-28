package jp.long_long_float.cuick.ast;


public class SizeofTypeNode extends ExprNode {
    protected TypeNode operand;
    //protected TypeNode type;
    
    public SizeofTypeNode(TypeNode operand) {
        this.operand = operand;
    }
    
    @Override
    public Location location() {
        return operand.location();
    }

    @Override
    protected void _dump(Dumper d) {
    }

}
