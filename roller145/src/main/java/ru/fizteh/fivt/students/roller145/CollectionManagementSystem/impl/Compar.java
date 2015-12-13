package ru.fizteh.fivt.students.roller145.CollectionManagementSystem.impl;

import java.util.Comparator;

/**
 * Created by riv on 01.12.2015.
 */
public class Compar<T> implements Comparator<T> {
    private Comparator<T> [] compars;
    @SafeVarargs
    public Compar(Comparator<T>... comparators) {
        this.compars = comparators;
    }

    @Override
    public int compare(T first, T second) {
        for (Comparator<T> comparator : compars) {
            if (comparator.compare(first, second) != 0) {
                return comparator.compare(first, second);
            }
        }
        return 0;
    }
}
