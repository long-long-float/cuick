package jp.long_long_float.cuick.type;

import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.compiler.Table;

public class NamedType extends Type {
    protected String name;
    
    public NamedType(String name, Location loc) {
        super(loc);
        this.name = name;
        
        Table.getInstance().entryTuple(this);
    }
    
    @Override
    public String typeString() {
        return name;
    }
}