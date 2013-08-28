package jp.long_long_float.cuick.type;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.ast.Location;
import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
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

    public Type setChild(Type child) {
        this.child = child;
        return this;
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
    
    public Type decreasePointer() throws DecreasePointerException {
        if(!isPointer()) {
            throw new DecreasePointerException("This is not a pointer!");
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

    public Type setTemplateTypes(List<Type> templTypes) {
        this.templTypes = templTypes;
        return this;
    }
    
    abstract public String typeString();
    
    @Override
    public String toString() {
        String ret = (isStatic ? "static " : "") + typeString();
        if(templTypes.size() != 0) {
            ret += "<" + StringUtils.join(templTypes, ", ") + ">";
        }
        return ret + /*TextUtils.times("*", pointerCount) + (isReference ? "&" : "") +*/ (child != null ? "::" + child : "");
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

    //FIXME 一般化する
    
    private boolean isStatic;
    
    public Type setIsStatic(boolean b) {
        isStatic = b;
        return this;
    }
}
