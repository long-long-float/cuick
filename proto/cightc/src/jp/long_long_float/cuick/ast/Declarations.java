package jp.long_long_float.cuick.ast;

import java.util.LinkedHashSet;
import java.util.Set;

import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.Variable;

public class Declarations {
    public Set<Variable> vars = new LinkedHashSet<Variable>();
    public Set<Function> funcs = new LinkedHashSet<Function>();
    public Set<TypedefNode> typedefs = new LinkedHashSet<TypedefNode>();
    public Set<StmtNode> stmts = new LinkedHashSet<StmtNode>();
    
    public void add(Declarations decls) {
        vars.addAll(decls.vars);
        funcs.addAll(decls.funcs);
        typedefs.addAll(decls.typedefs);
        stmts.addAll(decls.stmts);
    }
}
