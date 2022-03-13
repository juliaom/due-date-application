package com.comcave.due.controller;

import java.util.Date;

/**
 * Interface of listener of change due date-related changes.
 */
@FunctionalInterface
public interface DueDateListener {

    /**
     * Method that is called on change of the date.
     *
     * @param oldDate old date
     * @param newDate new date
     */
    void onDateChanged(Date oldDate, Date newDate);
}
