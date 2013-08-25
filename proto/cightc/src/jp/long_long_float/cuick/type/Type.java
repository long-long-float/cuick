package jp.long_long_float.cuick.type;

import java.util.ArrayList;
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
    private List<Type> templTypes = new ArrayList<Type>();
    
    private Type child;
    
    public Type(Location location) {
        this.location = location;
    }
    
    public Location location() {
        return location;
    }

    public void setChild(Type child) {
        this.child = child;
    }
    
    public Type getDeepestChild() {
        return child != null ? child.getDeepestChild() : this;
    }
    
    public Type setReference() {
        Type ret = this.clone();
        ret.isReference = true;
        return ret;
    }
    
    public Type increasePointer() {
        Type ret = this.clone();
        ret.pointerCount++;
        return ret;
    }
    
    public Type decreasePointer() {
        if(!isPointer()) {
            throw new RuntimeException("This is not a pointer!");
        }
        Type ret = this.clone();
        ret.pointerCount--;
        return ret;
    }
    
    @Override
    public Type clone() {
        Type ret = null;
        try {
            ret = (Type)super.clone();
            if(child != null) ret.child = child.clone();
        }
        catch(CloneNotSupportedException ex) {
        }
        return ret;
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
        if(templTypes.size() != 0) {
            ret += "<" + StringUtils.join(templTypes, ", ") + ">";
        }
        return ret + TextUtils.times("*", pointerCount) + (isReference ? "&" : "") + (child != null ? "::" + child : "");
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
