package ru.fizteh.fivt.students.roller145.moduletests.library;

import junit.framework.TestCase;
import org.junit.Test;
import ru.fizteh.fivt.students.roller145.TwitterStream.TimeMethods;

import java.time.LocalDateTime;

/**
 * Created by riv on 25.10.15.
 */
public class TimeMethodsTest extends TestCase {
    @Test
    public void testGetTimeStr() throws Exception {
        TimeMethods testing = new TimeMethods();
        LocalDateTime date = LocalDateTime.now();
        assertEquals(testing.getTimeStr(date), "Только что ");
        LocalDateTime date2 = date.minusMinutes(2);
        assertEquals(testing.getTimeStr(date2), "2 минуты назад ");
        LocalDateTime date3 = date.minusHours(2);
        assertEquals(testing.getTimeStr(date3),"2 часа назад ");
        LocalDateTime date4 = date.minusDays(2);
        assertEquals(testing.getTimeStr(date4),"2 дня назад ");
        LocalDateTime date5 = date.minusDays(1);
        assertEquals(testing.getTimeStr(date5),"Вчера ");
    }
}