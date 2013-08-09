package jp.long_long_float.cuick.ast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.Variable;

public class Declarations {
    public Set<Variable> vars = new LinkedHashSet<Variable>();
    public Set<Function> funcs = new LinkedHashSet<Function>();
    public Set<StmtNode> stmts = new LinkedHashSet<StmtNode>();
    public Set<AtCommandNode> atCommands = new LinkedHashSet<AtCommandNode>();
    
    public void add(Declarations decls) {
        vars.addAll(decls.vars);
        funcs.addAll(decls.funcs);
        stmts.addAll(decls.stmts);
        atCommands.addAll(decls.atCommands);
    }
    
    public List<Variable> vars() {
        return new ArrayList<Variable>(vars);
    }
    
    public List<Function> funcs() {
        return new ArrayList<Function>(funcs);
    }
    
    public List<StmtNode> stmts() {
        return new ArrayList<StmtNode>(stmts);
    }
    
    public List<AtCommandNode> atCommands() {
        return new ArrayList<AtCommandNode>(atCommands);
    }
}
