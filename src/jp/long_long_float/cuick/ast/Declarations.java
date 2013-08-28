package jp.long_long_float.cuick.ast;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.Variable;

public class Declarations {
    private List<Variable> vars = new ArrayList<Variable>();
    private List<Function> funcs = new ArrayList<Function>();
    private List<StmtNode> stmts = new ArrayList<StmtNode>();
    private List<AtCommandNode> atCommands = new ArrayList<AtCommandNode>();
    private List<SharpDirectiveNode> sharpDirectives = new ArrayList<SharpDirectiveNode>();

    public List<Variable> vars() {
        return vars;
    }
    
    public List<Function> funcs() {
        return funcs;
    }
    
    public List<StmtNode> stmts() {
        return stmts;
    }
    
    public List<AtCommandNode> atCommands() {
        return atCommands;
    }
    
    public List<SharpDirectiveNode> sharpDirectives() {
        return sharpDirectives;
    }
}
