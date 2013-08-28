package jp.long_long_float.cuick.entity;

import java.util.List;

import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.ast.MemorizeNode;
import jp.long_long_float.cuick.ast.TemplateNode;
import jp.long_long_float.cuick.ast.TypeNode;
import jp.long_long_float.cuick.type.FunctionType;
import jp.long_long_float.cuick.type.Type;

public class Function extends Entity{
    private Params params;
    private BlockNode body;
    private LocalScope scope;
    
    private MemorizeNode memorize;
    
    private Location location = null; //for extend
    
    private TemplateNode template;
    
    public Function(Type ret, String name, Params ps, BlockNode body, MemorizeNode memorize, TemplateNode template) {
        super(new TypeNode(new FunctionType(ret, ps != null ? ps.parametersType() : null)), name);
        this.params = ps;
        this.body = body;
        this.memorize = memorize;
        this.template = template;
        
        for(Parameter param : ps) {
            body.variables().add(param);
        }
    }
    
    //for extend
    public Function(Location location, Type ret, Type receiver, String name, Params ps, BlockNode body, TemplateNode template) {
        this(ret, name, ps, body, null, template);
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
    
    public MemorizeNode memorize() {
        return memorize;
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
        if(memorize != null) d.printMember("memorize", memorize);
        if(template != null) d.printMember("template", template);
    }

    public void setScope(LocalScope scope) {
        this.scope = scope;
    }

    public TemplateNode template() {
        return template;
    }
}
