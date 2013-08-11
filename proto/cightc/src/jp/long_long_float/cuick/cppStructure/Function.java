package jp.long_long_float.cuick.cppStructure;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.cppStructure.CodeBuilder.BlockCallback;

import org.apache.commons.lang3.StringUtils;

public class Function extends CppStructure {

    private String retType, name;
    private List<String> args = new ArrayList<String>();
    private List<String> body = new ArrayList<String>();
    
    public Function(String retType, String name) {
        this.retType = retType;
        this.name = name;
    }
    
    public Function addArg(String type, String name) {
        args.add(type + " " + name);
        return this;
    }
    
    public Function addStmt(String stmt) {
        body.add(stmt);
        return this;
    }
    
    @Override
    public String generateCode() {
        CodeBuilder cb = new CodeBuilder();
        cb.addLine(retType + " " + name + "(" + StringUtils.join(args, ", ") + ")");
        cb.block(new BlockCallback() {
            @Override
            public void call(CodeBuilder cb) {
                for(String stmt : body) {
                    cb.addLine(stmt);
                }
            }
        }, false);
        return cb.toString();
    }

}
