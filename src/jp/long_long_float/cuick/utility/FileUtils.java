package jp.long_long_float.cuick.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class FileUtils {
    public static String readFromFile(String path) throws IOException {
        FileInputStream fis = null;
        String ret = null;
        try {
            File file = new File(path);
            fis = new FileInputStream(file);
            byte[] buf = new byte[(int) file.length()];
            fis.read(buf);
            ret = new String(buf);
        }
        finally {
            if(fis != null) fis.close();
        }
        return ret;
    }
}
