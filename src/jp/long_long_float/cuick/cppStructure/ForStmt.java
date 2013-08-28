package jp.long_long_float.cuick.cppStructure;


public class ForStmt extends CppStructure {

    private String init, cond, incr, body;
    
    public ForStmt(String init, String cond, String incr) {
        this.init = init;
        this.cond = cond;
        this.incr = incr;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    @Override
    public String toString() {
        return "for(" + init + ";" + cond + ";" + incr + ")" + body;
    }
    
}
