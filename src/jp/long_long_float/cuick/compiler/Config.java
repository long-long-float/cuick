package jp.long_long_float.cuick.compiler;

import java.io.FileWriter;
import java.io.IOException;

import org.yaml.snakeyaml.Yaml;

public class Config {
    public static final String FILE_NAME = "config.yaml";
    
    public void save() throws IOException {
        FileWriter fw = null;
        try {
            fw = new FileWriter(FILE_NAME);
            fw.write(new Yaml().dump(this));
        }
        finally {
            if(fw != null) fw.close();
        }
    }
    
    public void createDummyConfig() {
        Config config = new Config();
        config.compiler = new String[]{"1", "2", "3"};
        config.test = new Test();
        config.test.exefile = "a.exe";
        try {
            config.save();
        } catch (IOException ex) {
        }
    }
    
    public String[] compiler;
    
    public static class Test {
        public String exefile;
    }
    public Test test;
    
}
