package jp.long_long_float.cuick.cppStructure;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.cppStructure.CodeBuilder.BlockCallback;

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
    
    public String toString() {
        CodeBuilder cb = new CodeBuilder();
        cb.addLine("struct " + name);
        cb.block(new BlockCallback() {
            @Override
            public void call(CodeBuilder cb) {
                for(String member : members) {
                    cb.addLine(member + ";");
                }
            }
        }, true);
        return cb.toString();
    }
    
}
