package jp.long_long_float.cuick.type;

public class FunctionType extends Type{
    protected Type returnType;
    protected ParamTypes paramTypes;
    
    public FunctionType(Type ret, ParamTypes paramTypes) {
        this.returnType = ret;
        this.paramTypes = paramTypes;
    }
}
