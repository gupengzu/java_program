package Four;

public class Test {
    public static void main(String[] args) {
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();
        Thread t5 = new MyThread();

        t1.setName("111");
        t2.setName("222");
        t3.setName("333");
        t4.setName("444");
        t5.setName("555");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}