package multiThreadLeetCode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
* Suppose you are given the following code:

class FooBar {
  public void foo() {
  for (int i = 0; i < n; i++) {
   print("foo");
   }
  }

  public void bar() {
  for (int i = 0; i < n; i++) {
   print("bar");
  }
  }
}
The same instance of FooBar will be passed to two different threads.
* Thread A will call foo() while thread B will call bar().
* Modify the given program to output "foobar" n times.

Example 1:
Input: n = 1
Output: "foobar"
Explanation: There are two threads being fired asynchronously. One of them calls foo(), while the other calls bar(). "foobar" is being output 1 time.

* Example 2:
Input: n = 2
Output: "foobarfoobar"
Explanation: "foobar" is being output 2 times.

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/print-foobar-alternately
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */
public class FooBar {
    private int n;
    private Lock lock = new ReentrantLock();
    private Condition fooCon = lock.newCondition();
    private Condition barCon = lock.newCondition();
    private int count = 1;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                if (count != 1) {
                    barCon.await();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                count = 2;
                fooCon.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                if (count != 2) {
                    fooCon.await();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printBar.run();
                barCon.signal();
                count = 1;
            } finally {
                lock.unlock();
            }
        }
    }
}