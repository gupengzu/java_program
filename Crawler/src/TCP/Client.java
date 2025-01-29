package TCP;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket s=new Socket("127.0.0.1",12345);

        OutputStream os=s.getOutputStream();

        Scanner scan=new Scanner(System.in);
        while (true){
            String str=scan.next();
            if("886".equals(str)){
                break;
            }
            else {
                os.write(str.getBytes());
            }
        }

        os.close();
        s.close();
    }
}
