package ru.fizteh.fivt.students.roller145.TwitterStream;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Parsing commands using jcomander
 * Created by riv on 25.09.15.
 */
public class TwitterStreamParser {
    @Parameter(names = {"--query", "-q"},
            description = "query or keywords for stream")
    private String queryWords = "";

    @Parameter(names = {"-h","--help"}, description = "вывод справки")
    private boolean isHelp = false;

    @Parameter(names = {"--place", "-p"},
            description = "Искать по заданному региону." +
                    " Если значение равно nearby или параметр отсутствует - искать по ip")
    private  String where = "";

    @Parameter(names = {"--stream", "-s"},
            description = "Равномерный и непрерывный с задержкой в 1 секунду вывод твитов на экран. ")
    private boolean stream = false;

    @Parameter(names = "---hideRetweets",
            description = "Eсли параметр задан, нужно фильтровать ретвиты")
    private boolean isFilter = false;

    @Parameter(names = {"--limit","-l"},
            description = "выводить только заданное количество твитов")
    private int number = -1;

    public boolean isHelpOn(){
        return isHelp;
    }

    public int getNumber() {
        return number;
    }

    public boolean isStreamOn(){
        return stream;
    }

    public boolean isLimit() {
        return (number != -1);
    }

    public String getQueryWords() {
        return queryWords;
    }

    public boolean isFilterRetweet() {
        return isFilter;
    }

    public boolean isPlace() {
        return !(where.isEmpty());
    }

    public String getWhere() {
        return where;
    }
    public void printHelp(JCommander command) {
        command.usage();
    }
}
