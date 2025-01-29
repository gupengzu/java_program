package First;

public class Test {
    public static void main(String[] args) {
        MyThread t1=new MyThread();
        MyThread t2=new MyThread();
        t1.setName("111");
        t2.setName("222");
        t2.start();
        t1.start();
    }
}
