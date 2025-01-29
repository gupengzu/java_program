package IP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Receive {
    public static void main(String[] args) throws IOException {
        //创建“快递公司”
        DatagramSocket ds=new DatagramSocket(10086);

        byte[]bytes=new byte[1024];
        DatagramPacket dp=new DatagramPacket(bytes,bytes.length);
        while (true) {
            ds.receive(dp);

            String ip = dp.getAddress().getHostAddress();
            byte[] data = dp.getData();
            int len = dp.getLength();
            String name = dp.getAddress().getHostName();

            System.out.println("ip:" + ip + " 主机名为" + name + " 发送的数据" + new String(data, 0, len));
        }
    }
}
