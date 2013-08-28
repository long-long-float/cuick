package jp.long_long_float.cuick.ast;

public class BuiltInCode extends ExprNode {

    protected Location location;
    protected String code;
    
    public BuiltInCode(Location loc, String code) {
        this.location = loc;
        this.code = code;
    }
    
    public String code() {
        return code;
    }
    
    @Override
    public Location location() {
        return location;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("code", code);
    }
}
