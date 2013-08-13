package jp.long_long_float.cuick.entity;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.TypeNode;

import org.apache.commons.lang3.StringUtils;

public class Variable extends Entity {
    
    protected List<ExprNode> constructorArgs = null;
    protected boolean isArray = false;
    protected ExprNode arraySize = null;
    protected List<ExprNode> init = new ArrayList<ExprNode>();
    
    public Variable(TypeNode type, String name, List<ExprNode> constructorArgs, boolean isArray, ExprNode arraySize, List<ExprNode> init) {
        super(type, name);
        this.constructorArgs = constructorArgs;
        this.isArray = isArray;
        this.arraySize = arraySize;
        this.init = init;
    }
    
    @Override
    public String toString() {
        String ret = name;
        if(constructorArgs != null) {
            ret += "(" + StringUtils.join(constructorArgs, ", ") + ")";
        }
        if(isArray) {
            ret += "[" + (arraySize != null ? arraySize : "") + "]";
        }
        if(init.size() != 0) {
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
        d.printMember("type", type());
        d.printMember("name", name);
    }

}
