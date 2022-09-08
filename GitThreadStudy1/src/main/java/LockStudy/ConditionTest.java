package LockStudy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
    //创建一个显示锁
    static Lock lock = new ReentrantLock();
    //获取一个显示锁绑定的Condition对象，生产方和消费方用的是同一个锁的同一个condition对象
    static  private Condition condition=lock.newCondition();
    //等待线程的异步目标任务
    static class WaitTarget implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("我是等待方");
                //开始等待，并且释放锁
                condition.await();
                System.out.println("收到通知，等待方继续执行");
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //释放锁
                lock.unlock();
            }
        }
    }
    //通知线程的异步目标任务
    static class NotifyTarget implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("我是通知方");
                //发送通知
                condition.signal();
                System.out.println("发出了通知，但是线程没有立即释放锁");
            }finally {
                //释放锁之后，等待线程才能获取到锁
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        //创建一个等待线程
        Thread waitThread = new Thread(new WaitTarget(), "WaitThread");
        //启动等待线程
        waitThread.start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread notifyThread = new Thread(new NotifyTarget(), "NotifyThread");
        //启动通知线程
        notifyThread.start();
    }
}
