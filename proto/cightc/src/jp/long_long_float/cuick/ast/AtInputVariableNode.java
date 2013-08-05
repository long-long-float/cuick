package jp.long_long_float.cuick.ast;

public class AtInputVariableNode extends AtInputAbstractVariableNode {

    protected String name;
    protected boolean isZeroEnd;
    
    public AtInputVariableNode(Location loc, String name, boolean isZeroEnd) {
        super(loc);
        this.name = name;
        this.isZeroEnd = isZeroEnd;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("isZeroEnd", isZeroEnd);
    }

}
