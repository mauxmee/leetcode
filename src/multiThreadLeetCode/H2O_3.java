package multiThreadLeetCode;

import java.util.concurrent.Semaphore;
/*状态：通过
执行用时: 20 ms
内存消耗: 41.9 MB
*/
public class H2O_3 {
    private Semaphore hSem = new Semaphore(2);
    private Semaphore oSem = new Semaphore(1);
    volatile int hNum = 0;

    public H2O_3() {
    }


    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hSem.acquire();
        releaseHydrogen.run();
        hNum++;
        if (hNum == 2) {
            oSem.release();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oSem.acquire();
        releaseOxygen.run();
        hNum = 0;
        hSem.release(2);
    }
}