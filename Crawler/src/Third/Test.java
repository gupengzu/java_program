package Third;

public class Test {
    public static void main(String[] args) {
        Myrun m1=new Myrun();
        Thread t1=new Thread(m1);
        Thread t2=new Thread(m1);
        t1.setName("111");
        t2.setName("222");
        t1.start();
        t2.start();
    }
}
