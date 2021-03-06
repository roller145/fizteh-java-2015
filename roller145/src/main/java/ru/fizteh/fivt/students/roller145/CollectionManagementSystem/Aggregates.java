package ru.fizteh.fivt.students.roller145.CollectionManagementSystem;

/**
 * Created by Riv on 17.11.2015.
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Aggregates {
    /**
     * Maximum value for expression for elements of given collection.
     */
    public static <C, T extends Comparable<T>> Function<C, T> max(Function<C, T> expression) {
        return new Agregator<C, T>() {
            @Override
            public T apply(List<C> elements) {
                if (elements.isEmpty()) {
                    return null;
                }
                C result = elements.get(0);
                for (C element : elements) {
                    if (expression.apply(result).compareTo(expression.apply(element)) < 0) {
                        result = element;
                    }
                }
                return expression.apply(result);
            }

            @Override
            public T apply(C c) {
                return null;
            }
        };


    }

    /**
     * Minimum value for expression for elements of given collection
     */
    public static <C, T extends Comparable<T>> Function<C, T> min(Function<C, T> expression) {
        return new Agregator<C, T>() {
            @Override
            public T apply(List<C> elements) {
                if (elements.isEmpty()) {
                    return null;
                }
                C result = elements.get(0);
                for (C element : elements) {
                    if (expression.apply(result).compareTo(expression.apply(element)) > 0) {
                        result = element;
                    }
                }
                return expression.apply(result);
            }

            @Override
            public T apply(C c) {
                return null;
            }
        };
    }

    /**
     * Number of items in source collection that turns this expression into not null.
     */
    public static <C> Function<C, Integer> count(Function<C, Integer> expression) {
        return new Agregator<C, Integer>() {
            @Override
            public Integer apply(List<C> elements) {
                Set<Object> distincted = new HashSet<>();
                elements.stream().filter(element -> !distincted.contains(expression.apply(element))).forEach(element -> {
                    distincted.add(expression.apply(element));
                });
                return distincted.size();
            }

            @Override
            public Integer apply(C c) {
                return null;
            }
        };
    }

    /**
     * Average value for expression for elements of given collection.
     */
    public static <C, T extends Comparable<T>> Function<C, T> avg(Function<C, T> expression) {
        throw new UnsupportedOperationException();
    }

}
