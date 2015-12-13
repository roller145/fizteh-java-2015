package ru.fizteh.fivt.students.roller145.CollectionManagementSystem.impl;

/**
 * Created by riv on 17.11.2015.
 */

import javafx.util.Pair;
import ru.fizteh.fivt.students.roller145.CollectionManagementSystem.Agregator;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SelectSmth<T, R>  {
    private List<T> elements;
    private Class<R> resultClass;

    private Function<T, ?> [] functions;
    private Function<T,?> []  groupByFunc;
    private Predicate<T> werePredicate;
    private Predicate<R> havingPredicate;

    private Compar<R> comparators;

    private Boolean isDistinct;
    private Boolean isJoin;
    private Boolean isUnion;
    private Boolean hasAggregators;
    private Integer limitRange = Integer.MAX_VALUE;
    private List<R> pastElements;

    @SafeVarargs
    public SelectSmth (List<T> list, Class<R> returnClass, boolean distinct, Function<T, ?>... func) {
        isDistinct = distinct;
        elements = list;
        resultClass = returnClass;
        functions = func;
        hasAggregators = false;
        for (Function<T, ?> i: func) {
            if (i instanceof Agregator) {
                hasAggregators = true;
                break;
            }
        }
    }

    public SelectSmth (List<T> list, boolean distinct, Function<T, ?> first,
                       Function<T, ?> second) {
        isDistinct = distinct;
        elements = list;
        resultClass =  (Class<R>) elements.get(0).getClass();
        functions = new Function[]{first, second};
        hasAggregators = false;
        for (Function<T, ?> i: functions) {
            if (i instanceof Agregator) {
                hasAggregators = true;
                break;
            }
        }
    }

    public SelectSmth(List<R> pastElements, List<T> elements, Class<R> returnClass, boolean isDistinct, Function<T, ?> ... func) {
        this.elements = new ArrayList<>();
        for (T element : elements) {
            //System.out.println(element.toString());
            this.elements.add(element);
        }
        this.resultClass = returnClass;
        this.isDistinct = isDistinct;
        this.functions = func;
        this.isUnion= true;
        this.isJoin = true;
        this.pastElements = pastElements;
    }

    public SelectSmth(List<R> pastElements, List<T> elements, boolean isDistinct, Function<T, ?> first,
                      Function<T, ?> second) {
        this.elements = new ArrayList<>();
        for (T element : elements) {
            //System.out.println(element.toString());
            this.elements.add(element);
        }
        this.resultClass = (Class<R>) elements.get(0).getClass();
        this.isDistinct = isDistinct;
        this.functions = new Function[]{first, second};
        this.isUnion= true;
        this.isJoin = true;
        this.pastElements = pastElements;
    }
    public SelectSmth<T, R> where (Predicate<T> predicate) {
        werePredicate = predicate;
        return this;
    }

    @SafeVarargs
    public final SelectSmth<T, R> groupBy(Function<T, ?>... expressions) {
        groupByFunc = expressions;
        if (expressions.length == 0) {
            throw new IllegalStateException("Group only by non-zero params");
        }
        this.isDistinct = true;
        return this;
    }

    @SafeVarargs
    public final SelectSmth<T, R> orderBy(Comparator<R>... compar) {
        comparators = new Compar<R>(comparators);
        return this;
    }

    public SelectSmth<T, R> limit(int lim) {
        this.limitRange = lim;
        return this;
    }

    public SelectSmth<T, R> having(Predicate<R> predicate) {
        havingPredicate = predicate;
        return this;
    }


    public Iterable<R> execute () throws UnsupportedOperationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<R> resultList = new ArrayList<>();
        Class[] returnClasses = new Class[functions.length];
        Object[] arguments = new Object[functions.length];
        Map<Object, ArrayList<Object>> groupingMap = new HashMap<> ();
        Function<T, ?> groupByFunction;
        List<T> elem = new ArrayList<> ();
        for (T element: elements){
            elem.add (element);
        }
        if (groupByFunc == null) {
            if (hasAggregators) {
                groupByFunction = (x -> true);
            } else {
                groupByFunction = Function.identity();
            }
        } else {
            groupByFunction = ((T x) -> {
                Object[] values = new Object[groupByFunc.length];
                for (int i = 0; i < groupByFunc.length; ++i) {
                    values[i] = groupByFunc[i].apply(x);
                }
                return Objects.hash(values);
            });
        }
        if (werePredicate != null) {
            List<T> filtered = new ArrayList<>();
            elem.stream().filter(werePredicate::test).forEach(filtered::add);
            elements = filtered;
        }
        if (groupByFunc != null) {
            Map<Integer, Integer> mapped = new HashMap<>();
            String[] results = new String[groupByFunc.length];
            List<Pair<T, Integer>> grouped = new ArrayList<>();
            elem.stream().forEach(
                    element -> {
                        for (int i = 0; i < groupByFunc.length; i++) {
                            results[i] = (String) groupByFunc[i].apply(element);
                        }
                        if (!mapped.containsKey(Objects.hash(results))) {
                            mapped.put(Objects.hash(results), mapped.size());
                        }
                        grouped.add(new Pair(element, mapped.get(Objects.hash(results))));
                    }
            );
            List<List<T>> groupedElements = new ArrayList<>();
            for (int i = 0; i < mapped.size(); i++) {
                groupedElements.add(new ArrayList<T>());
            }

            for (Pair<T, Integer> element : grouped) {
                groupedElements.get(element.getValue()).add(element.getKey());
            }
            for (List<T> group : groupedElements) {
                for (int i = 0; i < functions.length; i++) {
                    if (functions[i] instanceof  Agregator) {
                        arguments[i] = ((Agregator) functions[i]).apply(group);
                    } else {
                        arguments[i] = functions[i].apply(group.get(0));
                    }
                    returnClasses[i] = arguments[i].getClass();
                }
                if (isJoin) {
                    Tuple newElement = new Tuple(arguments[0], arguments[1]);
                    resultList.add((R) newElement);
                } else {
                    R newElement = (R)resultClass.getConstructor(returnClasses).newInstance(arguments);
                    resultList.add(newElement);
                }
            }
        } else {
            for (T element : this.elements) {
                for (int i = 0; i < functions.length; i++) {
                    arguments[i] = functions[i].apply(element);
                    if (functions[i] instanceof  Agregator) {
                        List<T> currArg = new ArrayList<>();
                        currArg.add(element);
                        arguments[i] = ((Agregator) functions[i]).apply(currArg);
                    } else {
                        arguments[i] = functions[i].apply(element);
                    }
                    returnClasses[i] = arguments[i].getClass();
                }
                if (isJoin) {
                    Tuple newElement = new Tuple(arguments[0], arguments[1]);
                    resultList.add((R) newElement);
                } else {
                    R newElement = (R) resultClass.getConstructor(returnClasses).newInstance(arguments);
                    resultList.add(newElement);
                }
            }
        }
        if (havingPredicate != null) {
            List<R> filtered = new ArrayList<>();
            resultList.stream().filter(havingPredicate::test).forEach(filtered::add);
            resultList = filtered;
        }
        if (isDistinct) {
            Set<Integer> hashes = new HashSet<>();
            List<Integer> flags = new ArrayList<>();
            for (R element : resultList) {
                if (!hashes.contains(element.toString().hashCode())) {
                    flags.add(1);
                    hashes.add(element.toString().hashCode());
                } else {
                    flags.add(0);
                }
            }
            List<R> distincted = new ArrayList<>();
            for (int i = 0; i < resultList.size(); i++) {
                if (flags.get(i) == 1) {
                    distincted.add(resultList.get(i));
                }
            }
            resultList = distincted;
        }
        if (comparators != null) {
            resultList.sort(comparators);
        }
        while (resultList.size() < limitRange) {
            resultList.remove(resultList.size() - 1);
        }

        if (isJoin) {
            pastElements.addAll(resultList);
            resultList = pastElements;
        }
        //System.out.println("Hello!");
        return resultList;

    }

    public UnionSmth<T, R> union() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        List<R> result = (List<R>) this.execute();
        if (isJoin) {
            return new UnionSmth<>(result, true);
        } else {
            return new UnionSmth<>(result);
        }
    }


}
