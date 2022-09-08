package MultiThreadStudy;

import java.util.concurrent.Executors;

public class ExecutorsTest {
    public static void test1(){
        Executors.newSingleThreadExecutor();
        Executors.newFixedThreadPool(10);
        Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool(10);
    }
}
