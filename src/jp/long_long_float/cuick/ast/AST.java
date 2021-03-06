package jp.long_long_float.cuick.ast;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.entity.Entity;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.ToplevelScope;
import jp.long_long_float.cuick.entity.Variable;

public class AST extends Node{
    protected Location source;
    protected Declarations declarations;
    protected ToplevelScope toplevelScope;
    
    public AST(Location source, Declarations declarations) {
        super();
        this.source = source;
        this.declarations = declarations;
    }
    
    public List<Entity> definitions() {
        List<Entity> result = new ArrayList<Entity>();
        result.addAll(declarations.vars());
        result.addAll(declarations.funcs());
        return result;
    }
    
    public List<Variable> vars() {
        return declarations.vars();
    }
    
    public List<StmtNode> stmts() {
        return declarations.stmts();
    }
    
    public List<StmtNode> moveStmts() {
        List<StmtNode> ret = new ArrayList<StmtNode>(stmts());
        declarations.stmts().clear();
        return ret;
    }
    /*
    public void setStmt(List<StmtNode> stmts) {
        declarations.stmts = new LinkedHashSet<StmtNode>(stmts);
    }
    */
    public List<Function> funcs() {
        return declarations.funcs();
    }
    
    public void addFunction(Function func) {
        declarations.funcs().add(func);
    }
    
    public boolean isDefinedFunction(String name) {
        for(Function func : declarations.funcs()) {
            if(func.name().equals(name)) return true;
        }
        return false;
    }
    
    public List<AtCommandNode> atCommands() {
        return new ArrayList<AtCommandNode>(declarations.atCommands());
    }
    
    public List<SharpDirectiveNode> sharpDirectives() {
        return declarations.sharpDirectives();
    }
    
    public Location location() {
        return source;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printNodeList("vars", vars());
        d.printNodeList("funcs", funcs());
        d.printNodeList("stmts", stmts());
        d.printNodeList("atCommands", atCommands());
    }

    public void setScope(ToplevelScope scope) {
        if(toplevelScope != null) {
            //XXX
            //throw new Error("must not happen: ToplevelScope set twice");
        }
        toplevelScope = scope;
    }
}
