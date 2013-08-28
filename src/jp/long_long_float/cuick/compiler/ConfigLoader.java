package jp.long_long_float.cuick.compiler;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.long_long_float.cuick.exception.ConfigException;
import jp.long_long_float.cuick.utility.ErrorHandler;
import jp.long_long_float.cuick.utility.FileUtils;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

public class ConfigLoader {
    private ErrorHandler errorHandler;
    private String yamlData = null;
    
    public ConfigLoader(ErrorHandler errorHandler) throws IOException {
        this.errorHandler = errorHandler;
        yamlData = FileUtils.readFromFile(Config.FILE_NAME);
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
            errorHandler.error(Config.FILE_NAME + ": undefined constant \"" + m.group(1) + "\"");
            throw new ConfigException("config error");
        }
        Config ret = null;
        try {
            ret = (Config) new Yaml().load(replacedData);
        } 
        catch (YAMLException ex) {
            errorHandler.error(Config.FILE_NAME + ": " + ex.getMessage());
            throw new ConfigException("load error");
        }
        return ret;
    }

}
