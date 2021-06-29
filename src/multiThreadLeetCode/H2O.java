package multiThreadLeetCode;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/* 1117
There are two kinds of threads, oxygen and hydrogen. Your goal is to group these threads to form water molecules.There is a barrier where each thread has towait until a complete molecule can be formed. Hydrogen and oxygen threads will be given releaseHydrogenand releaseOxygenmethods respectively, which will allow them to pass the barrier. These threads should pass the barrier in groups of three, and they must be able to immediately bond with each other to form a water molecule.You must guarantee that all the threads from one molecule bond before any other threads from the next molecule do.

In other words:

If an oxygen thread arrives at the barrier when no hydrogen threads are present, it has to wait for two hydrogen threads.
If a hydrogen thread arrives at the barrier when no other threads are present, it has to wait for an oxygen thread and another hydrogen thread.
We don’t have to worry about matching the threads up explicitly; that is, the threads do not necessarily know which other threads they are paired up with. The key is just that threads pass the barrier in complete sets; thus, if we examine the sequence of threads that bond and divide them into groups of three, each group should contain one oxygen and two hydrogen threads.

Write synchronization code for oxygen and hydrogen molecules that enforces these constraints.

Example 1:

Input: "HOH"
Output: "HHO"
Explanation: "HOH" and "OHH" are also valid answers.
Example 2:

Input: "OOHHHH"
Output: "HHOHHO"
Explanation: "HOHHHO", "OHHHHO", "HHOHOH", "HOHHOH", "OHHHOH", "HHOOHH", "HOHOHH" and "OHHOHH" are also valid answers.


Constraints:

Total length of input string will be 3n, where 1 ≤n≤ 20.
Total number of H will be 2nin the input string.
Total number of O willbe nin the inputstring.

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/building-h2o
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。*/

/*
* 执行用时：
23 ms
, 在所有 Java 提交中击败了
8.64%
的用户
内存消耗：
41.9 MB
, 在所有 Java 提交中击败了
21.76%
的用户*/
public class H2O {

    Semaphore hSem = new Semaphore(2);
    Semaphore oSem = new Semaphore(1);
    CyclicBarrier cb = new CyclicBarrier(3, () ->
    {
        hSem.release(2);
        oSem.release(1);
    });


    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hSem.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        try {
            cb.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oSem.acquire();
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        try {
            cb.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}