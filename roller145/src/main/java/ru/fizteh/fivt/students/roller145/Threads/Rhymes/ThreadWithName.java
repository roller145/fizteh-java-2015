package ru.fizteh.fivt.students.roller145.Threads.Rhymes;

import java.util.Objects;

/**
 * Created by riv on 13.12.15.
 */

public class ThreadWithName extends Thread {
    public  Integer name;
    public PlayingThreads parent;

    public ThreadWithName(Integer n, PlayingThreads p){
        name = n;
        parent = p;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            synchronized (parent.getClass()) {
                if (Objects.equals(parent.whoseTurn, name)) {
                    System.out.println("Thread - " + name.toString());
                    parent.whoseTurn = (name + 1) % parent.quantity;
                    this.getClass().notifyAll();
                }
            }
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.print("Bad news");
                e.printStackTrace();
            }
        }
    }
}
