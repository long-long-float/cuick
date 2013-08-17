package jp.long_long_float.cuick.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.long_long_float.cuick.exception.SemanticException;

public class ToplevelScope extends Scope {
    
    protected Map<String, Entity> entities;
    protected List<Variable> staticLocalVariable;

    public ToplevelScope() {
        super();
        entities = new LinkedHashMap<String, Entity>();
        staticLocalVariable = null;
    }
    
    @Override
    public boolean isToplevel() {
        return true;
    }

    @Override
    public ToplevelScope toplevel() {
        return this;
    }

    @Override
    public Scope parent() {
        return null;
    }

    @Override
    public Entity get(String name) throws SemanticException {
        Entity ent = entities.get(name);
        if(ent == null) {
            throw new SemanticException("unresolved reference : " + name);
        }
        return ent;
    }

    public void defineEntity(Entity ent) {
        // TODO 自動生成されたメソッド・スタブ
        
    }

}
