package me.aco.marketplace.util;

import java.time.format.DateTimeFormatter;

public final class DateFormats {

    private DateFormats() {}

    // I need this format because of angular
    public static final String ISO_INSTANT_NO_MILLIS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final DateTimeFormatter ISO_INSTANT_NO_MILLIS =
            DateTimeFormatter.ofPattern(ISO_INSTANT_NO_MILLIS_PATTERN);
}
