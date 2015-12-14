package ru.fizteh.fivt.students.roller145.moduletests.library;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.fizteh.fivt.students.roller145.TwitterStream.PrintMethods;
import twitter4j.Status;
import java.lang.String;


import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

/**
 * Created by riv on 24.11.2015.
 * it tests setting the tweet to String
 */

@RunWith (PowerMockRunner.class)
@PrepareForTest(Status.class)
public class PrintMethodsTest {
    private Status tweet;
    private static String TWEET ="Только что @Vasya: Hi! (5 ретвитов)";

    @Before
    public void setStatusForTest(){

    }



    @Test
    public void testSetTweet () throws Exception {
        tweet = PowerMockito.mock(Status.class);
        PowerMockito.when(tweet.getUser ().getScreenName ()).thenReturn ("Vasya");
        PowerMockito.when(tweet.isRetweet ()).thenReturn (false);
        PowerMockito.when(tweet.getText ()).thenReturn ("Hi!");
        PowerMockito.when (tweet.getRetweetCount ()).thenReturn (5);
        PowerMockito.when (tweet.getCreatedAt ()
                .toInstant ()
                .atZone (ZoneId.systemDefault ()).toLocalDateTime())
                .thenReturn (LocalDateTime.now());
        assertEquals (new PrintMethods(System.out,tweet).setTweet (), TWEET);
    }
}