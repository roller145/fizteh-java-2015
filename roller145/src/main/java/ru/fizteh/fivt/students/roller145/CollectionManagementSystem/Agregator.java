package ru.fizteh.fivt.students.roller145.CollectionManagementSystem;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Riv on 17.11.2015.
 */
public interface Agregator<T, C> extends Function<T, C> {
    C apply(List<T> elements);
}
