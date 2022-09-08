package volatileStudy;

/**
 * 通过volatile关键字来实现变量的修改在多个线程之间属于可见状态
 * 如果这里不加上volatile关键字，那么会导致线程1永远处于运行状态
 * 线程2修改变量flag的值对于线程1不可见
 */
public class Test {
    private static volatile boolean flag = false;
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 如果 flag 变量为 true 就终止执行
                while (!flag) {

                }
                System.out.println("终止执行");
            }
        });
        t1.start();
        // 1s 之后将 flag 变量的值修改为 true
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("设置 flag 变量的值为 true！");
                flag = true;
            }
        });
        t2.start();
    }
}
