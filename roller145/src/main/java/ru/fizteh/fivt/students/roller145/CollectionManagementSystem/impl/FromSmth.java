package ru.fizteh.fivt.students.roller145.CollectionManagementSystem.impl;

/**
 * Created by riv on 17.11.2015.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FromSmth<T> {
    private List<T> elements;

    public FromSmth() {
        elements = new ArrayList<T>();
    }

    public FromSmth( Iterable<T> iterable) {
        elements = new ArrayList<T>();
        for ( T element : iterable) {
            elements.add(element);
        }
    }

    public static <T> FromSmth<T> from(Iterable<T> iterable) {
        return new FromSmth<>(iterable);
    }

    public FromSmth(Stream<T> stream) {
        elements = new ArrayList<T>();
        stream.forEach(element->elements.add(element));
    }

    public static <T> FromSmth<T> from(Stream<T> stream) {
        return new FromSmth<>(stream);
    }

    @SafeVarargs
    public final <R> SelectSmth<T, R> select(Class<R> clazz, Function<T, ?>... s) {
        return new SelectSmth<T,R>(elements, clazz, false, s);
    }
    @SafeVarargs
    public final <R> SelectSmth<T, R> selectDistinct(Class<R> returnClass, Function<T, ?>... functions) {
        return new SelectSmth<T,R>(elements, returnClass, true, functions);
    }

    public final <F, S> SelectSmth<T, Tuple<F, S>> select(Function<T, F> first, Function<T, S> second) {
        return new SelectSmth<T, Tuple<F, S>>(elements, false, first, second);
    }

    public <J> JoinClause<T, J> join(Iterable<J> iterable) {
        return new JoinClause<T, J>(elements, iterable);
    }

    public class JoinClause<S, J> {

        private List<S> firstElements = new ArrayList<>();
        private List<J> secondElements = new ArrayList<>();
        private List<Tuple<S, J>> elements = new ArrayList<>();

        public JoinClause(List<S> firstElements, Iterable<J> secondElements) {
            this.firstElements.addAll(firstElements.stream().collect(Collectors.toList()));
            for (J curr : secondElements) {
                this.secondElements.add(curr);
            }
        }

        public FromSmth<Tuple<S, J>> on(BiPredicate<S, J> condition) {
            for (S first : firstElements) {
                for (J second : secondElements) {
                    if (condition.test(first, second)) {
                        elements.add(new Tuple<>(first, second));
                    }
                }
            }
            return new FromSmth<>(elements);
        }

        public <K extends Comparable<?>> FromSmth<Tuple<S, J>> on(
                Function<S, K> leftKey,
                Function<J, K> rightKey) {
            throw new UnsupportedOperationException();
        }
    }
}
