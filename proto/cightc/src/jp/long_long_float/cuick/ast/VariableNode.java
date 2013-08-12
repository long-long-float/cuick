package jp.long_long_float.cuick.ast;



public class VariableNode extends LHSNode {
    private Location location;
    private String name;
    //private Entity entity;
    
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
        /*if (type != null) {
            d.printMember("type", type);
        }*/
        d.printMember("name", name);
    }

}
