package block;

import java.util.concurrent.ArrayBlockingQueue;

public class Test {
    public static void main(String[] args) {
        ArrayBlockingQueue<String>queue=new ArrayBlockingQueue<>(1);
        Cook c=new Cook(queue);
        Eater e=new Eater(queue);
        c.start();
        e.start();
    }
}
