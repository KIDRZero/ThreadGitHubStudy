package CopyOnWriteArrayListStudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

public class Test1 {
    private static Logger log = LoggerFactory.getLogger(Test1.class);

    /**
     * 请求总数
     */
    public static int clientTotal = 5000;

    /**
     * 同时并发执行的线程数
     */
    public static int threadTotal = 200;

    public static List<Integer> arrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws Exception {
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量（并发线程数）
        final Semaphore semaphore = new Semaphore(threadTotal);
        //计数器  （把请求计数）
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    //信号量  判断进程是否执行
                    semaphore.acquire();
                    add();
                    semaphore.release();
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
        log.info("listSize:{}", arrayList.size());
    }

    private static void add() {
        arrayList.add(1);
    }

}
