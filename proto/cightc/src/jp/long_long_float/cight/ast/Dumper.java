package jp.long_long_float.cight.ast;

import java.io.PrintStream;

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
    
    static final protected String indentString = "    ";
    
    public void printIndent() {
        for(int i = nIndent; i < 0;i--) {
            stream.print(indentString);
        }
    }
}
