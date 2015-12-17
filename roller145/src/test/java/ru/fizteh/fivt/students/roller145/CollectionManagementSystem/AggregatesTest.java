package ru.fizteh.fivt.students.roller145.CollectionManagementSystem;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static ru.fizteh.fivt.students.roller145.CollectionManagementSystem.Aggregates.*;

/**
 * Created by riv on 17.12.15.
 */
public class AggregatesTest extends TestCase {
    List<CollectionManagementSystem.Student> testList;
    Function<CollectionManagementSystem.Student, ?> minf = min(CollectionManagementSystem.Student::age), maxf = max(CollectionManagementSystem.Student::age);



    @Before
    public void setUp() throws Exception {
        testList = new ArrayList<>();
        testList.add(new CollectionManagementSystem.Student("ivanov", LocalDate.parse("1986-08-06"), "494"));
        testList.add(new CollectionManagementSystem.Student("sidorov", LocalDate.parse("1986-08-06"), "495"));
        testList.add(new CollectionManagementSystem.Student("petrov", LocalDate.parse("2006-08-06"), "494"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAll() {
        assertTrue(minf instanceof Agregator);
        assertEquals(((Agregator<CollectionManagementSystem.Student, ?>) minf).apply(testList), 9L);
        assertTrue(maxf instanceof Agregator);
        assertEquals(((Agregator<CollectionManagementSystem.Student, ?>) maxf).apply(testList), 29L);
        assertEquals(((Agregator) Aggregates.max(CollectionManagementSystem.Student::getGroup)).apply(testList), "495");
        assertEquals(((Agregator) Aggregates.max(CollectionManagementSystem.Student::getName)).apply(testList), "sidorov");
        assertEquals(((Agregator) Aggregates.count(CollectionManagementSystem.Student::age)).apply(testList), 2);
    }

}