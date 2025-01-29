package TCP_3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(10002);

        Socket socket=ss.accept();

        BufferedInputStream bis=new BufferedInputStream(socket.getInputStream());
        String name= UUID.randomUUID().toString().replace("-","");
        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream("src\\"+name+".jpg"));
        int len;
        byte[]bytes=new byte[1024];
        while ((len=bis.read(bytes))!=-1){
            bos.write(bytes,0,len);
        }
        bos.flush();

        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write("已完成");
        bw.newLine();
        bw.flush();
        socket.close();
        ss.close();
    }
}
