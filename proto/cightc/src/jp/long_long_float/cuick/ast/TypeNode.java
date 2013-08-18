package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.type.Type;

public class TypeNode extends Node {

    Type type;
    
    public TypeNode(Type type) {
        super();
        this.type = type;
    }
    
    public Type type() {
        return type;
    }

    @Override
    public Location location() {
        return type.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("type", type);
    }

}
