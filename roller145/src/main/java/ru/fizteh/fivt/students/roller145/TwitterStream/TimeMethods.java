package ru.fizteh.fivt.students.roller145.TwitterStream;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ru.fizteh.fivt.students.roller145.TwitterStream.DistentionForms.DisForm;

/**
 * This class changes time between creating time
 * and current in beautiful string for printing
 * Created by riv on 27.09.15.
 */
public class TimeMethods {

    public static final int MILISEC_IN_SEC = 1000;

    public String getTimeStr(LocalDateTime when) {
        String result = "";
        LocalDateTime currentTime = LocalDateTime.now();
        long minute = ChronoUnit.MINUTES.between(when, currentTime);
        long hour = ChronoUnit.HOURS.between(when, currentTime);
        long day = ChronoUnit.DAYS.between(when, currentTime);

            if (minute < 2) {
                result+="Только что ";
            }
            else if (hour < 1) {
                result+= minute+ DisForm(minute, DistentionForms.ETime.MINUTE) +" назад ";
            }
            else if (day < 1) {
                result+=(hour  + DisForm(hour, DistentionForms.ETime.HOUR)+ " назад ");
            }
            else {
                if (day < 2) {
                    result+=("Вчера ");
                }
                else {
                    result+=(day  + DisForm(day, DistentionForms.ETime.DAY)+ " назад ");
                }
            }
        return result;
    }

}
