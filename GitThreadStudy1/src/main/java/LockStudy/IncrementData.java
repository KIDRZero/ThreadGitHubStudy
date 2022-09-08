package LockStudy;

import java.util.concurrent.locks.Lock;

public class IncrementData {
    public static int sum=0;

    /**
     * 对临界区使用锁的代码进行了抽取和封装
     * @param lock
     */
    public static void lockAndFastIncrease(Lock lock){
        lock.lock();
        try {
            //执行临界区代码
            sum++;
        }finally {

        }
        lock.unlock();
    }
}
