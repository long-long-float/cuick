package jp.long_long_float.cuick.entity;

import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.TypeNode;

public class Variable extends Entity {
    
    public Variable(TypeNode type, String name) {
        super(type, name);
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("type", type());
        d.printMember("name", name);
    }

}
