package jp.long_long_float.cuick.ast;

public class RangeNode extends ExprNode {

    protected ExprNode lexpr, rexpr;
    protected String rangeStr;
    
    public RangeNode(ExprNode lexpr, String rangeStr, ExprNode rexpr) {
        this.lexpr = lexpr;
        this.rangeStr = rangeStr;
        this.rexpr = rexpr;
    }
    
    public ExprNode begin() {
        return lexpr;
    }
    
    public ExprNode end() {
        return rexpr;
    }
    
    public boolean isIncludingEnd() {
        return rangeStr == "..";
    }
    
    @Override
    public Location location() {
        return lexpr.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("lexpr", lexpr);
        d.printMember("rangeStr", rangeStr);
        d.printMember("rexpr", rexpr);
    }

}
