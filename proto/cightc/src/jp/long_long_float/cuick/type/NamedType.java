package jp.long_long_float.cuick.type;

import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.compiler.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class NamedType extends Type {
    protected String name;
    
    public NamedType(String name, Location loc) {
        super(loc);
        this.name = name;
        
        if(name.equals("tuple")) Table.getInstance().entryTuple(this);
    }
    
    @Override
    public String typeString() {
        return name;
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)) return false;
        if(obj instanceof NamedType) {
            NamedType other = (NamedType)obj;
            boolean ret = new EqualsBuilder()
                    .append(name, other.name)
                    .isEquals();
            return ret;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean hasType(String typeStr) {
        return false;
    }
}
