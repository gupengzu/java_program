public class Cook implements Runnable {

    @Override
    public void run() {
        //循环
        //代码块
        //判断，如果到末尾
        //判断，如何没到末尾
        while (true){
            synchronized (Desk.lock){
                if(Desk.count==0){
                    break;
                }
                else {
                    if(Desk.foodFlag==1){
                        try {
                            Desk.lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        System.out.println("厨师做了一碗面条");
                        Desk.foodFlag=1;
                        Desk.lock.notifyAll();
                    }
                }
            }
        }
    }
}
