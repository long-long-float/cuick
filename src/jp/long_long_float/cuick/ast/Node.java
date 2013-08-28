package jp.long_long_float.cuick.ast;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;

import jp.long_long_float.cuick.compiler.Acceptable;

abstract public class Node implements Dumpable, Acceptable{
    
    protected Node parent;
    
    public Node(){
    }
    
    public void setParents() {
        for(Field field : getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object child = field.get(this);
                if(child instanceof Node) {
                    ((Node)child).setParent(this);
                }
                else if(child instanceof List) {
                    for(Object item : (List<?>)child) {
                        if(!(item instanceof Node)) break;
                        
                        ((Node)item).setParent(this);
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
    }
    
    public Node parent() {
        return parent;
    }
    
    public void setParent(Node parent) {
        this.parent = parent;
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
        if(parent != null) d.printMember("parent", parent.getClass().getSimpleName());
    }
    
    abstract protected void _dump(Dumper d);
    
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public BlockNode parentBlockNode(int depth) {
        return parent != null ? parent.parentBlockNode(depth) : null;
    }
    
    public boolean isDefinedVariable(String name) {
        return parent != null && parent.isDefinedVariable(name);
    }
}
