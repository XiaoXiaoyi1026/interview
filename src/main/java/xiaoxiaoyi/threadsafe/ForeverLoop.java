package xiaoxiaoyi.threadsafe;

/**
 * JIT: java即时代码编译器, 在代码编译阶段, jit会判断该段代码是否需要进行编译(热点代码)
 * 如果该段代码同样编译结果的执行次数超过了一定阈值, 那么jit不会再对改代码进行编译
 * 而是直接将该段代码的机器码存入CodeCache中, 并且后续执行都从CodeCache中读取该段代码的机器码执行
 * 程序中stop的值一直为false, 假设阈值为100万, 那么在foo()方法中的while循环执行超过100万次
 * 且每次读取stop的值都为false, 那么jit会将该段代码的机器码直接存入CodeCache
 * 下次执行不会再去物理内存中读取stop的值, 而是直接从CodeCache中读机器码去执行
 * 进而导致其他线程对stop的修改对于执行foo()这个方法的线程是不可见的
 * -Xint 指定JVM执行代码时只用解释执行, 不用JIT进行优化
 *
 * @author xiaoxiaoyi
 * @version 1.0
 * @description 测试volatile可见性
 * @date 2/23/2023 4:03 PM
 */
public class ForeverLoop {

    static volatile boolean stop = false;

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop = true;
            System.out.println("Modify stop to true...");
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(stop);
        }).start();

        foo();
    }

    static void foo() {
        int i = 0;
        while (!stop) {
            i++;
        }
        System.out.println("loop stop..., i: " + i);
    }

}
