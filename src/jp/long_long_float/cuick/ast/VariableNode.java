package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.entity.Entity;

public class VariableNode extends LHSNode {
    private Location location;
    private String provisionalName;
    private Entity entity;
    
    public VariableNode(Location loc, String name) {
        this.location = loc;
        this.provisionalName = name;
    }
    
    @Override
    public Location location() {
        return location;
    }
    
    public String name() {
        return entity != null ? entity.name() : provisionalName;
    }
    
    @Override
    public String toString() {
        return name();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("name", name());
        d.printMember("type", (entity != null ? entity.type() : "???"));
    }
    
    public Entity entity() {
        /*
         * 知らない関数だとnullがありうるので
        if(entity == null) {
            throw new Error("VariableNode.entity == null");
        }
        */
        return entity;
    }

    public void setEntity(Entity ent) {
        entity = ent;
    }

}
