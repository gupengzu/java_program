package TCP_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
            System.out.println((char)b);
        }

        OutputStream os=soc.getOutputStream();
        os.write("nihaonihao".getBytes());
        soc.close();
        ss.close();
    }
}
