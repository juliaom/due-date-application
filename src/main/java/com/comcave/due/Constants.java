package com.comcave.due;

import java.io.File;

/**
 * Utility class that contains constants.
 */
public final class Constants {

    public static final String DATA_FILE_NAME = "due_date.csv";

    public static final File DATA_FILE = new File(DATA_FILE_NAME);

    public static final String[] HEADERS = {"title", "time", "toBeTriggered", "task", "triggered"};

    public static final String[] TITLES = {"Title", "Time", "To Be Triggered", "Task"};

    private Constants() {
    }
}
