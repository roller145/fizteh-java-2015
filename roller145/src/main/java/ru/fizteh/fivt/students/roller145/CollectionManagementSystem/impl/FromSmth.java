package ru.fizteh.fivt.students.roller145.CollectionManagementSystem.impl;

/**
 * Created by User on 17.11.2015.
 */

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

public class FromSmth<T> {
    public static <T> FromSmth<T> from(Iterable<T> iterable) {
        throw new UnsupportedOperationException();
    }

    public static <T> FromSmth<T> from(Stream<T> stream) {
        throw new UnsupportedOperationException();
    }

    public static <T> FromSmth<T> from(Query query) {
        throw new UnsupportedOperationException();
    }

    @SafeVarargs
    public final <R> SelectSmth<T, R> select(Class<R> clazz, Function<T, ?>... s) {
        throw new UnsupportedOperationException();
    }

    /**
     * Selects the only defined expression as is without wrapper.
     */
    public final <R> SelectSmth<T, R> select(Function<T, R> s) {
        throw new UnsupportedOperationException();
    }

    /**
     * Selects the only defined expression as is without wrapper.
     */
    public final <F, S> SelectSmth<T, Tuple<F, S>> select(Function<T, F> first, Function<T, S> second) {
        throw new UnsupportedOperationException();
    }

    @SafeVarargs
    public final <R> SelectSmth<T, R> selectDistinct(Class<R> clazz, Function<T, ?>... s) {
        throw new UnsupportedOperationException();
    }

    /**
     * Selects the only defined expression as is without wrapper.
     */
    public final <R> SelectSmth<T, R> selectDistinct(Function<T, R> s) {
        throw new UnsupportedOperationException();
    }

    public <J> JoinClause<T, J> join(Iterable<J> iterable) {
        throw new UnsupportedOperationException();
    }

    public <J> JoinClause<T, J> join(Stream<J> stream) {
        throw new UnsupportedOperationException();
    }

    public <J> JoinClause<T, J> join(Query<J> stream) {
        throw new UnsupportedOperationException();
    }

    public class JoinClause<T, J> {

        public FromSmth<Tuple<T, J>> on(BiPredicate<T, J> condition) {
            throw new UnsupportedOperationException();
        }

        public <K extends Comparable<?>> FromSmth<Tuple<T, J>> on(
                Function<T, K> leftKey,
                Function<J, K> rightKey) {
            throw new UnsupportedOperationException();
        }
    }

}
