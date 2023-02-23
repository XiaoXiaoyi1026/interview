package xiaoxiaoyi.threadsafe;

import java.util.concurrent.CountDownLatch;

/**
 * volatile关键字修饰变量, 可以保证变量的可见性和有序性, 但不能保证原子性
 * 可见性: 一个线程对变量进行修改过后, 其余线程能够马上看见
 * 有序性: 针对一个操作, 可以保证执行顺序不被打乱
 * 原子性: 对于一个操作, 在它执行的过程中不会被另一个操作乱入
 *
 * @author xiaoxiaoyi
 * @version 1.0
 * @description volatile测试
 * @date 2/23/2023 3:40 PM
 */
public class AddAndSubtract {

    private static volatile int balance = 10;

    private static void add() {
        int b = balance;
        b += 5;
        balance = b;
    }

    private static void subtract() {
        int b = balance;
        b -= 5;
        balance = b;
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            add();
            countDownLatch.countDown();
        }).start();
        new Thread(() -> {
            subtract();
            countDownLatch.countDown();
        }).start();
        countDownLatch.await();
        System.out.println(balance);
    }

}
