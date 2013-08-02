package jp.long_long_float.cuick.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import sun.org.mozilla.javascript.internal.ast.Scope;

public class LocalScope extends Scope{
    protected Scope parent;
    protected Map<String, Variable> variables;
    
    public LocalScope(Scope parent) {
        super();
        this.parent = parent;
        parent.addChild(this);
        variables = new LinkedHashMap<String, Variable>();
    }
}
