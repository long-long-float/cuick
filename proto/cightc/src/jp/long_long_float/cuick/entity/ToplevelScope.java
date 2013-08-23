package jp.long_long_float.cuick.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.long_long_float.cuick.exception.SemanticException;
import jp.long_long_float.cuick.utility.ErrorHandler;

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
            //XXX undefinedに関しては関与しない
            //throw new SemanticException("unresolved reference : " + name);
        }
        return ent;
    }

    public void defineEntity(Entity ent) throws SemanticException {
        Entity e = entities.get(ent.name());
        if(e != null && e.isDefined()) {
            throw new SemanticException("duplicated definition: " + ent.name() + ": " + e.location() + " and " + ent.location());
        }
        entities.put(ent.name(), ent);
    }

    public void checkReferences(ErrorHandler errorHandler) {
        /*
        for(Entity ent : entities.values()) {
            if(ent.isDefined() && ent.isPrivate()) {
                errorHandler.warn(ent.location(), "unused variable : " + ent.name());
            }
        }
        */
        // do not check parameters
        for(LocalScope funcScope : children) {
            for(LocalScope s : funcScope.children) {
                s.checkReferences(errorHandler);
            }
        }
    }

}
