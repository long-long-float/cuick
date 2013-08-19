package jp.long_long_float.cuick.compiler;

import java.util.List;
import java.util.Stack;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.ast.VariableNode;
import jp.long_long_float.cuick.entity.Entity;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.LocalScope;
import jp.long_long_float.cuick.entity.Scope;
import jp.long_long_float.cuick.entity.ToplevelScope;
import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.exception.SemanticException;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class LocalResolver extends Visitor {
    private final Stack<Scope> scopeStack;
    
    public LocalResolver(ErrorHandler h) {
        super(h);
        this.scopeStack = new Stack<Scope>();
    }
    
    public void resolve(AST ast) throws SemanticException {
        ToplevelScope toplevel = new ToplevelScope();
        scopeStack.add(toplevel);
        
        for(Entity ent : ast.definitions()) {
            toplevel.defineEntity(ent);
        }
        
        for(StmtNode stmt : ast.stmts()) {
            stmt.accept(this);
        }
        resolveFunctions(ast.funcs());
        
        toplevel.checkReferences(errorHandler);
        if(errorHandler.errorOccured()) {
            throw new SemanticException("compile failed!");
        }
        
        ast.setScope(toplevel);
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
    
    public Void visit(BlockNode node) {
        pushScope(node.variables());
        super.visit(node);
        node.setScope(popScope());
        return null;
    }
    
    public Void visit(VariableNode node) {
        try {
            Entity ent = currentScope().get(node.name());
            if(ent != null) {
                ent.refered();
                node.setEntity(ent);
            }
        }
        catch (SemanticException ex) {
            error(node.location(), ex.getMessage());
        }
        return null;
    }

    private LocalScope popScope() {
        return (LocalScope) scopeStack.pop();
    }
    
    private Scope currentScope() {
        return scopeStack.lastElement();
    }
}
