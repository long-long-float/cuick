package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.foreach.Enumerable;
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
    
    public void setEnumerable(Enumerable enume) {
        this.enumerable = enume;
    }
    
    public StmtNode lastBody() {
        return lastBody;
    }
    
    public StmtNode body() {
        return body;
    }
    
    public void setBody(StmtNode body) {
        this.body = body;
    }
    
    @Override
    protected void _dump(Dumper d) {
        if(var.typeNode() != null) d.printMember("var.type", var.type());
        d.printMember("var.name", var.name());
        d.printMember("isFore", isFore);
        d.printMember("enumerable", enumerable);
        d.printMember("body", body);
        d.printMember("lastBody", lastBody);
    }

}
