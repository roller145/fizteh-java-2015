package ru.fizteh.fivt.students.roller145.TwitterStream;

import javafx.util.Pair;
import twitter4j.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static java.lang.Thread.sleep;
import static ru.fizteh.fivt.students.roller145.TwitterStream.TimeMethods.MILISEC_IN_SEC;

/**
 * The class is an implementation of main usage
 * Created by riv on 11.10.15.
 */

public class TwitterModes {

    private static final String UNIT_RADIUS = "km";

    private final TwitterStreamParser twParse;
    private final PrintStream out;

    public TwitterModes(PrintStream out,TwitterStreamParser twParse) {
        this.out = out;
        this.twParse = twParse;
    }


    public void limitedMode() throws IOException, TwitterException {

        boolean filterRetweet = twParse.isFilterRetweet();
        int numberOfTweets = twParse.getNumber();
        String queryWords = twParse.getQueryWords();
        String where = twParse.getWhere();
        boolean isLocation = twParse.isPlace();

        Twitter twitter = new TwitterFactory().getInstance();
        if (where == null && queryWords == null) {
            throw new TwitterException("Пустой запрос");
        }
        boolean isAnyTweets = false;
        try {
            Query query = new Query();
            setQuery(queryWords, where, isLocation, query);
            QueryResult result;
            Integer count = 0;

            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    if ((tweet.isRetweet() && !filterRetweet) || !tweet.isRetweet()) {
                        new PrintMethods(out, tweet).printTweet();
                        isAnyTweets = true;
                        ++count;
                        if (count == numberOfTweets) {
                            break;
                        }
                    }
                }
            } while ((query = result.nextQuery()) != null);
            if (!isAnyTweets) {
                throw new TwitterException("\nЯ не могу найти какие-либо твиты для запроса "
                        + queryWords
                        + " для  "
                        + where
                        + " \n\n"
                        + " Пожалуйста, попробуйте проверить орфографию или использовать более общие слова\n");
            }
        } catch (TwitterException te) {
            throw new TwitterException("Не удалось найти твиты:  " + te.getMessage());
        }
    }

    private void setQuery(String queryWords, String where, boolean isLocation, Query query) throws IOException {
        if (queryWords == null) {
            Pair<GeoLocation, Double> location = new GetGeolocation().getGeolocation(where);
            query.setGeoCode(location.getKey(),
                    location.getValue(),
                    UNIT_RADIUS);
        } else {
            query.setQuery(queryWords);
            if (isLocation) {
                Pair<GeoLocation, Double> location = new GetGeolocation().getGeolocation(where);
                query.setGeoCode(location.getKey(),
                        location.getValue(),
                        UNIT_RADIUS);
            }
        }
    }

    public void streamMode() throws IOException, TwitterException, InterruptedException {
        boolean filterRetweet = twParse.isFilterRetweet();
        String queryWords = twParse.getQueryWords();
        String where = twParse.getWhere();
        boolean isLocation = twParse.isPlace();

        if (where.isEmpty() && queryWords.isEmpty()) {
            throw new TwitterException("Пустой запрос ");
        }
        Twitter twitter = new TwitterFactory().getInstance();

        Query query = new Query();
        setQuery(queryWords, where, isLocation, query);
        QueryResult result;
        boolean isAnyTweets = false;
        do {
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();
            for (Status tweet : tweets) {
                if ((tweet.isRetweet() && !filterRetweet) || !tweet.isRetweet()) {
                    PrintMethods printMethods = new PrintMethods(out, tweet);
                    printMethods.printTweet();

                    sleep(MILISEC_IN_SEC);
                    isAnyTweets = true;
                }
            }
        } while ((query = result.nextQuery()) != null);

        if (!isAnyTweets) {
            throw new TwitterException("\nЯ не могу найти какие-либо твиты для запроса "
                    + queryWords
                    + " для  "
                    + where
                    + " \n\n"
                    + " Пожалуйста, попробуйте проверить орфографию или использовать более общие слова\n");
        }
    }
}
