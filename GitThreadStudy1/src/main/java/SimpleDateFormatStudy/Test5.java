package SimpleDateFormatStudy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test5 {
    // 创建 ThreadLocal 对象，并设置默认值（new SimpleDateFormat）
    /**
     * 这里相当于初始化构建一下threadlocal，设置了线程的局部变量是个simpleDataFormat对象
     * 然后每个线程去访问，都会拿到这个变量的一个副本，这样多个线程其实操作的都是SimpleDataFormat的副本而已，当然也就不会出现问题了
     */
    private static ThreadLocal<SimpleDateFormat> threadLocal =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("mm:ss"));


    public static void main(String[] args) {
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        // 执行 10 次时间格式化
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            // 线程池执行任务
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    // 创建时间对象
                    Date date = new Date(finalI * 1000);
                    // 格式化时间
                    String result = threadLocal.get().format(date);
                    // 打印结果
                    System.out.println(result);
                }
            });
        }
        // 任务执行完之后关闭线程池
        threadPool.shutdown();
    }

}
