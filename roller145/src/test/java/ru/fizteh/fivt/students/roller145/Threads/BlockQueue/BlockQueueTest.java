package ru.fizteh.fivt.students.roller145.Threads.BlockQueue;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by riv on 13.12.15.
 */
public class BlockQueueTest extends TestCase {

    public void testTake() throws Exception {
        BlockQueue <String> queue = new BlockQueue<>(10);
        Runnable syncThread = new Runnable() {
            private Random rand = new Random();
            @Override
            public void run() {
                int num= rand.nextInt(500);
                try{
                    queue.take(num, 100);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            Thread t = new Thread(syncThread, Integer.toString(i));
            ts.add(t);
        }
        for (int i = 0; i < 100; ++i) {
            ts.get(i).start();
        }
    }


}