package multiThread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ArrayBlockingQueueTest {
    protected static final ExecutorService pool = Executors.newCachedThreadPool();
    //栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行
    protected CyclicBarrier barrier;
    protected final ArrayBlockingQueue<Integer> bb;
    protected final int nTrials, nPairs;
    //入列总和
    protected final AtomicInteger putSum = new AtomicInteger(0);
    //出列总和
    protected final AtomicInteger takeSum = new AtomicInteger(0);

    @Test
    public void testArrayBlock_correctcess() {
        new ArrayBlockingQueueTest(10, 10, 100000).test(); // 10个承载因子，10个线程，运行100000
        pool.shutdown();
    }

    public ArrayBlockingQueueTest(int capacity, int npairs, int ntrials) {
        this.bb = new ArrayBlockingQueue<>(capacity);
        this.nTrials = ntrials;
        this.nPairs = npairs;
        this.barrier = new CyclicBarrier(npairs * 2 + 1);
    }

    void test() {
        try {
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            /*
            * CyclicBarrier叫循环栅栏，它实现让一组线程等待至某个状态之后再全部 同时执行，而且当所有等待线程被释放后，
            * CyclicBarrier可以被重复使 用。CyclicBarrier的典型应用场景是用来等待并发线程结束。
            * CyclicBarrier 的主要方法是await()，await。每被调用一次，计数便会减少1,并阻塞住 当前线程。
            * 当计数减至0时，阻塞解除，所有在此CyclicBarrier上面阻塞 的线程开始运行。
            在这之后，如果再次调用await。，计数就又会变成N-1，新一轮重新开 始，
            * 这便是Cyclic的含义所在。CyclicBarrier.await()带有返回值，用来表 示当前线程是第几个到达这个Barrier的线程。*/
            barrier.await(); // 等待所有的线程就绪
            barrier.await(); // 等待所有的线程执行完成
            System.out.println("result，put==take :" + (putSum.get() == takeSum.get()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    //生产者
    class Producer implements Runnable {
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int) System.nanoTime());
                int sum = 0;
                barrier.await();
                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }

                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    //消费者
    class Consumer implements Runnable {
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class BarrierTimer implements Runnable {
        private volatile boolean started;
        private volatile long startTime, endTime;

        @Override
        public synchronized void run() {
            long t = System.nanoTime();
            if (!started) {
                started = true;
                startTime = t;
            } else
                endTime = t;
        }

        public void clear() {
            started = false;
        }

        public synchronized long getTime() {
            return endTime - startTime;
        }

    }
}