package First;

public class MyThread extends Thread{
    public static int ticketCount=10;
    public static final Object lock=new Object();
    @Override
    public void run() {
        while (true){
        synchronized (lock) {
            if (ticketCount > 0) {
                try {
                    System.out.println(ticketCount+this.getName());
                    ticketCount--;
                    Thread.sleep(10);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                break;
            }

        }
        }
    }
}
