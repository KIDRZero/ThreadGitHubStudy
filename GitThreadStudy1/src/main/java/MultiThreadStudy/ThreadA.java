package MultiThreadStudy;

public class ThreadA extends Thread {
    @Override
    public void run() {
        System.out.println("aaaaa");
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadA threadA = new ThreadA();
        threadA.join();
    }
}
