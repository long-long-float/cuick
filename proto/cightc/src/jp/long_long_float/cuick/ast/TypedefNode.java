package jp.long_long_float.cuick.ast;

public class TypedefNode{
    protected String real;
    protected String name;
    
    public TypedefNode(Location loc, String real, String name) {
        this.real = real;
        this.name = name;
    }
}
