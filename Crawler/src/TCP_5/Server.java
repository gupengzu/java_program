package TCP_5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.*;

public class Server {
    public static void main(String[] args) throws IOException {

        //创建线程池对象
        ThreadPoolExecutor pool=new ThreadPoolExecutor(
                3,
                20,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        ServerSocket ss = new ServerSocket(10002);

        while (true) {
            Socket socket = ss.accept();
            pool.submit(new MyRun(socket));
        }

    }
}
