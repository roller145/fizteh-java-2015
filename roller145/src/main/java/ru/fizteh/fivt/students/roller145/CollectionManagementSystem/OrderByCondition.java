package ru.fizteh.fivt.students.roller145.CollectionManagementSystem;

/**
 * Created by RIV on 17.11.2015.
 */

import java.util.Comparator;
import java.util.function.Function;

public class OrderByCondition {

    public static <T, R extends Comparable<R>> Comparator<T> asc(Function<T, R> expression) {
        return (o1, o2) -> expression.apply(o1).compareTo(expression.apply(o2));
    }


    public static <T, R extends Comparable<R>> Comparator<T> desc(Function<T, R> expression) {
        return (o1, o2) -> expression.apply(o2).compareTo(expression.apply(o1));
    }
}
