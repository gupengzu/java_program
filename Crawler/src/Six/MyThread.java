package Six;
import java.util.ArrayList;
import java.util.Collections;

public class MyThread extends Thread {
    ArrayList<Integer> list;
    ArrayList<Integer> list1 = new ArrayList<>();
    ArrayList<Integer> list2 = new ArrayList<>();

    public MyThread(ArrayList<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (MyThread.class) {
                if (list.size() == 0) {
                    if("111".equals(getName())) {
                        System.out.println("list1" + list1);
                    }
                    if("222".equals(getName())) {
                        System.out.println("list2" + list2);
                    }
                    break;
                } else {

                    Collections.shuffle(list);
                    int prize = list.remove(0);
                    if ("111".equals(getName())) {
                        list1.add(prize);
                    }
                    if ("222".equals(getName())) {
                        list2.add(prize);
                    }

                    //System.out.println(getName()+"  "+prize);
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
