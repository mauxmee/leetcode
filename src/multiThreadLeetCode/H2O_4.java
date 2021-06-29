package multiThreadLeetCode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class H2O_4 {

    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();
    private int countO = 0;
    private int countH = 0;


    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        try {
            lock.lock();
            while (countH == 2) {
                cond.await();
            }
            countH++;
            if (countH == 2 && countO == 1) {
                countH = 0;
                countO = 0;
            }
            // releaseHydrogen.run() outputs "H". Do not change or remove this line.
            releaseHydrogen.run();
            cond.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        try {
            lock.lock();
            while (countO == 1) {
                cond.await();
            }
            countO++;
            if (countH == 2 && countO == 1) {
                countH = 0;
                countO = 0;
            }
            // releaseOxygen.run() outputs "O". Do not change or remove this line.
            releaseOxygen.run();
            cond.signalAll();
        } finally {
            lock.unlock();
        }

    }

}