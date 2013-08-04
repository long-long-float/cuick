package jp.long_long_float.cuick.type;

public class ReferenceType extends Type {
    protected Type baseType;
    
    public ReferenceType(Type baseType) {
        super(baseType.location());
        this.baseType = baseType;
    }
}
