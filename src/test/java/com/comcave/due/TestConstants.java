package com.comcave.due;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Utility class that is used to store constants for tests.
 */
public final class TestConstants {

    public static final String TEST_EVENT_TASK = "dir";

    public static final String TEST_EVENT_TASK_2 = "ls";

    public static final OffsetDateTime TEST_EVENT_TIME = OffsetDateTime.of(2022, 3, 13,
            17, 49, 5, 353554100, ZoneOffset.ofHours(1));

    public static final String TEST_EVENT_TIME_STR = "2022-03-13T17:49:05.3535541+01:00";

    public static final String TEST_EVENT_TIME_STR_2 = "2022-03-14T17:49:05.3535541+01:00";

    public static final String TEST_EVENT_TITLE = "Blablabla";

    public static final String TEST_EVENT_TITLE_2 = "Blablabla2";

    private TestConstants() {
    }
}
