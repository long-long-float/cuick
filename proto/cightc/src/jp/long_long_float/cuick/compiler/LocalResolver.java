package jp.long_long_float.cuick.compiler;

import java.util.List;
import java.util.Stack;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.entity.Entity;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.LocalScope;
import jp.long_long_float.cuick.entity.Scope;
import jp.long_long_float.cuick.entity.ToplevelScope;
import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class LocalResolver extends Visitor {
    private final Stack<Scope> scopeStack;
    private final ErrorHandler errorHandler;
    
    public LocalResolver(ErrorHandler h) {
        this.errorHandler = h;
        this.scopeStack = new Stack<Scope>();
    }
    
    public void resolve(AST ast) {
        ToplevelScope toplevel = new ToplevelScope();
        scopeStack.add(toplevel);
        
        for(Entity ent : ast.definitions()) {
            toplevel.defineEntity(ent);
        }
        
        resolveFunctions(ast.funcs());
    }

    private void resolveFunctions(List<Function> funcs) {
        for(Function func : funcs) {
            pushScope(func.parameters());
            resolve(func.body());
            func.setScope(popScope());
        }
    }

    private void resolve(StmtNode n) {
        n.accept(this);
    }

    private void pushScope(List<? extends Variable> vars) {
        LocalScope scope = new LocalScope(currentScope());
        for(Variable var : vars) {
            if(scope.isDefinedLocally(var.name())) {
                error(var.location(), "deplicated variable in scope : " + var.name());
            }
            else {
                scope.defineVariable(var);
            }
        }
        scopeStack.push(scope);
    }

    private void error(Location location, String message) {
        errorHandler.error(location, message);
    }

    private LocalScope popScope() {
        return (LocalScope) scopeStack.pop();
    }
    
    private Scope currentScope() {
        return scopeStack.lastElement();
    }
}
