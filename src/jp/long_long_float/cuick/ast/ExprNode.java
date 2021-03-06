package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.type.Type;

public abstract class ExprNode extends Node {

    //式の型
    private Type type;
    
    private boolean isSurrounded = false;
    
    public ExprNode() {
        super();
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
    @Override
    public void dump(Dumper d) {
        super.dump(d);
        d.printMember("type as node", type != null ? type : "???");
    }
    
    public Type type() {
        return type;
    }

    public boolean isSurrounded() {
        return isSurrounded;
    }
    
    public ExprNode setIsSurrounded(boolean b) {
        //TODO cloneする
        this.isSurrounded = b;
        return this;
    }

}
