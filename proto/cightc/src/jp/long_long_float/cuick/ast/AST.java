package jp.long_long_float.cuick.ast;

public class AST extends Node{
    protected Location source;
    protected Declarations declarations;
    
    public AST(Location source, Declarations declarations) {
        super();
        this.source = source;
        this.declarations = declarations;
    }
    
    public Location location() {
        return source;
    }

    @Override
    protected void _dump(Dumper d) {
        
    }
}