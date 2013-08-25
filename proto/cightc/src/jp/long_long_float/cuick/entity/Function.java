package jp.long_long_float.cuick.entity;

import java.util.List;

import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.ast.TypeNode;
import jp.long_long_float.cuick.compiler.Table;
import jp.long_long_float.cuick.type.FunctionType;
import jp.long_long_float.cuick.type.Type;

public class Function extends Entity{
    protected Params params;
    protected BlockNode body;
    protected LocalScope scope;
    
    private Location location = null; //for extend
    
    public Function(Type ret, String name, Params ps, BlockNode body) {
        super(new TypeNode(new FunctionType(ret, ps != null ? ps.parametersType() : null)), name);
        this.params = ps;
        this.body = body;
        
        Table.getInstance().entryFunction(this);
    }
    
    //for extend
    public Function(Location location, Type ret, Type receiver, String name, Params ps, BlockNode body) {
        this(ret, name, ps, body);
        this.location = location;
        this.params.addParamFront(new Parameter(new TypeNode(receiver), "this"));
    }

    public Type returnType() {
        return ((FunctionType)type()).returnType();
    }
    
    public List<Parameter> parameters() {
        return params.parameters();
    }
    
    public BlockNode body() {
        return body;
    }
    
    @Override
    public Location location() {
        if(location == null) return super.location();
        else return location;
    }
    
    protected void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("params", params);
        d.printMember("body", body);
    }

    public void setScope(LocalScope scope) {
        this.scope = scope;
    }
}
