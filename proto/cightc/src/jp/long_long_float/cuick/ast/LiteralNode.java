package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.type.Type;

public class LiteralNode extends ExprNode {
    protected Location location;
    protected TypeNode typeNode;
    protected String value;
    
    public LiteralNode(Location loc, Type type, String value) {
        super();
        this.location = loc;
        this.typeNode = new TypeNode(type);
        this.value = value;
    }
    
    @Override
    public Location location() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    protected void _dump(Dumper d) {
        // TODO 自動生成されたメソッド・スタブ

    }

}
