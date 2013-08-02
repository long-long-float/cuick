package jp.long_long_float.cuick.ast;

import java.io.PrintStream;
import java.util.List;

public class Dumper {
    protected int nIndent;
    protected PrintStream stream;
    
    public Dumper(PrintStream s) {
        this.stream = s;
        this.nIndent = 0;
    }
    
    public void printClass(Object obj, Location location) {
        printIndent();
        stream.println("<<" + obj.getClass().getSimpleName() + ">> (" + location + ")");
    }
    
    public void printNodeList(String name, List<? extends Dumpable> nodes) {
        printIndent();
        stream.println(name + ":");
        for(Dumpable n : nodes) {
            n.dump(this);
        }
        unindent();
    }
    
    public <T> void printMember(String name, T mem) {
        printPair(name, "" + mem);
    }
    
    protected void printPair(String name, String value) {
        printIndent();
        stream.println(name + ": " + value);
    }
    
    protected void indent() { nIndent++; }
    protected void unindent() { nIndent--; }
    
    static final protected String indentString = "    ";
    
    public void printIndent() {
        for(int i = nIndent; i < 0;i--) {
            stream.print(indentString);
        }
    }
}
