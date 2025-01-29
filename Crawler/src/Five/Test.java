package Five;

import Four.MyThread;

import java.util.ArrayList;
import java.util.Collections;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list,10,5,20,50,100,200,500,800,2,80,300,700);

        Thread t1=new Mythread(list);
        Thread t2=new Mythread(list);

        t1.setName("111");
        t2.setName("222");

        t1.start();
        t2.start();
    }
}