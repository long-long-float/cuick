package jp.long_long_float.cuick.type;

import org.apache.commons.lang3.StringUtils;

public class FunctionType extends Type{
    protected Type returnType;
    protected ParamTypes paramTypes;
    
    public FunctionType(Type ret, ParamTypes paramTypes) {
        super(ret.location());
        this.returnType = ret;
        this.paramTypes = paramTypes;
    }
    
    @Override
    public String typeString() {
        return "" + returnType + "(" + StringUtils.join(paramTypes, ", ") + ")";
    } 
}
