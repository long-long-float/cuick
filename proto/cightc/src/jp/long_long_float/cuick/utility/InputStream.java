package jp.long_long_float.cuick.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import lombok.Getter;

@Getter
public class InputStream implements AutoCloseable, Iterable<String>{
    private BufferedReader bufferedReader;
    private java.io.InputStream inputStream;
    
    public InputStream(java.io.InputStream is) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(is));
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
