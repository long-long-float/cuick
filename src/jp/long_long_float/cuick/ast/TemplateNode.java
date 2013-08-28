package jp.long_long_float.cuick.ast;

import java.util.List;

import jp.long_long_float.cuick.utility.Pair;
import lombok.Getter;

@Getter
public class TemplateNode extends Node {

    private Location location;
    private List<Pair<String, String>> args;
    
    public TemplateNode(Location location, List<Pair<String, String>> args) {
        this.location = location;
        this.args = args;
    }

    @Override
    public Location location() {
        return location;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("args", args);
    }

}
