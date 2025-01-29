package IP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Zubo {
    public static void main(String[] args) throws IOException {
        MulticastSocket ms=new MulticastSocket(10000);

        InetAddress address=InetAddress.getByName("224.0.0.1");
        ms.joinGroup(address);

        byte[]bytes=new byte[1024];
        DatagramPacket dp=new DatagramPacket(bytes,bytes.length);

        ms.receive(dp);

        byte[]data= dp.getData();
        int len=dp.getLength();

    }
}
