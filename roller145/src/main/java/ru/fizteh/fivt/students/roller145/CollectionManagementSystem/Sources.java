package ru.fizteh.fivt.students.roller145.CollectionManagementSystem;

/**
 * Created by RIV on 17.11.2015.
 */

import java.util.Arrays;
import java.util.List;


public class Sources {
    @SafeVarargs
    public static <T> List<T> list(T... items) {
        return Arrays.asList(items);
    }

}
