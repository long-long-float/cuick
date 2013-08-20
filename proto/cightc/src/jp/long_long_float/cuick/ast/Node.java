package jp.long_long_float.cuick.ast;
import java.io.PrintStream;

import jp.long_long_float.cuick.compiler.Acceptable;

abstract public class Node implements Dumpable, Acceptable{
    public Node(){   
    }
    
    abstract public Location location();
    
    public void dump() {
        dump(System.out);
    }
    
    public void dump(PrintStream ps) {
        dump(new Dumper(ps));
    }
    
    public void dump(Dumper d) {
        d.printClass(this, location());
        _dump(d);
    }
    
    abstract protected void _dump(Dumper d);
    
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
    
    @Deprecated
    public String toString() {
        throw new Error("toString() is deprecated!");
    }
}
