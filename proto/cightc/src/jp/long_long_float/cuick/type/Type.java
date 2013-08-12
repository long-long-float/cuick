package jp.long_long_float.cuick.type;

import java.util.List;

import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.utility.TextUtils;

import org.apache.commons.lang3.StringUtils;
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

    public void setReference() {
        isReference = true;
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

    public void addPointer() {
        pointerCount++;
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
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        
        Type other = (Type)obj;
        boolean ret = true;
        ret &= (isReference == other.isReference);
        ret &= (pointerCount == other.pointerCount);
        ret &= ((templTypes == null && templTypes == other.templTypes)
                ||(templTypes != null && templTypes.equals(other.templTypes)));
        return ret;
    }
}
