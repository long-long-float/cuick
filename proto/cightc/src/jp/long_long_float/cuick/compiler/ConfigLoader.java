package jp.long_long_float.cuick.compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.long_long_float.cuick.exception.ConfigException;
import jp.long_long_float.cuick.utility.ErrorHandler;

import org.yaml.snakeyaml.Yaml;

public class ConfigLoader {
    private ErrorHandler errorHandler;
    private String yamlData = null;
    
    public ConfigLoader(ErrorHandler errorHandler) throws IOException {
        this.errorHandler = errorHandler;
        
        FileInputStream fis = null;
        try {
            File file = new File(Config.FILE_NAME);
            fis = new FileInputStream(file);
            byte[] buf = new byte[(int) file.length()];
            fis.read(buf);
            yamlData = new String(buf);
        }
        finally {
            if(fis != null) fis.close();
        }
    }

    public Config load(Map<String, String> constants) {
        String replacedData = yamlData;
        //定数を置換
        for(Map.Entry<String, String> constant : constants.entrySet()) {
            replacedData = replacedData.replaceAll("@\\{" + constant.getKey() + "\\}", constant.getValue());
        }
        //置換されてないものはエラー
        Matcher m = Pattern.compile("@\\{([a-zA-Z\\-_]*)\\}").matcher(replacedData);
        while(m.find()) {
            errorHandler.error(Config.FILE_NAME + " : undefined constant \"" + m.group(1) + "\"");
            throw new ConfigException("config error");
        }
        return (Config) new Yaml().load(replacedData);
    }

}
