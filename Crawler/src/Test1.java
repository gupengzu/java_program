import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Test1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Cook c=new Cook();
        Thread t1=new Thread(c);
        Eater e=new Eater();
        Thread t2=new Thread(e);
        t1.setName("cook");
        t2.setName("eat");
        t1.start();
        t2.start();
    }
}