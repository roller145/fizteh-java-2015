package ru.fizteh.fivt.students.roller145.CollectionManagementSystem;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static ru.fizteh.fivt.students.roller145.CollectionManagementSystem.Aggregates.max;
import static ru.fizteh.fivt.students.roller145.CollectionManagementSystem.Aggregates.min;
import static ru.fizteh.fivt.students.roller145.CollectionManagementSystem.Conditions.rlike;

/**
 * Created by riv on 17.12.15.
 */
public class ConditionsTest extends TestCase {
    Function<CollectionManagementSystem.Student, String> function = CollectionManagementSystem.Student::getName;
    List<CollectionManagementSystem.Student> testList;



    @Before
    public void setUp() throws Exception {
        testList = new ArrayList<>();
        testList.add(new CollectionManagementSystem.Student("ivanov", LocalDate.parse("1986-08-06"), "494"));
        testList.add(new CollectionManagementSystem.Student("sidorov", LocalDate.parse("1986-08-06"), "495"));
        testList.add(new CollectionManagementSystem.Student("rudenko", LocalDate.parse("2006-08-06"), "494"));
    }

    @Test
    public void testRlike() throws Exception {

        assertEquals(rlike(function, ".*ov").test(testList.get(0)), true);
        assertEquals(rlike(function, ".*ov").test(testList.get(1)), true);
        assertEquals(rlike(function, ".*ov").test(testList.get(2)), false);
    }

    /*@Test(expected = UnsupportedOperationException.class)
    public void testLike() throws Exception {
        Conditions.like(function, "aaa");
    }*/



}