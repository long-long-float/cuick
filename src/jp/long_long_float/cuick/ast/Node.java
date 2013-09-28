package jp.long_long_float.cuick.ast;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;

import jp.long_long_float.cuick.compiler.Acceptable;

abstract public class Node implements Dumpable, Acceptable{
    
    protected Node parent;
    
    public Node(){
    }
    
    /**
     * 自分のすべての子のsetParent(this)を呼ぶ
     */
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
            } catch (IllegalArgumentException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
    }
    
    public Node parent() {
        return parent;
    }
    
    /**
     * 親を設定する
     * @param parent 親
     */
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
    
    protected void _dumpDefault(Dumper d) {
        for(Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                d.printMember(field.getName(), field.get(this));
            } catch (IllegalArgumentException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
    }
    
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
