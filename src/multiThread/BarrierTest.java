package multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarrierTest {
    public static class Barrier {
        private final int numOfWorkers;
        private Semaphore sem = new Semaphore(0);
        private int counter = 0;
        private Lock lock = new ReentrantLock();
        private AtomicInteger counter2 = new AtomicInteger(0);

        public Barrier(int numOfWorkers) {
            this.numOfWorkers = numOfWorkers;
        }

        public void barrier() {
            lock.lock();
            boolean isLastWorker = false;
            try {
                counter++;
                if (counter == numOfWorkers) {
                    isLastWorker = true;
                }

            } finally {
                lock.unlock();
            }
            if (isLastWorker) {
                // release num-1 since there are num-1 threads are blocked by the semaphore
                sem.release(numOfWorkers - 1);
            } else {
                try {
                    sem.acquire();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " is interrupted");
                }
            }
        }

        public void barrier2() {
            boolean isLastWorker = false;
            if (counter2.incrementAndGet() == numOfWorkers) {
                isLastWorker = true;
            }

            if (isLastWorker) {
                // release num-1 since there are num-1 threads are blocked by the semaphore
                sem.release(numOfWorkers - 1);
            } else {
                try {
                    sem.acquire();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " is interrupted");
                }
            }
        }
    }

    public static class CoordinatedWorkRunner implements Runnable {
        private Barrier barrier;

        public CoordinatedWorkRunner(Barrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                task();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
            }
        }

        public void task() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " part 1");

             barrier.barrier();
            //barrier.barrier2();
            System.out.println(Thread.currentThread().getName() + " part 2");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numOfThreads = 12;
        List<Thread> threads = new ArrayList<>();
        Barrier barrier = new Barrier(numOfThreads);
        for (int i = 0; i < numOfThreads; i++) {
            threads.add(new Thread(new CoordinatedWorkRunner(barrier)));
        }
        long start = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        long end = System.currentTimeMillis();
        System.out.println("used: " + (end-start) + " ms");
    }


}