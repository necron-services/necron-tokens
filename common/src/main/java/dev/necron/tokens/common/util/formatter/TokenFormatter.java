package dev.necron.tokens.common.util.formatter;

import java.text.NumberFormat;
import java.util.Locale;

public class TokenFormatter {

    public static String format(long tokens) {
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        if (tokens < 1000.0D) return format.format(tokens);
        else if (tokens < 1000000.0D) return format.format(tokens / 1000.0D) + "k";
        else if (tokens < 1.0E9D)return format.format(tokens / 1000000.0D) + "M";
        else if (tokens < 1.0E12D)return format.format(tokens / 1.0E9D) + "B";
        else if (tokens < 1.0E15D)return format.format(tokens / 1.0E12D) + "T";
        else return tokens < 1.0E18D ? format.format(tokens / 1.0E15D) + "Q" : String.valueOf(tokens);
    }

}
