package Four;

import java.util.Random;

public class MyThread extends Thread{
    static double money=100;
    static int count=3;
    static final double MIN=0.01;

    @Override
    public void run() {
        synchronized (MyThread.class){
            if(count==0){
                System.out.println(getName()+"no no no");
            }else {
                double prize=0;
                if(count==1){
                    prize=money;
                }else {
                    Random r=new Random();
                    double bounds=money-(count-1)*MIN;
                    prize=r.nextDouble(bounds);
                    if(prize<MIN){
                        prize=MIN;
                    }
                }

                money-=prize;
                count--;
                System.out.println(getName()+"  "+prize+"元");
            }
        }
    }
}
