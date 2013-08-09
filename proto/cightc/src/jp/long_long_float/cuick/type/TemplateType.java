package jp.long_long_float.cuick.type;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TemplateType extends Type {
    protected List<Type> templTypes;
    protected Type baseType;
    
    public TemplateType(List<Type> templTypes, Type baseType) {
        super(baseType.location());
        this.templTypes = templTypes;
        this.baseType = baseType;
    }
    
    public String toString() {
        return "" + baseType + "<" + StringUtils.join(templTypes, ", ") + ">";
    }
}
