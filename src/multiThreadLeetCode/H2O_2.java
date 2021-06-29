package multiThreadLeetCode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class H2O_2 {

    volatile int hNum = 0;
    Lock lock = new ReentrantLock();
    Condition oReady = lock.newCondition();
    Condition hReady = lock.newCondition();

    public H2O_2() {
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        lock.lock();
        try {
            while (hNum == 2) {
                hReady.signal();
                oReady.wait();
            }
            releaseHydrogen.run();
            hNum++;
            if (hNum == 2) {
                hReady.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        lock.lock();
        try {
            while (hNum != 2) {
                hReady.wait();
            }
            releaseOxygen.run();
            hNum = 0;
            oReady.signalAll();
        } finally {
            lock.unlock();
        }
    }
}