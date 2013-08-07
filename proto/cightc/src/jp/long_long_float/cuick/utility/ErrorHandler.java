package jp.long_long_float.cuick.utility;

import java.io.OutputStream;
import java.io.PrintStream;

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
}
