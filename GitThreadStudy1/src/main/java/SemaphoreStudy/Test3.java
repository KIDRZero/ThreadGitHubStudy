package SemaphoreStudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Test3 {
    private static Logger log = LoggerFactory.getLogger(Test2.class);

    private final static int threadCount = 15;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();
        //信号量设置为3，也就是最大并发量为3，同时只允许3个线程获得许可
        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    //尝试获取一个许可,如果未获取到，不等待，将直接丢弃该线程不执行
                    if(semaphore.tryAcquire()) {
                        test(threadNum);
                        //释放许可
                        semaphore.release();
                    }
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
