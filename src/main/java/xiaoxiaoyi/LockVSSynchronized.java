package xiaoxiaoyi;

/**
 * synchronized是java语言中的一个关键字, 类似于new, public等, 其锁功能是由C++在java底层中实现的
 * Lock是java语言实现的一个类, 锁功能由java语言定义, 提供很多方法
 * synchronized锁会自动释放, Lock则需要手动调用unlock方法进行释放
 * 二者均属于悲观锁, 具备基本的互斥, 同步, 可重入特性
 * Lock提供了许多synchronized不具备的方法, 例如获取线程等待状态, 公平锁, 可打断, 可超时, 多条件变量(将线程按照条件划分)
 * Lock有适用于不同场景的实现, 如ReentrantLock, ReentrantReadWriteLock等
 * 在没有竞争时, synchronized作出了许多优化, 如偏向锁, 轻量级锁, 性能不赖
 * 在竞争激烈时, Lock的实现往往能提供更好的性能
 *
 * @author xiaoxiaoyi
 * @version 1.0
 * @description Lock类 VS synchronized关键字
 * @date 2/23/2023 2:58 PM
 */
public class LockVSSynchronized {
}
