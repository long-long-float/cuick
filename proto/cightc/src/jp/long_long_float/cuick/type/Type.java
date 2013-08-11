package jp.long_long_float.cuick.type;

import java.util.List;

import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.utility.TextUtils;

import org.apache.commons.lang3.StringUtils;

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
        return typeString() + "<" + StringUtils.join(templTypes, ", ") + ">" + TextUtils.times("*", pointerCount) + (isReference ? "&" : "");
    }
}
