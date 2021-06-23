package multiThread;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.*;

public class CyclicBarrierTest {
    private static int SIZE = 5;
    private static CyclicBarrier cb;

    @Test
    public void test_cyclicBarrier() throws InterruptedException {
        cb = new CyclicBarrier(SIZE);
        for (int i = 0; i < SIZE; i++) {
            new MyTask().start();
        }
        Thread.sleep(5000);
    }

    static class MyTask extends Thread {
        @Override
        public void run() {
            try {
                String name = Thread.currentThread().getName();
                System.out.println(name + " is running");
                Thread.sleep(1000);
                System.out.println(name + " done. waiting for other threads");
                cb.await();

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(" all threads done");
        }
    }

    static class MyTask2 extends Thread {
        private CyclicBarrier cb;

        public MyTask2(CyclicBarrier cb) {
            this.cb = cb;
        }

        @Override
        public void run() {
            int time = ThreadLocalRandom.current().nextInt(1000);
            var id = Thread.currentThread().getId();
            System.out.println(id + ": need " + time + " ms");
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(id + ": done. waiting for other threads");
            try {
                cb.await(2, TimeUnit.SECONDS);
            } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
                e.printStackTrace();
            }
            System.out.println(id + ": all done");
        }
    }

    @Test
    public void test_writeDataWaitTimerOut() throws InterruptedException {
        final int NUM_THREAD = 5;
        CyclicBarrier cb = new CyclicBarrier(NUM_THREAD, () -> System.out.println(Thread.currentThread().getId() + " all threads done"));
        for (int i = 0; i < NUM_THREAD; i++) {
            if (i < NUM_THREAD - 1) {
                new MyTask2(cb).start();
            } else {
                Thread.sleep(3000);
                new MyTask2(cb).start();
            }
        }
        Thread.sleep(10000);
    }
}