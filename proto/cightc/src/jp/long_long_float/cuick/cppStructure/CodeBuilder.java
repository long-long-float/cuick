package jp.long_long_float.cuick.cppStructure;


public class CodeBuilder {
    private StringBuilder sb = new StringBuilder();
    
    public CodeBuilder addLine(String line) {
        sb.append(CodeContext.getInstance().getTabSpace() + line + System.getProperty("line.separator"));
        return this;
    }
    
    public String toString() {
        return sb.toString();
    }
    
    public interface BlockCallback {
        public void call(CodeBuilder cb);
    }
    
    public void block(BlockCallback callback, boolean semicolon) {
        CodeContext cc = CodeContext.getInstance();
        addLine("{");
        cc.indent();
        callback.call(this);
        cc.unindent();
        addLine("}" + (semicolon ? ";" : ""));
    }
}
