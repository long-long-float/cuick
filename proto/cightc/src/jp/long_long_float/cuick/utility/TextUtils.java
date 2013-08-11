package jp.long_long_float.cuick.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import jp.long_long_float.cuick.exception.FileException;

import org.mozilla.universalchardet.UniversalDetector;

public class TextUtils {
    static final private byte vtab = 013;
    
    static public String encoding;
    
    static public String dumpString(String str) {
        try {
            return dumpString(str, encoding);
        }
        catch (UnsupportedEncodingException ex) {
            throw new Error("UTF-8 is not supported??: " + ex.getMessage());
        }
    }

    static public String dumpString(String string, String encoding) throws UnsupportedEncodingException {
        byte[] src = string.getBytes(encoding);
        StringBuffer buf = new StringBuffer();
        buf.append("\"");
        for (int n = 0; n < src.length; n++) {
            int c = toUnsigned(src[n]);
            if (c == '"') buf.append("\\\"");
            else if (isPrintable(c)) buf.append((char)c);
            else if (c == '\b') buf.append("\\b");
            else if (c == '\t') buf.append("\\t");
            else if (c == '\n') buf.append("\\n");
            else if (c == vtab) buf.append("\\v");
            else if (c == '\f') buf.append("\\f");
            else if (c == '\r') buf.append("\\r");
            else {
                buf.append("\\" + Integer.toOctalString(c));
            }
        }
        buf.append("\"");
        return buf.toString();
    }
    
    static private int toUnsigned(byte b) {
        return b >= 0 ? b : 256 + b;
    }

    static public boolean isPrintable(int c) {
        return (' ' <= c) && (c <= '~');
    }
    
    static public String detectEncoding(FileInputStream fis) throws FileException {
        UniversalDetector detector = new UniversalDetector(null);
        
        byte[] buf = new byte[4096];
        int nread;
        try {
            while((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
        }
        catch (IOException ex) {
            throw new FileException(ex.getMessage());
        }
        detector.dataEnd();
        
        String encoding = detector.getDetectedCharset();
        if(encoding == null) return "UTF-8";
        
        detector.reset();
        
        return encoding;
    }
    
    static public String times(String str, int count) {
        String ret = "";
        for(int i = 0;i < count;i++) ret += str;
        return ret;
    }
}
