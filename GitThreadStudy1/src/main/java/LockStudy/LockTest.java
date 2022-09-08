package LockStudy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    /**
     * 测试可重入锁
     */
    public static void testReenttrantLock(){
        //每个线程执行的轮数
         final int TURNS=100000;
         //线程数
         final int THREADS=10;
         //线程池，用于多线程模拟测试
        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        //创建一个可重入的，独占的锁对象
        Lock reentrantLock = new ReentrantLock();
        //创建倒数闸
        CountDownLatch countDownLatch = new CountDownLatch(THREADS);
        long start = System.currentTimeMillis();
        //10个线程并发操作
        for (int i = 0; i <THREADS ; i++) {
            //通过lambda表达式创建线程
            pool.submit(()->{
                try{
                    //每个线程做的过程是累加1000次
                    for (int j = 0; j <TURNS ; j++) {
                        IncrementData.lockAndFastIncrease(reentrantLock);
                    }
                    System.out.println(Thread.currentThread()+"本线程累加完成");
                }catch (Exception e){
                    e.printStackTrace();
                }
                //线程执行完成后，倒数闸减少一次
                 countDownLatch.countDown();
            });
        }
        try{
            //等待倒数闸门归零，相当于主线程处于等待状态，
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        float time=(System.currentTimeMillis() - start)/1000f;
        System.out.println("运行的总时长为:"+time);
        System.out.println("累加的结果为:"+IncrementData.sum);
    }

    public static void main(String[] args) {
        testReenttrantLock();
    }
}
