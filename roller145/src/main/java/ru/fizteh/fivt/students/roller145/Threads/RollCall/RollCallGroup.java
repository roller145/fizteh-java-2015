package ru.fizteh.fivt.students.roller145.Threads.RollCall;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by riv on 13.12.15.
 */
public class RollCallGroup {
    public int quantity;
    public boolean isFinished;
    public CyclicBarrier bar;
    List<ThreadUnder> threads = new ArrayList<>();
    public final String ARE_YOU_READY = "Are you ready?";

    RollCallGroup(int n){
        quantity = n;
        isFinished = false;
        bar = new CyclicBarrier(quantity + 1, () -> {
            isFinished = true;
            for (ThreadUnder answeringThread : threads) {
                isFinished &= answeringThread.lastCallResult;
            }
            if (!isFinished) {
                System.out.println(ARE_YOU_READY);
            }
        });
    }
    public void roll() {
        System.out.println(ARE_YOU_READY);
        for (int i = 0; i < quantity; ++i) {
            threads.add(new ThreadUnder(this));
            threads.get(i).start();
        }
        while (!isFinished) {
            try {
                bar.await();
            } catch (Exception e) {
            }
        }
    }


}
