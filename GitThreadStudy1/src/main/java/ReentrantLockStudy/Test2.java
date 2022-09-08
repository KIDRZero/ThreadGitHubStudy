package ReentrantLockStudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

public class Test2 {
    private static Logger log = LoggerFactory.getLogger(Test1.class);

    /**
     * 定义一个公平锁 ReentrantLock(true)参数为true，表明实现公平锁机制
     */
    final static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) throws Exception {
        new Thread(() -> testLock(), "线程壹号").start();
        new Thread(() -> testLock(), "线程贰号").start();
        new Thread(() -> testLock(), "线程叁号").start();
    }

    private static void testLock() {
        for (int i = 0; i < 2; i++) {
            //操作前加锁
            lock.lock();
            try {
                log.info("{}获取了锁", Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //操作后在finally中关闭锁，确保锁成功释放，避免死锁
                lock.unlock();
            }
        }
    }

}
