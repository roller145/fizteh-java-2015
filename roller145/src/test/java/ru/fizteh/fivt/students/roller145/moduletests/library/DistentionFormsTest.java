package ru.fizteh.fivt.students.roller145.moduletests;

import junit.framework.TestCase;
import org.junit.Test;
import ru.fizteh.fivt.students.roller145.TwitterStream.DistentionForms;

/**
 * Created by riv on 12.10.15.
 */
public class DistentionFormsTest extends TestCase {

    @Test
    public void testGetCorrectForm() throws Exception {
        DistentionForms testing = new DistentionForms();
        assertEquals(testing.DisForm(1, DistentionForms.ETime.DAY), " день");
        assertEquals(testing.DisForm(2, DistentionForms.ETime.DAY), " дня");
        assertEquals(testing.DisForm(3, DistentionForms.ETime.DAY), " дня");
        assertEquals(testing.DisForm(5, DistentionForms.ETime.DAY), " дней");
        assertEquals(testing.DisForm(10, DistentionForms.ETime.DAY), " дней");
        assertEquals(testing.DisForm(15, DistentionForms.ETime.DAY), " дней");
        assertEquals(testing.DisForm(21, DistentionForms.ETime.DAY), " день");
        assertEquals(testing.DisForm(37, DistentionForms.ETime.DAY), " дней");
        assertEquals(testing.DisForm(105, DistentionForms.ETime.DAY), " дней");
        assertEquals(testing.DisForm(601, DistentionForms.ETime.DAY), " день");
        assertEquals(testing.DisForm(348, DistentionForms.ETime.DAY), " дней");
        assertEquals(testing.DisForm(8088, DistentionForms.ETime.DAY), " дней");
    }

}