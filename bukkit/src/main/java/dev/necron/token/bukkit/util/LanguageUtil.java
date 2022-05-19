package dev.necron.token.bukkit.util;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class LanguageUtil {

    public static String replace(String message) {
        return ColorSerializer.serialize(message);
    }

    public static String replace(String message, String[] targets, String[] replacements) {
        return ColorSerializer.serialize(StringUtils.replaceEach(message, targets, replacements));
    }

    public static List<String> replaceList(List<String> messages) {
        messages.replaceAll(LanguageUtil::replace);
        return messages;
    }

    public static List<String> replaceList(List<String> messages, String[] targets, String[] replacements) {
        messages.replaceAll(message -> replace(message, targets, replacements));
        return messages;
    }

}
