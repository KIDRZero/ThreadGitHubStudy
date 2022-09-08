package CountDownLatchStudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class Test3 {
    private static Logger log = LoggerFactory.getLogger(Test2.class);

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    //所有线程阻塞在这，等待主线程号令
                    log.info(Thread.currentThread().getName() + "已准备完毕！！");
                    countDownLatch.await();
                    log.info("【" + Thread.currentThread().getName() + "】" + "开始执行……");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        // 主线程准备发令
        Thread.sleep(2000);
        log.info(Thread.currentThread().getName() + "发号施令，给我冲！！");
        // 主线程：执行发令
        countDownLatch.countDown();
    }
}
