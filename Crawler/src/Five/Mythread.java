package Five;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class Mythread extends Thread{
    ArrayList<Integer>list;

    public Mythread(ArrayList<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true){
            synchronized (Mythread.class) {
                if (list.size() == 0) {
                    System.out.println("no no no");
                    break;
                }else {

                    Collections.shuffle(list);
                    int prize=list.remove(0);
                    System.out.println(getName()+"  "+prize);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
