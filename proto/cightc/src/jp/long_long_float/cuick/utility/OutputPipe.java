package jp.long_long_float.cuick.utility;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import lombok.Getter;

@Getter
public class OutputPipe implements AutoCloseable{
    private PipedInputStream pipedInputStream;
    private BufferedWriter bufferedWriter;
    
    public OutputPipe() throws IOException {
        pipedInputStream = new PipedInputStream();
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new PipedOutputStream(pipedInputStream)));
    }
    
    public void write(String str) throws IOException {
        bufferedWriter.write(str);
        bufferedWriter.flush();
    }
    
    @Override
    public void close() throws Exception {
        bufferedWriter.close();
        bufferedWriter.close();
    }

}
