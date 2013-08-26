package jp.long_long_float.cuick.ast;

public class SharpIncludeNode extends SharpDirectiveNode {

    private String name;
    
    public SharpIncludeNode(Location location, String name) {
        super(location);
        this.name = name;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("name", name);
    }

    public String name() {
        return name;
    }

}
