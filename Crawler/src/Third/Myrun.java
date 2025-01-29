package Third;

public class Myrun implements Runnable{
    public static int count=0;
    public static final Object obj=new Object();
    @Override
    public void run() {
        while (true){
            synchronized (obj){
                if(count<=100) {
                    if (count % 2 == 1) {
                        System.out.println(Thread.currentThread().getName()+"   " + count);
                        count++;
                    }
                    else {
                        count++;
                    }
                }else {
                    break;
                }
            }
        }
    }
}
