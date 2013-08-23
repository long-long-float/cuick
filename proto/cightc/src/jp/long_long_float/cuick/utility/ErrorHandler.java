package jp.long_long_float.cuick.utility;

import java.io.OutputStream;
import java.io.PrintStream;

import jp.long_long_float.cuick.ast.Location;

public class ErrorHandler {
    protected String programId;
    protected PrintStream stream;
    protected long nError;
    protected long nWarning;
    
    public ErrorHandler(String programId) {
        this.programId = programId;
        this.stream = System.err;
    }
    
    public ErrorHandler(String programId, OutputStream stream) {
        this.programId = programId;
        this.stream = new PrintStream(stream);
    }

    public void error(Location loc, String message) {
        error((loc != null ? loc : "???") + ": " + message);
    }
    
    public void error(String message) {
        stream.println(programId + ": error: " + message);
        nError++;
    }
    
    public void warn(Location loc, String msg) {
        warn((loc != null ? loc : "???") + ": " + msg);
    }

    public void warn(String msg) {
        stream.println(programId + ": warning: " + msg);
        nWarning++;
    }
    
    public boolean errorOccured() {
        return nError > 0;
    }
}
