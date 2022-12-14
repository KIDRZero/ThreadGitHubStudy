package ReentrantLockStudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {
    private static Logger log = LoggerFactory.getLogger(Test1.class);

    /**
     * 请求总数
     */
    public static int clientTotal = 5000;

    public static int count = 0;
    /**
     * 定义一个可重入锁
     */
    final static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws Exception {
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //计数器  （把请求计数）
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    add();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                //计数器减1
                countDownLatch.countDown();
            });
        }
        //当所有请求结束
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", count);
    }

    private static void add() {
        //操作前加锁
        lock.lock();
        try {
            count++;
        } catch (Exception e) {

        } finally {
            //操作后在finally中关闭锁，确保锁成功释放，避免死锁
            lock.unlock();
        }
    }

}
