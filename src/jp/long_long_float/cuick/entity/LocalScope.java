package jp.long_long_float.cuick.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import jp.long_long_float.cuick.exception.SemanticException;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class LocalScope extends Scope{
    protected Scope parent;
    protected Map<String, Variable> variables;
    
    public LocalScope(Scope parent) {
        super();
        this.parent = parent;
        parent.addChild(this);
        variables = new LinkedHashMap<String, Variable>();
    }

    @Override
    public boolean isToplevel() {
        return false;
    }

    @Override
    public ToplevelScope toplevel() {
        return parent.toplevel();
    }

    @Override
    public Scope parent() {
        return this.parent;
    }
    /*
    @Override
    public boolean isDefinedVariable(String name) {
        if(variables.containsKey(name)) return true;
        return parent.isDefinedVariable(name);
    }
    */
    @Override
    public Entity get(String name) throws SemanticException {
        Variable var = variables.get(name);
        if(var != null) {
            return var;
        }
        else {
            return parent.get(name);
        }
    }

    public boolean isDefinedLocally(String name) {
        return variables.containsKey(name);
    }

    public void defineVariable(Variable var) {
        if(isDefinedLocally(var.name()))  {
            throw new Error("duplicated variable: " + var.name());
        }
        variables.put(var.name(), var);
    }
    
    public Variable getDefinedVariable(String name) {
        return variables.get(name);
    }

    public void checkReferences(ErrorHandler errorHandler) {
        for(Variable var : variables.values()) {
            if(!var.isRefered()) {
                errorHandler.warn(var.location(), "unused variable : " + var.name());
            }
        }
        for(LocalScope c : children) {
            c.checkReferences(errorHandler);
        }
    }
}
