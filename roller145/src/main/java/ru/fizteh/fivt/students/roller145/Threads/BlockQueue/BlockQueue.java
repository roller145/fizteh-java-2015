package ru.fizteh.fivt.students.roller145.Threads.BlockQueue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by riv on 13.12.15.
 */
public class BlockQueue<R> {
    private int maxCapacity;
    private Queue<R> queue;
    private final Lock lock;
    final Condition notFull;
    final Condition notEmpty ;

    BlockQueue(int max){
        maxCapacity = max;
        lock = new ReentrantLock();
        queue = new ArrayDeque<>(maxCapacity);
        notEmpty = lock.newCondition();
        notFull  = lock.newCondition();
    }

    public void offer(List e) throws InterruptedException {
       offer(e, 0);
    }

    public List take(int n) throws InterruptedException {
        return take(n,0);
    }

    public void offer(List e, long timeout) throws InterruptedException {
        lock.lock();
        long whenLastUpdate = System.currentTimeMillis();
        boolean isTimeLimit = true;
        if (timeout ==0)  {
            isTimeLimit = false;
        }
        long currentTime = System.currentTimeMillis();
        try {
            while (queue.size() + e.size() > maxCapacity) {
                currentTime = System.currentTimeMillis();
                if(isTimeLimit) {
                    timeout -= currentTime - whenLastUpdate;
                    whenLastUpdate = currentTime;
                    notFull.await(timeout, TimeUnit.MILLISECONDS);
                }
                else{
                    notFull.await();
                }
            }
            queue.addAll(e);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public List take(int n, long timeout) throws InterruptedException {
        List<R> result = new ArrayList<>();
        lock.lock();
        long whenLastUpdate = System.currentTimeMillis();
        boolean isTimeLimit = true;
        if (timeout ==0)  {
            isTimeLimit = false;
        }
        long currentTime = System.currentTimeMillis();
        try {
            while (queue.size() < n) {
                currentTime = System.currentTimeMillis();
                if(isTimeLimit) {
                    timeout -= currentTime - whenLastUpdate;
                    whenLastUpdate = currentTime;
                    notFull.await(timeout, TimeUnit.MILLISECONDS);
                }
                else{
                    notFull.await();
                }
            }
            for (int i = 0; i < n; ++i) {
                result.add(queue.remove());
            }
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
        return result;
    }
}
