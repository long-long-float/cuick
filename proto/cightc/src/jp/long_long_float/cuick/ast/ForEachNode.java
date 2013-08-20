package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.foreach.Enumerable;
import jp.long_long_float.cuick.type.BasicType;
import jp.long_long_float.cuick.type.Type;

public class ForEachNode extends StmtNode {

    //protected Pair<Type, String> var;
    protected Variable var;
    protected boolean isFore;
    protected Enumerable enumerable;
    protected StmtNode body;
    protected StmtNode lastBody;
    
    public ForEachNode(Location loc, Type type, String name, boolean isFore, Enumerable enume, StmtNode body, StmtNode lastBody) {
        super(loc);
        //TODO ここでtypeを確定しておく
        if(type == null) {
            //type = enume.typeInference();
            type = new BasicType("int", null);
        }
        this.var = new Variable(new TypeNode(type), name, null, false, null, null);
        this.isFore = isFore;
        this.enumerable = enume;
        this.enumerable.setForEachNode(this);
        this.body = body;
        this.lastBody = lastBody;
    }
    
    public Variable var() {
        return var;
    }
    
    public boolean isFore() {
        return isFore;
    }
    
    public Enumerable enumerable() {
        return enumerable;
    }
    
    public StmtNode lastBody() {
        return lastBody;
    }
    
    public StmtNode body() {
        return body;
    }
    
    @Override
    public String toString() {
        return enumerable.toString(this);
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("var", var);
        d.printMember("isFore", isFore);
        d.printMember("enumerable", enumerable);
        d.printMember("body", body);
        d.printMember("lastBody", lastBody);
    }

}
