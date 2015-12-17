package ru.fizteh.fivt.students.roller145.CollectionManagementSystem;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by riv on 17.12.15.
 */
public class OrderByConditionTest extends TestCase {
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
    public void testAll() throws Exception {
        assertTrue(OrderByCondition.asc(function).compare(testList.get(0), testList.get(2)) < 0);
        assertTrue(OrderByCondition.asc(function).compare(testList.get(1), testList.get(0)) > 0);
        assertTrue(OrderByCondition.asc(function).compare(testList.get(0), testList.get(0)) == 0);
        assertTrue(OrderByCondition.desc(function).compare(testList.get(0), testList.get(2)) > 0);
        assertTrue(OrderByCondition.desc(function).compare(testList.get(1), testList.get(0)) < 0);
        assertTrue(OrderByCondition.desc(function).compare(testList.get(0), testList.get(0)) == 0);
    }
}