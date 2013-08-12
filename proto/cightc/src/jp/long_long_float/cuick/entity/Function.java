package jp.long_long_float.cuick.entity;

import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.ast.TypeNode;
import jp.long_long_float.cuick.compiler.Table;

public class Function extends Entity{
    protected Params params;
    protected BlockNode body;
    
    private Location loc = null; //for extend
    
    public Function(TypeNode type, String name, Params params, BlockNode body) {
        super(type, name);
        this.params = params;
        this.body = body;
        
        Table.getInstance().entryFunction(this);
    }
    
    //for extend
    public Function(Location loc, TypeNode type, String name, Params params, BlockNode body) {
        this(type, name, params, body);
        this.loc = loc;
        for(StmtNode stmt : body.stmts()) {
            //stmt.replaceVariable("this", "this_");
        }
    }
    
    public Params params() {
        return params;
    }
    
    public BlockNode body() {
        return body;
    }
    
    public Location location() {
        if(loc == null) return super.location();
        else return loc;
    }
    
    protected void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("params", params);
        d.printMember("body", body);
    }
}
