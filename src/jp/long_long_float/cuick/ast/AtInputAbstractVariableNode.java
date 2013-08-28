package jp.long_long_float.cuick.ast;

public abstract class AtInputAbstractVariableNode extends Node {

    protected Location location;

    private String varName;
    
    public AtInputAbstractVariableNode(Location loc, String varName) {
        this.location = loc;
        this.varName = varName;
    }
    
    public String varName() {
        return varName;
    }
    
    public VariableNode getVariableNode() {
        return new VariableNode(null, varName);
    }
    
    @Override
    public Location location() {
        return location;
    }
}
