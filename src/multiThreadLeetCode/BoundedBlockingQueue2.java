package multiThreadLeetCode;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBlockingQueue2 {
    int capacity;
    LinkedList<Integer> list;
    Lock lock = new ReentrantLock();
    Condition empty = lock.newCondition();
    Condition full = lock.newCondition();

    public BoundedBlockingQueue2(int capacity) {
        this.capacity = capacity;
        list = new LinkedList<>();
    }

    public void enqueue(int element) throws InterruptedException {
        try {
            lock.lock();
            while (list.size() == capacity) {
                full.await();
            }
            list.push(element);
            empty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int dequeue() throws InterruptedException {
        int element = 0;
        try {
            lock.lock();
            while (list.isEmpty()) {
                empty.await();
            }
            element = list.removeLast();
            full.signal();
        } finally {
            lock.unlock();
        }
        return element;
    }

    public int size() {
        return list.size();
    }

}