package ru.fizteh.fivt.students.roller145.Threads.Rhymes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by riv on 13.12.15.
 */
public class Rhymes {

    public static void main(String[] args) throws IOException {
        System.out.print("Number: ");
        Scanner sc = new Scanner(System.in);
        Integer number = sc.nextInt();

        if (number <= 0) throw new IllegalArgumentException();

        PlayingThreads pl = new PlayingThreads(number);
        List<ThreadWithName> threads = new ArrayList<>();
        for (int i = 0; i < number; ++i) {
            threads.add(new ThreadWithName(i, pl));
        }

        threads.forEach(ThreadWithName::run);
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threads.forEach(ThreadWithName::interrupt);

    }

}
