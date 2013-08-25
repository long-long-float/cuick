package jp.long_long_float.cuick.ast;

public class AtInputVariableNode extends AtInputAbstractVariableNode {

    protected boolean isZeroEnd;
    
    public AtInputVariableNode(Location loc, String name, boolean isZeroEnd) {
        super(loc, name);
        this.isZeroEnd = isZeroEnd;
    }
    
    public boolean isZeroEnd() {
        return isZeroEnd;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("name", varName());
        d.printMember("isZeroEnd", isZeroEnd);
    }

}
