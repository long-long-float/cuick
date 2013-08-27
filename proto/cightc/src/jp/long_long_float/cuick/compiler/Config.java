package jp.long_long_float.cuick.compiler;

import java.io.FileWriter;
import java.io.IOException;

import org.yaml.snakeyaml.Yaml;

public class Config {
    public static final String FILE_NAME = "config.yaml";
    
    public String[] compiler;
    
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
}
