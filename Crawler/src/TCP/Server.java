package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(12345);
        Socket soc=ss.accept();
        InputStream is=soc.getInputStream();
        InputStreamReader isr=new InputStreamReader(is);
        int b;
        while ((b=isr.read())!=-1){
            System.out.print((char)b);
        }
        soc.close();
        ss.close();
    }
}