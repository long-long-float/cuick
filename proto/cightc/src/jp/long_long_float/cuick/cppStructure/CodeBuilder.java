package jp.long_long_float.cuick.cppStructure;


public class CodeBuilder {
    private StringBuilder sb = new StringBuilder();
    
    public CodeBuilder add(String str) {
        sb.append(CodeContext.getInstance().getTabSpace() + str);
        return this;
    }
    
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
        beginBlock();
        callback.call(this);
        endBlock(semicolon);
    }
    
    public void beginBlock() {
        addLine("{");
        CodeContext.getInstance().indent();
    }
    
    public void endBlock(boolean semicolon) {
        CodeContext.getInstance().unindent();
        addLine("}" + (semicolon ? ";" : ""));
    }
}
