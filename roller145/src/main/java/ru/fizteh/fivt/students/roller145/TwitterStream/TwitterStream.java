package ru.fizteh.fivt.students.roller145.TwitterStream;

import com.beust.jcommander.JCommander;

public class TwitterStream {

    public static void main(String[] args) throws Exception {

        TwitterStreamParser twParse = new TwitterStreamParser();
        JCommander command = new JCommander(twParse, args);
        if (twParse.isHelpOn()) {
            twParse.printHelp(command);
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

