package jp.long_long_float.cuick.type;

public enum PrimitiveTypes {
    cint(new CInt());
    
    private Type type;
    
    private PrimitiveTypes(Type type) {
        this.type = type;
    }
    
    public Type type() {
        return type;
    }
}
