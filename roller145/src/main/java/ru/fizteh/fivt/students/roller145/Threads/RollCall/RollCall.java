package ru.fizteh.fivt.students.roller145.Threads.RollCall;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by riv on 13.12.15.
 */
public class RollCall {

    public static void main(String[] args){
        System.out.print("Number: ");
        Scanner sc = new Scanner(System.in);
        Integer number = sc.nextInt();

        if (number <= 0) throw new IllegalArgumentException();
        RollCallGroup group= new RollCallGroup(number);
        group.roll();

    }

}
