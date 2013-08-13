package jp.long_long_float.cuick.type;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.ast.Location;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    
    public String typeString() {
        return StringUtils.join(basicTypes, " ");
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)) return false;
        if(obj instanceof BasicType) {
            BasicType other = (BasicType)obj;
            boolean ret = new EqualsBuilder()
                    .append(basicTypes, other.basicTypes)
                    .isEquals();
            //System.out.println(toString() + (ret ? " == " : " != ") + other.toString());
            return ret;
        }
        else {
            return false;
        }
    }
}
