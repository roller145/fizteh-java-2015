package ru.fizteh.fivt.students.roller145.CollectionManagementSystem.impl;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.fizteh.fivt.students.roller145.CollectionManagementSystem.CollectionManagementSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Integer.MAX_VALUE;
import static ru.fizteh.fivt.students.roller145.CollectionManagementSystem.CollectionManagementSystem.*;

/**
 * Created by riv on 17.12.15.
 */
public class SelectSmthTest extends TestCase {
    Function<CollectionManagementSystem.Student, String> fName = Student::getName;
    Function<CollectionManagementSystem.Student, Long> fAge = Student::age;
    Function<CollectionManagementSystem.Student, String> fGroup = Student::getGroup;

    List<CollectionManagementSystem.Student> testList;
    List<Student> emptyList;

    @Before
    public void setUp() throws Exception {
        testList = new ArrayList<>();
        testList.add(new Student("ivanov", LocalDate.parse("1986-08-06"), "494"));
        testList.add(new Student("sidorov", LocalDate.parse("1986-08-06"), "495"));
        testList.add(new Student("rudenko", LocalDate.parse("1996-08-06"), "494"));
        emptyList = new ArrayList<>();
    }

    @Test
    public void testAll(){
        FromSmth<Student> fromSmth = FromSmth.from(testList);
        assertEquals(fromSmth.getElements().size(), testList.size());
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(fromSmth.getElements().get(i), testList.get(i));
        }
        SelectSmth<CollectionManagementSystem.Student, CollectionManagementSystem.Student> select = FromSmth.from(testList)
                .select(CollectionManagementSystem.Student.class, CollectionManagementSystem.Student::getName,
                        CollectionManagementSystem.Student::getGroup);
        assertEquals(select.getNumberOfObjects(), MAX_VALUE);
        assertEquals(select.getResultClass(), CollectionManagementSystem.Student.class);
        assertEquals(select.isDistinct(), false);
        assertEquals(select.isUnion(), false);
        Function<CollectionManagementSystem.Student, String>[] functions = new Function[2];
        functions[0] = CollectionManagementSystem.Student::getName;
        functions[1] = CollectionManagementSystem.Student::getGroup;
        assertEquals(select.getFunctions().length, functions.length);
        for (int i = 0; i < functions.length; i++) {
            for (CollectionManagementSystem.Student element : testList) {
                assertEquals(functions[i].apply(element),
                        select.getFunctions()[i].apply(element));
            }
        }
        assertEquals(testList.size(), select.getElements().size());
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(testList.get(i), select.getElements().get(i));
        }



        SelectSmth<CollectionManagementSystem.Student, CollectionManagementSystem.Student> selectDistinct = FromSmth.from(testList)
                .selectDistinct(CollectionManagementSystem.Student.class, CollectionManagementSystem.Student::getName,
                        CollectionManagementSystem.Student::getGroup);
        assertEquals(selectDistinct.getResultClass(), CollectionManagementSystem.Student.class);
        assertEquals(selectDistinct.isDistinct(), true);
        assertEquals(selectDistinct.isUnion(), false);
        assertEquals(select.getFunctions().length, functions.length);
        for (int i = 0; i < functions.length; i++) {
            for (CollectionManagementSystem.Student element : testList) {
                assertEquals(functions[i].apply(element),
                        select.getFunctions()[i].apply(element));
            }
        }
        assertEquals(testList.size(), select.getElements().size());
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(testList.get(i), select.getElements().get(i));
        }
    }
}