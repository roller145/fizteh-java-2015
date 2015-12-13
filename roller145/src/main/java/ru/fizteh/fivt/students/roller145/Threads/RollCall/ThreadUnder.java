package ru.fizteh.fivt.students.roller145.Threads.RollCall;

import ru.fizteh.fivt.students.roller145.Threads.ThreadWithName;

import java.util.Random;

/**
 * Created by riv on 13.12.15.
 */

public class ThreadUnder extends Thread {
    private Random rand = new Random();
    public boolean lastCallResult;
    public RollCallGroup group;

    ThreadUnder(RollCallGroup it){
        group = it;
    }

    @Override
    public void run(){
        while (!group.isFinished) {
            if (rand.nextInt(10) <= 9) {
                lastCallResult = true;
                System.out.println("YES");
            } else {
                lastCallResult = false;
                System.out.println("NO");
            }
            try {
                group.bar.await();
            } catch (Exception e) {
            }
        }
    }
}