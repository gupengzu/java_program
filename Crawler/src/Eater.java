public class Eater implements Runnable{
    @Override
    public void run() {

        //1.循环
        //2.同步代码块
        //3.判断共享数据是否到末尾（到了末尾）
        //4.判断共享数据是否到末尾（没到末尾，执行核心逻辑）
        while (true){
            synchronized (Desk.lock){
                if(Desk.count==0){
                    break;
                }
                else {
                    //判断桌子上是否有面条
                    if(Desk.foodFlag==0)
                    {  //如果没有，就等待
                        try {
                            Desk.lock.wait();//让当前线程跟锁进行绑定
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    //如果有，就开吃
                    else {
                        System.out.println("吃货正在吃面条" + Desk.count);

                        //吃完后，唤醒厨师接着做
                        Desk.lock.notifyAll();

                        //把吃的总数-1
                        Desk.count--;
                        //修改桌子的状态
                        Desk.foodFlag=0;
                    }
                }
            }
        }
    }
}
