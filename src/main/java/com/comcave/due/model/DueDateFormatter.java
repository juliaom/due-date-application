package com.comcave.due.model;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Date-time formatter for the due date events.
 */
public final class DueDateFormatter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Converts the date-time to the OffsetDateTime format.
     *
     * @param time date-time in the string representation
     * @return date-time to the OffsetDateTime format
     */
    public static String fromTime(OffsetDateTime time) {
        return Objects.requireNonNull(time).format(DATE_TIME_FORMATTER);
    }

    /**
     * Converts the date-time to the string format.
     *
     * @param time date-time in the OffsetDateTime representation
     * @return date-time to the string format
     */
    public static OffsetDateTime toTime(String time) {
        return OffsetDateTime.parse(Objects.requireNonNull(time), DATE_TIME_FORMATTER);
    }

    private DueDateFormatter() {
    }
}
