package ru.fizteh.fivt.students.roller145.CollectionManagementSystem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by riv on 17.11.2015.
 */
public class Count<T> implements Agregator<T, Integer> {

    private Function<T, ?> function;
    public Count(Function<T, ?> expression) {
        this.function = expression;
    }

    @Override
    public Integer apply(List<T> elements) {
        Set<Object> distincted = new HashSet<>();
        for (T element : elements) {
            if (!distincted.contains(function.apply(element))) {
                distincted.add(function.apply(element));
            }
        }
        return distincted.size();
    }
    @Override
    public Integer apply(T t) {
        return null;
    }
}