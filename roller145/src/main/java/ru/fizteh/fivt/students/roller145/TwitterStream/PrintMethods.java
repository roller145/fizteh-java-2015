package ru.fizteh.fivt.students.roller145.TwitterStream;

import twitter4j.Status;

import java.io.PrintStream;
import java.io.PrintWriter;

import java.time.ZoneId;

import static ru.fizteh.fivt.students.roller145.TwitterStream.DistentionForms.RETWEET_FORMS;
import static ru.fizteh.fivt.students.roller145.TwitterStream.DistentionForms.getCorrectForm;

/**
 * This is a class, witch works like PrintWriter, but for tweets
 * Created by riv on 11.10.15.
 */
public class PrintMethods extends PrintWriter {

    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_RESET = "\u001B[0m";

    private final Status tweet;

    public PrintMethods(PrintStream out, Status tw) {
        super(out,true);
        this.tweet = tw;
    }

    public void printTweet() {
        printTime();
        printName();
        if (tweet.isRetweet()) {
            print(" ретвитнул ");
            String[] splited = tweet.getText().split(" ");
            print(
                    "@"
                            + ANSI_PURPLE
                            + splited[1].split("@|:")[1]
                            + ANSI_RESET
                            + " : ");

            for (int i = 2; i < splited.length; ++i) {
                print(splited[i] + " ");
            }
            println();
        } else {
            print(tweet.getText() + " (" + tweet.getRetweetCount() + " " +
                    RETWEET_FORMS[getCorrectForm(tweet.getRetweetCount()).getType()] + ")");
            println();
        }
    }

    private void printName() {
        print(
                ANSI_PURPLE
                        + "@"
                        + tweet.getUser().getScreenName()
                        + ":"
                        + ANSI_RESET);
    }

    private void printTime() {
        print(new TimeMethods()
                .getTimeStr(tweet.getCreatedAt()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()));
    }
}
