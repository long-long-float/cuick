package jp.long_long_float.cuick.type;



public class FunctionType extends Type{
    protected Type returnType;
    protected ParamTypes paramTypes;
    
    public FunctionType(Type ret, ParamTypes paramTypes) {
        super(ret.location());
        this.returnType = ret;
        this.paramTypes = paramTypes;
    }
    
    public Type returnType() {
        return returnType;
    }
    
    @Override
    public String typeString() {
        return returnType.toString();
    }

    @Override
    public boolean hasType(String typeStr) {
        return false;
    }
}
