package jp.long_long_float.cuick.type;

import java.util.List;

import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.utility.TextUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class Type implements Cloneable{
    private Location location;
    private boolean isReference = false;
    private int pointerCount = 0;
    private List<Type> templTypes = null;
    
    public Type(Location location) {
        this.location = location;
    }
    
    public Location location() {
        return location;
    }

    public Type setReference() {
        isReference = true;
        return this;
    }
    
    @Override
    public Type clone() {
        Type ret;
        try {
            ret = (Type)super.clone();
        }
        catch(CloneNotSupportedException ex) {
            throw new RuntimeException("'clone' is not supported!");
        }
        return ret;
    }

    public Type addPointer() {
        pointerCount++;
        return this;
    }

    public void setTemplateTypes(List<Type> templTypes) {
        this.templTypes = templTypes;
    }
    
    public List<Type> getTemplateTypes() {
        return templTypes;
    }
    
    abstract public String typeString();
    
    @Override
    public String toString() {
        String ret = typeString();
        if(templTypes != null) {
            ret += "<" + StringUtils.join(templTypes, ", ") + ">";
        }
        return ret + TextUtils.times("*", pointerCount) + (isReference ? "&" : "");
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Type) {
            Type other = (Type)obj;
            boolean ret = new EqualsBuilder()
                    .append(isReference, other.isReference)
                    .append(pointerCount, other.pointerCount)
                    .append(templTypes, other.templTypes)
                    .isEquals();
            //System.out.println(toString() + (ret ? " == " : " != ") + other.toString());
            return ret;
        }
        else {
            return false;
        }
    }

    public boolean isPointer() {
        return pointerCount > 0;
    }
    
    public abstract boolean hasType(String typeStr);
}
