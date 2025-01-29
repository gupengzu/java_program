package block;

import java.util.concurrent.ArrayBlockingQueue;

public class Eater extends Thread{
    ArrayBlockingQueue<String> queue;
    public Eater(ArrayBlockingQueue<String>queue){
        this.queue=queue;
    }
    @Override
    public void run() {
        while (true){
            try {
                String q=queue.take();
                System.out.println("eat"+q);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
