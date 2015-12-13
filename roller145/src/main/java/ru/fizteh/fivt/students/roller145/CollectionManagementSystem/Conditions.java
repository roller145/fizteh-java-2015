package ru.fizteh.fivt.students.roller145.CollectionManagementSystem;

/**
 * Created by riv on 17.11.2015.
 */

import java.util.function.Function;
import java.util.function.Predicate;


public class Conditions<T> {
    /**
     * то же самое, но с регулярными выражениями
     */
    public static <T> Predicate<T> rlike(Function<T, String> expression, String regexp) {
        return element -> expression.apply(element).matches(regexp);
    }

    /**
     * возвращает предикат и забирает строку
     * % ?
     */
    public static <T> Predicate<T> like(Function<T, String> expression, String pattern) {
        throw new UnsupportedOperationException();
    }

}
