package CountDownLatchStudy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test1 {
    /**
     * 线程数量
     */
    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    System.out.println("exception");
                } finally {
                    // 表示一个请求已经完成
                    countDownLatch.countDown();
                }
            });
        }
        //使当前线程等待，直到计数器为零，除非当前线程被中断
        countDownLatch.await();
        //当这200个请求被处理完成之后，才会执行
        System.out.println("finish");
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        // 模拟请求的耗时操作
        Thread.sleep(100);
        System.out.println(threadNum);
        Thread.sleep(100);
    }
}
