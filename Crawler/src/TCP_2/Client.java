package TCP_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket s1 = new Socket("127.0.0.1", 12345);
        OutputStream os=s1.getOutputStream();
        os.write("haohao".getBytes());
        s1.shutdownOutput();


        InputStream is=s1.getInputStream();
        InputStreamReader isr=new InputStreamReader(is);
        int b;
        while ((b=isr.read())!=-1){
            System.out.println((char) b);
        }

        s1.close();

    }
}