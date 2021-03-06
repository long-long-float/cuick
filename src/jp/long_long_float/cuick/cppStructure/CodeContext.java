package jp.long_long_float.cuick.cppStructure;

import jp.long_long_float.cuick.utility.TextUtils;

public final class CodeContext {
    private static final class CodeContextHolder {
        private static final CodeContext instance = new CodeContext();
    }
    
    public static CodeContext getInstance() {
        return CodeContext.CodeContextHolder.instance;
    }
    
    private CodeContext() {
        
    }
    
    private String indentWidth = "    ";
    public void setIndentWidth(int n) {
        indentWidth = TextUtils.times(" ", n);
    }
    
    private int indent = 0;
    public void indent() { indent++; }
    public void unindent() { indent--; }
    
    public int getIndent() { return indent; }
    
    public String getTabSpace() {
        return TextUtils.times(indentWidth, indent);
    }
}
