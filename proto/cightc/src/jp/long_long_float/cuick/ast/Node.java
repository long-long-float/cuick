package jp.long_long_float.cuick.ast;
import java.io.PrintStream;

abstract public class Node implements Dumpable{
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
}
