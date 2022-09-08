package FutureTaskStudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class Test1 {
    private static Logger log = LoggerFactory.getLogger(Test1.class);

    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("callable耗时任务开始");
                //耗时任务
                Thread.sleep(5000);
                log.info("callable耗时任务完成");
                return "耗时任务：报告！我已完成";
            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(futureTask);
        //executor.execute(futureTask);
        log.info("主线程任务开始");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("主线程任务完成");
        log.info("等待耗时任务完成。。。");
        //获取耗时任务的返回结果，如果未返回，主线程将阻塞，处于等待状态
        String result = null;
        try {
            result = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        log.info("result：{}", result);

    }
}
