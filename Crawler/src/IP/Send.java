package IP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Send {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds=new DatagramSocket();
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入您要说的话");
            String str = sc.nextLine();
            if("886".equals(str)){
                break;
            }
            byte[] bytes = str.getBytes();
            InetAddress address = InetAddress.getByName("127.0.0.1");

            DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, 10086);

            ds.send(dp);
        }
        ds.close();
    }
}
