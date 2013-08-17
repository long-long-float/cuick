package jp.long_long_float.cuick.ast;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.entity.Entity;
import jp.long_long_float.cuick.entity.Function;

public class AST extends Node{
    protected Location source;
    protected Declarations declarations;
    
    public AST(Location source, Declarations declarations) {
        super();
        this.source = source;
        this.declarations = declarations;
    }
    
    public List<Entity> definitions() {
        List<Entity> result = new ArrayList<Entity>();
        result.addAll(declarations.vars);
        result.addAll(declarations.funcs);
        return result;
    }
    
    public List<StmtNode> stmts() {
        return declarations.stmts();
    }
    
    public List<Function> funcs() {
        return declarations.funcs();
    }
    
    public Location location() {
        return source;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printNodeList("vars", declarations.vars());
        d.printNodeList("funcs", declarations.funcs());
        d.printNodeList("stmts", declarations.stmts());
        d.printNodeList("atCommands", declarations.atCommands());
    }
}
