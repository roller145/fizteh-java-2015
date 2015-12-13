package ru.fizteh.fivt.students.roller145.CollectionManagementSystem.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by riv on 17.11.2015.
 */
public class UnionSmth<T, R> {

    private List<R> pastElements = new ArrayList<>();
    private List<Tuple<T, R>> pastTupleElements = new ArrayList<>();
    private List<T> elements = new ArrayList<>();
    private boolean isTuple;

    public List<R> getPastElements() {
        return pastElements;
    }

    public List<T> getElements() {
        return elements;
    }

    public UnionSmth(Iterable<R> iterable) {
        for (R curr : iterable) {
            pastElements.add(curr);
        }
        this.isTuple = false;
    }

    public UnionSmth(Iterable<R> iterable, boolean isTuple) {
        for (R curr : iterable) {
            pastElements.add(curr);
        }
        this.isTuple = true;
    }

    public <S> FromClause<S, R> from(Iterable<S> elements) {
        if (isTuple) {
            return new FromClause<S, R>(pastElements, elements);
        } else {
            return new FromClause<S, R>(pastElements, /*(Iterable<T>)*/ elements);
        }
    }

    public class FromClause<S, R> {
        public List<R> getPastElements() {
            return pastElements;
        }
        private List<R> pastElements = new ArrayList<>();

        public List<S> getElements() {
            return elements;
        }
        private List<S> elements = new ArrayList<>();

        public FromClause(Iterable<R> pastElements, Iterable<S> elements) {
            for (R curr : pastElements) {
                this.pastElements.add(curr);
            }
            for (S curr : elements) {
                this.elements.add(curr);
            }
        }
        @SafeVarargs
        public final SelectSmth<S, R> select(Class<R> returnClass, Function<S, ?>... functions) {
            return new SelectSmth<S, R>((List<R>) pastElements, elements,returnClass,false, functions);
        }

        public final <F, Z> SelectSmth<S, Tuple<F, Z>> select(Function<S, F> first, Function<S, Z> second) {
            return new SelectSmth<S, Tuple<F, Z>>((List<Tuple<F, Z>>) pastElements, elements, false, first, second);
        }

        @SafeVarargs
        public final SelectSmth<S, R> selectDistinct(Class<R> returnClass, Function<S, ?>... functions) {
            return new SelectSmth<S, R>((List<R>) pastElements, elements, returnClass, true, functions);
        }

        public <J> JoinClause<R, S, J> join(Iterable<J> iterable) {
            return new JoinClause<R, S, J>(pastElements, elements, iterable);
        }
    }

    public class JoinClause<R, F, J> {

        private List<F> firstElements = new ArrayList<> ();
        private List<J> secondElements = new ArrayList<>();
        private List<R> pastElements = new ArrayList<>();
        private List<Tuple<F, J>> elements = new ArrayList<>();

        public JoinClause(List<R> pastElements, List<F> firstElements, Iterable<J> secondElements) {
            this.pastElements.addAll(pastElements.stream().collect(Collectors.toList()));
            this.firstElements.addAll(firstElements.stream().collect(Collectors.toList()));
            for (J curr : secondElements) {
                this.secondElements.add(curr);
            }
        }

        public FromClause<Tuple<F, J>, R> on(BiPredicate<F, J> condition) {
            for (F first : firstElements) {
                for (J second : secondElements) {
                    if (condition.test(first, second)) {
                        elements.add(new Tuple<>(first, second));
                    }
                }
            }
            return new FromClause<>(pastElements, elements);
        }

    }
}
