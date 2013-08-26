package jp.long_long_float.cuick.ast;

public class AtTestCase extends Node {

    private Location location;
    private String fileName;

    public AtTestCase(Location location, String fileName) {
        this.location = location;
        this.fileName = fileName;
    }
    
    public String fileName() {
        return fileName;
    }
    
    @Override
    public Location location() {
        return location;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("fileName", fileName);
    }

}
