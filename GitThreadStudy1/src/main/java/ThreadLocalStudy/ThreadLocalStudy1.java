package ThreadLocalStudy;

public class ThreadLocalStudy1 {
    public static ThreadLocal<String> threadLocal=new ThreadLocal<>();

    /**
     * 调用该方法的必然是某个线程
     * @param str
     */
    public static void print(String str){
        //打印当前线程中本地内存中本地变量的值
        System.out.println(str+":"+threadLocal.get());
        //清除本地内存中的本地变量
        threadLocal.remove();
    }

    public static void main(String[] args) {
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("thread1 local variable");
                print("thread1");
                //打印本地变量
                System.out.println("after remove:"+threadLocal.get());
            }
        });
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("thread2 local variable");
                print("thread2");
                //打印本地变量
                System.out.println("after remove:"+threadLocal.get());
            }
        });
        t1.start();
        t2.start();
    }
}
