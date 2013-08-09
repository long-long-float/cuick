package jp.long_long_float.cuick.type;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.ast.Location;

import org.apache.commons.lang3.StringUtils;

public class BasicType extends Type {
    protected List<String> basicTypes;
    
    public BasicType(List<String> basicTypes, Location loc) {
        super(loc);
        this.basicTypes = basicTypes;
    }
    
    public BasicType(String basicType, Location loc) {
        this(new ArrayList<String>(), loc);
        this.basicTypes.add(basicType);
    }
    
    public String toString() {
        return StringUtils.join(basicTypes, " ");
    }
}
