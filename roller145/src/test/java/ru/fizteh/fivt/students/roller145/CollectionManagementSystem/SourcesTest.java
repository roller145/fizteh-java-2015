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
public class SourcesTest extends TestCase {
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
    public void testList() throws Exception {
        List<CollectionManagementSystem.Student> resultList = Sources.list(
                CollectionManagementSystem.Student.student("ivanov", LocalDate.parse("1986-08-06"), "494"),
                CollectionManagementSystem.Student.student("sidorov", LocalDate.parse("1986-08-06"), "495"),
                CollectionManagementSystem.Student.student("rudenko", LocalDate.parse("2006-08-06"), "494"));
        assertEquals(testList.size(), resultList.size());
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(testList.get(i).toString(), resultList.get(i).toString());
        }
    }
}