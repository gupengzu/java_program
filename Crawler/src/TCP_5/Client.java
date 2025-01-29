package TCP_5;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 10002);
        BufferedInputStream bis=new BufferedInputStream(new FileInputStream("G:\\photos\\bk.jpg"));
        BufferedOutputStream bos=new BufferedOutputStream(socket.getOutputStream());
        int len;
        byte[]bytes=new byte[1024];
        while ((len=bis.read(bytes))!=-1){
            bos.write(bytes,0,len);
        }
        bos.flush();
        //向服务器写出结束标记
        socket.shutdownOutput();

        //接受服务器的回写数据
        BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line=br.readLine();
        System.out.println(line);
        socket.close();
    }
}
