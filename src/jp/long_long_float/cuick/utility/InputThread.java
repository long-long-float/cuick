package jp.long_long_float.cuick.utility;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class InputThread extends Thread {

    @Getter
    private List<String> buffer = new ArrayList<String>();
    private java.io.InputStream inputStream;
    
    public InputThread(java.io.InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    @Override
    public void run() {
        InputStream is = null;
        try {
            is = new InputStream(inputStream);
            while(true) {
                String line = is.getBufferedReader().readLine();
                if(line == null) break;
                buffer.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null) is.close();
            } catch (Exception e) {
            }
        }
    }

}
