package jp.long_long_float.cuick.type;

import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.compiler.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

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
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
