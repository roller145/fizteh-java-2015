package ru.fizteh.fivt.students.roller145.TwitterStream;

import com.beust.jcommander.JCommander;

import java.io.PrintWriter;

public class TwitterStream {
    private static final String near = "nearbly";
    public static void main(String[] args) throws Exception {
        String were = new String();
        TwitterStreamParser twParse = new TwitterStreamParser();
        JCommander command = new JCommander(twParse, args);
        if (twParse.isHelpOn()) {
            twParse.printHelp(command);
        }
        if ((were = twParse.getWhere()) == near ){
            if (were.equals(near)) {
                were = new GetGeolocation().getLocation();
            }
            new PrintWriter(System.out).println(were);
        }
        else if (twParse.isStreamOn() && twParse.isLimit()) {
            System.err.println("Конфлит команд ");
        }
        else if (twParse.isStreamOn()) {
            new TwitterModes(System.out,twParse).streamMode();
        }
        else if (twParse.isLimit()){
            new TwitterModes(System.out,twParse).limitedMode();
        }
    }

}

