package xianchengchi;

import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
//        ThreadPoolExecutor pool=new ThreadPoolExecutor(
//                3,//核心线程数量
//                6,//最大线程数量
//                60,//秒
//                TimeUnit.SECONDS,//时间单位
//                new ArrayBlockingQueue<>(3),//排队长度
//                Executors.defaultThreadFactory(),//创建线程工厂
//                new ThreadPoolExecutor.AbortPolicy()//任务的拒绝策略
//        );

        //pool1.shutdown();

        int count=Runtime.getRuntime().availableProcessors();
        System.out.println(count);

    }
}
