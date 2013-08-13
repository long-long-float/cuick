package jp.long_long_float.cuick.ast;

public class BuiltInCode extends ExprNode {

    protected Location location;
    protected String code;
    
    public BuiltInCode(Location loc, String code) {
        this.location = loc;
        this.code = code;
    }
    
    @Override
    public Location location() {
        return location;
    }
    
    @Override
    public String toString() {
        return code.replace("`", "");
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("code", code);
    }

}
