package jp.long_long_float.cuick.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Iterator;

import lombok.Getter;

@Getter
public class InputPipe implements AutoCloseable, Iterable<String>{
    private PipedOutputStream pipedOutputStream;
    private BufferedReader bufferedReader;
    
    public InputPipe() throws IOException {
        pipedOutputStream = new PipedOutputStream();
        bufferedReader = new BufferedReader(new InputStreamReader(new PipedInputStream(pipedOutputStream)));
    }

    @Override
    public void close() throws Exception {
        bufferedReader.close();
    }

    public static class InputIterator implements Iterator<String> {

        private BufferedReader br;
        
        public InputIterator(BufferedReader br) {
            this.br = br;
        }
        
        @Override
        public boolean hasNext() {
            try {
                return br.ready();
            } catch (IOException e) {
                throw new Error(e);
            }
        }

        @Override
        public String next() {
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new Error(e);
            }
        }

        @Override
        public void remove() {
        }
        
    }
    
    @Override
    public java.util.Iterator<String> iterator() {
        return new InputIterator(bufferedReader);
    }
}
