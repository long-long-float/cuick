package jp.long_long_float.cuick.type;

import jp.long_long_float.cuick.ast.Location;

public class FunctionTemplateType extends NamedType {
    
    private boolean isTemplate = true;
    
    public FunctionTemplateType(String name, Location loc) {
        super(name, loc);
    }

}
