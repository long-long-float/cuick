package jp.long_long_float.cuick.ast;
import java.io.PrintStream;
import java.lang.reflect.Field;

import jp.long_long_float.cuick.compiler.Acceptable;
import jp.long_long_float.cuick.entity.Scope;

abstract public class Node implements Dumpable, Acceptable{
    
    private Scope scope;
    
    protected Node parent;
    
    public Node(){
        //System.out.println(getClass().getSimpleName());
        for(Field field : getClass().getDeclaredFields()) {
            //System.out.println("    " + field.getName());
            try {
                field.setAccessible(true);
                if(field.get(this) instanceof Node) {
                    field.set(this, this);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
    }
    
    public Scope scope() {
        return scope;
    }
    
    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    public Node parent() {
        return parent;
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
    
    public BlockNode parentBlockNode(int depth) {
        return parent != null ? parent.parentBlockNode(depth) : null;
    }
    
    public boolean isDefinedVariable(String name) {
        if(parent == null) return false;
        if(parent.isDefinedVariable(name)) return true;
        return false;
    }
}
