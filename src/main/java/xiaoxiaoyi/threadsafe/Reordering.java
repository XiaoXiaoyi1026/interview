package xiaoxiaoyi.threadsafe;

import org.jetbrains.annotations.NotNull;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

/**
 * java -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation -jar jcstress.jar -t xiaoxiaoyi.threadsafe.Reordering.Case1
 * java -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation -jar jcstress.jar -t xiaoxiaoyi.threadsafe.Reordering.Case2
 * java -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation -jar jcstress.jar -t xiaoxiaoyi.threadsafe.Reordering.Case3
 *
 * @author xiaoxiaoyi
 * @version 1.0
 * @description volatile有序性
 * @date 2/23/2023 4:41 PM
 */
public class Reordering {

    @JCStressTest
    @Outcome(id = {"0, 0", "1, 1", "0, 1"}, expect = Expect.ACCEPTABLE, desc = "ACCEPTABLE")
    @Outcome(id = "1, 0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "INTERESTING")
    @State
    public static class Case1 {
        int x;
        int y;

        /**
         * Actor注解修饰的线程, 压测进行时会被单独线程执行
         */
        @Actor
        public void actor1() {
            x = 1;
            y = 1;
        }

        @Actor
        public void actor2(@NotNull II_Result r) {
            r.r1 = y;
            r.r2 = x;
        }
    }

    @JCStressTest
    @Outcome(id = {"0, 0", "1, 1", "0, 1"}, expect = Expect.ACCEPTABLE, desc = "ACCEPTABLE")
    @Outcome(id = "1, 0", expect = Expect.FORBIDDEN, desc = "FORBIDDEN")
    @State
    public static class Case2 {
        int x;
        volatile int y;

        /**
         * Actor注解修饰的线程, 压测进行时会被单独线程执行
         */
        @Actor
        public void actor1() {
            x = 1;
            y = 1;
        }

        @Actor
        public void actor2(@NotNull II_Result r) {
            r.r1 = y;
            r.r2 = x;
        }
    }

    @JCStressTest
    @Outcome(id = {"0, 0", "1, 1", "0, 1"}, expect = Expect.ACCEPTABLE, desc = "ACCEPTABLE")
    @Outcome(id = "1, 0", expect = Expect.FORBIDDEN, desc = "FORBIDDEN")
    @State
    public static class Case3 {
        volatile int x;
        int y;

        /**
         * Actor注解修饰的线程, 压测进行时会被单独线程执行
         */
        @Actor
        public void actor1() {
            x = 1;
            y = 1;
        }

        @Actor
        public void actor2(@NotNull II_Result r) {
            r.r1 = y;
            r.r2 = x;
        }
    }

}
