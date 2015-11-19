package ru.fizteh.fivt.students.roller145.CollectionManagementSystem.impl;

/**
 * Created by User on 17.11.2015.
 */

import java.util.stream.Stream;

public interface Query<R> {

    Iterable<R> execute();

    Stream<R> stream();
}
