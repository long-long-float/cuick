package jp.long_long_float.cuick.cppStructure;

import java.util.ArrayList;
import java.util.List;

public class Struct extends CppStructure{
    private String name;
    private List<String> members = new ArrayList<String>();
    
    public Struct(String name) {
        this.name = name;
    }

    public Struct addMember(String type, String name) {
        members.add(type + " " + name);
        return this;
    }
    
    public String generateCode() {
        CodeContext cc = CodeContext.getInstance();
        StringBuilder ret = new StringBuilder();
        ret.append(cc.line("struct " + name + " {"));
        cc.indent();
        for(String member : members) {
            ret.append(cc.line(member + ";"));
        }
        cc.unindent();
        ret.append(cc.line("};"));
        return ret.toString();
    }
    
}
