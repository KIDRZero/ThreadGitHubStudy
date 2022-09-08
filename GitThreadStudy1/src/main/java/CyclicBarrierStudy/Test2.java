package CyclicBarrierStudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test2 {
    private static Logger log = LoggerFactory.getLogger(Test2.class);

    /**
     * 线程数量
     */
    private final static int threadCount = 15;
    /**
     * 屏障拦截的线程数量为5，表示每次屏障会拦截5个线程
     */
    private static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        executor.shutdown();
    }

    private static void race(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready{}", threadNum,barrier.getNumberWaiting());
        //每次调用await方法后计数器+1，当前线程被阻塞
        //等待2s.为了使在发生异常的时候，不影响其他线程，一定要catch
        //由于设置了超时时间后阻塞的线程可能会被中断，抛出BarrierException异常，如果想继续往下执行,需要加上try-catch
        try {
            barrier.await(2, TimeUnit.SECONDS);
        }catch (Exception e){
            //查看执行异常的线程
            log.info("线程{} 执行异常，阻塞被中断？{}",threadNum,barrier.isBroken());
        }
        log.info("{} continue", threadNum);
    }
}
