package SemaphoreStudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Test2 {
    private static Logger log = LoggerFactory.getLogger(Test2.class);

    private final static int threadCount = 15;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    //一个线程可以获取多个许可
                    semaphore.acquire(3);
                    test(threadNum);
                    //释放多个许可
                    semaphore.release(3);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        // 模拟请求的耗时操作
        Thread.sleep(1000);
        log.info("{}", threadNum);
    }
}
