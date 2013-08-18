package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.entity.Entity;



public class VariableNode extends LHSNode {
    private Location location;
    private String name;
    private Entity entity;
    
    public VariableNode(Location loc, String name) {
        this.location = loc;
        this.name = name;
    }
    
    @Override
    public Location location() {
        return location;
    }
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("type", (entity != null ? entity.type() : "???"));
    }

    public String name() {
        return name;
    }
    
    public Entity entity() {
        if(entity == null) {
            throw new Error("VariableNode.entity == null");
        }
        return entity;
    }

    public void setEntity(Entity ent) {
        entity = ent;
    }

}
