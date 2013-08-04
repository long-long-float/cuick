package jp.long_long_float.cuick.type;

public class PointerType extends Type {
    protected Type baseType;
    
    public PointerType(Type baseType) {
        super(baseType.location());
        this.baseType = baseType;
    }
}
