package jp.long_long_float.cuick.entity;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.TypeNode;
import jp.long_long_float.cuick.type.Type;

import org.apache.commons.lang3.StringUtils;

public class Variable extends Entity {
    
    protected List<ExprNode> constructorArgs = null;
    protected boolean isArray = false;
    protected ExprNode arraySize = null;
    protected List<ExprNode> init;
    
    public Variable(TypeNode typeNode, String name, List<ExprNode> constructorArgs, boolean isArray, ExprNode arraySize, List<ExprNode> init) {
        super(typeNode, name);
        
        this.constructorArgs = (constructorArgs != null ? constructorArgs : new ArrayList<ExprNode>());
        this.isArray = isArray;
        this.arraySize = arraySize;
        this.init = (init != null ? init : new ArrayList<ExprNode>());
    }
    
    @Override
    public Type type() {
        Type ret = super.type();
        return isArray ? ret.increasePointer() : ret;
    }
    
    public Type rawType() {
        return super.type();
    }
    
    public boolean isArray() {
        return isArray;
    }
    
    public ExprNode arraySize() {
        return arraySize;
    }
    
    public List<ExprNode> init() {
        return init;
    }
    
    public List<ExprNode> constructorArgs() {
        return constructorArgs;
    }
    
    @Override
    public String toString() {
        String ret = name;
        if(!constructorArgs.isEmpty()) {
            ret += "(" + StringUtils.join(constructorArgs, ", ") + ")";
        }
        if(isArray) {
            ret += "[" + (arraySize != null ? arraySize : "") + "]";
        }
        if(!init.isEmpty()) {
            ret += " = ";
            if(init.size() == 1) {
                ret += init.get(0).toString();
            }
            else {
                ret += "{" + StringUtils.join(init, ", ") + "}";
            }
        }
        return ret;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("type", type());
    }

    public void rename(String name) {
        this.name = name;
    }
}
