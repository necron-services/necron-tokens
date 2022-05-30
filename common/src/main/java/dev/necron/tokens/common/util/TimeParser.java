package dev.necron.tokens.common.util;

public class TimeParser {

    public static String parse(long time) {
        int days = (int) (time / 86400);
        int hours = (int) (time / 3600 % 24);
        int minutes = (int) (time / 60 % 60);
        int seconds = (int) (time % 60);
        return (days > 0 ? days + "d " : "") + (hours > 0 ? hours + "h " : "") + (minutes > 0 ? minutes + "m " : "") + seconds + "s";
    }

}
