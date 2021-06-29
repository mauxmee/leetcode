package multiThreadLeetCode;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers2 {
    //1个Fork视为1个ReentrantLock，5个叉子即5个ReentrantLock，将其都放入数组中
    private final ReentrantLock[] forkList = {new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock()};

    private static final int NUM_PHILOSOPHERS = 5;
    private final Semaphore eatLimit = new Semaphore(NUM_PHILOSOPHERS - 1);

    public DiningPhilosophers2() {
    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher) throws InterruptedException {
        eatLimit.acquire();
        int leftFork = (philosopher + 1) % NUM_PHILOSOPHERS;
        int rightFork = philosopher % NUM_PHILOSOPHERS;

        forkList[leftFork].lock();
        forkList[rightFork].lock();
        //simulate pickup fork, eat and put down forks
        int duration = ThreadLocalRandom.current().nextInt(100);
        Thread.sleep(duration);
        forkList[leftFork].unlock();
        forkList[rightFork].unlock();

        eatLimit.release();
    }
}