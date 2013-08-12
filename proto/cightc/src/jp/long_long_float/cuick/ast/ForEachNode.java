package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.foreach.Enumerable;
import jp.long_long_float.cuick.type.Type;
import jp.long_long_float.cuick.utility.Pair;

public class ForEachNode extends StmtNode {

    protected Pair<Type, String> var;
    protected boolean isFore;
    protected Enumerable enumerable;
    protected StmtNode body;
    protected StmtNode lastBody;
    
    public ForEachNode(Location loc, Pair<Type, String> var, boolean isFore, Enumerable enume, StmtNode body, StmtNode lastBody) {
        super(loc);
        this.var = var;
        this.isFore = isFore;
        this.enumerable = enume;
        this.body = body;
        this.lastBody = lastBody;
    }
    
    public Type varType() {
        return var.getFirst();
    }
    
    public String varName() {
        return var.getSecond();
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
        d.printMember("var type", var.getFirst());
        d.printMember("var name", var.getSecond());
        d.printMember("isFore", isFore);
        d.printMember("enumerable", enumerable);
        d.printMember("body", body);
        d.printMember("lastBody", lastBody);
    }

}
