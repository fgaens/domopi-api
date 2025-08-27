package be.codesolutions.domopi.util;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public enum Log {

    TRACE(Color.PURPLE, System.out),
    DEBUG(Color.BLUE, System.out),
    INFO(Color.GREEN, System.out),
    WARN(Color.YELLOW, System.out),
    ERROR(Color.LIGHT_RED, System.err);

    enum Color {
        PURPLE("\033[0;35m"),
        BLUE("\033[0;34m"),
        GREEN("\033[0;32m"),
        YELLOW("\033[0;33m"),
        LIGHT_RED("\033[1;31m");

        final String code;

        Color(String code) {
            this.code = code;
        }
    }

    private final String colorFormat;
    private final PrintStream out;
    private static final String RESET = "\u001B[0m";

    private static boolean includeTimestamp = true;
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Log(Color color, PrintStream out) {
        this.colorFormat = color.code + "%s" + RESET;
        this.out = out;
    }

    private String format(String message, Object... args) {
        if (args.length == 0) {
            return message;
        }

        // Replace {} placeholders with %s for String.formatted()
        String formatted = message.replace("{}", "%s");
        return String.format(formatted, args);
    }

    private void log(String message, Object... args) {
        String formattedMessage = format(message, args);

        if (includeTimestamp) {
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
            formattedMessage = timestamp + " " + formattedMessage;
        }

        String coloredMessage = String.format(colorFormat, formattedMessage);
        out.println(coloredMessage);
    }

    public static void trace(String message, Object... args) {
        TRACE.log(message, args);
    }

    public static void debug(String message, Object... args) {
        DEBUG.log(message, args);
    }

    public static void info(String message, Object... args) {
        INFO.log(message, args);
    }

    public static void warn(String message, Object... args) {
        WARN.log(message, args);
    }

    public static void error(String message, Object... args) {
        ERROR.log(message, args);
    }

    public static void error(String message, Exception e) {
        ERROR.log(message + ": " + e.getMessage());
        e.printStackTrace(System.err);
    }

    public static void setTimestampEnabled(boolean enabled) {
        includeTimestamp = enabled;
    }
}