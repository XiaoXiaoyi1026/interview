package xiaoxiaoyi;

import java.time.LocalDateTime;

/**
 * @author xiaoxiaoyi
 * @version 1.0
 * @description 比较wait(), wait(long), sleep(long)的区别
 * @date 2/23/2023 2:09 PM
 */
public class WaitVSSleep {

    static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        sleeping();
    }

    private static void illegalWait() throws InterruptedException {
//        在未获取对象锁的情况下, 调用对象的wait方法会报IllegalMonitorStateException
//        LOCK.wait();
//        先获取对象锁, 然后再调用wait即可
        synchronized (LOCK) {
            LOCK.wait();
        }
    }

    private static void waiting() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
//            获得对象锁
            synchronized (LOCK) {
                try {
//                    对象wait, 释放对象锁
                    System.out.println(LocalDateTime.now());
                    System.out.println("线程thread1获得对象LOCK的锁, waiting...");
                    LOCK.wait();
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException");
                    e.printStackTrace();
                }
            }
        }, "thread1");
        thread1.start();
//        主线程休眠0.1秒, 确保线程thread1能够拿到LOCK的对象锁
        Thread.sleep(100);
//        主线程尝试获取LOCK的对象锁, 可以获取成功, 因为对象在wait时会释放掉对象锁
        synchronized (LOCK) {
            System.out.println(LocalDateTime.now());
            System.out.println("线程main获得对象LOCK的锁, other...");
        }
    }

    private static void sleeping() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
//            获得对象锁
            synchronized (LOCK) {
                try {
                    System.out.println(LocalDateTime.now());
                    System.out.println("线程thread1获得对象LOCK的锁, sleeping...");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException");
                    e.printStackTrace();
                }
            }
        }, "thread1");
        thread1.start();
//        主线程休眠0.1秒, 确保线程thread1能够拿到LOCK的对象锁
        Thread.sleep(100);
//        main线程主动打断thread1线程的sleep状态即可立即获得锁
        thread1.interrupt();
//        主线程尝试获取LOCK的对象锁, 必须等待thread1线程休眠完成才能拿到
        synchronized (LOCK) {
            System.out.println(LocalDateTime.now());
            System.out.println("线程main获得对象LOCK的锁, other...");
        }
    }

}
