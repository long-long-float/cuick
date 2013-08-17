package jp.long_long_float.cuick.entity;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.ast.TypeNode;
import jp.long_long_float.cuick.compiler.Table;
import jp.long_long_float.cuick.cppStructure.CodeBuilder;
import jp.long_long_float.cuick.cppStructure.CodeBuilder.BlockCallback;

import org.apache.commons.lang3.StringUtils;

public class Function extends Entity{
    protected Params params;
    protected BlockNode body;
    protected LocalScope scope;
    
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
    
    /*
    public Params params() {
        return params;
    }
    */
    
    public List<Parameter> parameters() {
        return params.parameters();
    }
    
    public BlockNode body() {
        return body;
    }
    
    public Location location() {
        if(loc == null) return super.location();
        else return loc;
    }
    
    @Override
    public String toString() {
        CodeBuilder cb = new CodeBuilder();
        List<String> args = new ArrayList<String>();
        if(params != null)  {
            for(Parameter param : params) args.add(param.toString());
        }
        cb.addLine(type() + " " + name + "(" + StringUtils.join(args, ", ") + ")");
        cb.block(new BlockCallback() {
            @Override
            public void call(CodeBuilder cb) {
                for(StmtNode stmt : body.stmts()) {
                    cb.addLine(stmt.toString());
                }
            }
        }, false);
        return cb.toString();
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
