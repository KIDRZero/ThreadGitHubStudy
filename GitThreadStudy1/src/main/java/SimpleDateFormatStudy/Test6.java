package SimpleDateFormatStudy;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test6 {
    // 创建 DateTimeFormatter 对象
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("mm:ss");

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
                    // 将 Date 转换成 JDK 8 中的时间类型 LocalDateTime
                    LocalDateTime localDateTime =
                            LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                    // 时间格式化
                    String result = dateTimeFormatter.format(localDateTime);
                    // 打印结果
                    System.out.println(result);
                }
            });
        }
        // 任务执行完之后关闭线程池
        threadPool.shutdown();
    }

}
