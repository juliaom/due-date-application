package com.comcave.due.model;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Data class that represents event to be triggered when the appropriate time comes.
 */
public class DueDateEvent {

    private final String title;

    private final String time;

    private final boolean toBeTriggered;

    private final String task;

    private boolean triggered;

    /**
     * Event constructor.
     *
     * @param title         event title
     * @param time          time, at which or after which the event is to be triggered
     * @param toBeTriggered if true, a model dialog should be shown when the triggering time comes
     * @param task          command line task to be executed when the triggered time comes
     */
    public DueDateEvent(String title, String time, boolean toBeTriggered, String task) {
        this.title = Objects.requireNonNull(title);
        this.time = Objects.requireNonNull(time);
        this.toBeTriggered = toBeTriggered;
        this.task = task;
    }

    /**
     * Gets event title.
     *
     * @return event title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets event triggering time in string representation.
     *
     * @return event triggering time
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets flag that signals about showing the model dialog in case the event is triggered.
     *
     * @return flag that signals about showing the model dialog in case the event is triggered
     */
    public boolean isToBeTriggered() {
        return toBeTriggered;
    }

    /**
     * Gets command line task.
     *
     * @return command line task
     */
    public String getTask() {
        return task;
    }

    /**
     * Gets flag that signals, if the event was already triggered.
     *
     * @return flag that signals, if the event was already triggered
     */
    public boolean isTriggered() {
        return triggered;
    }

    /**
     * Sets flag that signals, if the event was already triggered.
     *
     * @param triggered flag that signals, if the event was already triggered
     */
    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    /**
     * Gets event triggering time in OffsetDateTime representation.
     *
     * @return event triggering time
     */
    public OffsetDateTime toTime() {
        return DueDateFormatter.toTime(time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DueDateEvent that = (DueDateEvent) o;
        return title.equals(that.title) && time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time);
    }

    @Override
    public String toString() {
        return "DueDateEvent{" +
                "title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", toBeTriggered=" + toBeTriggered +
                ", task='" + task + '\'' +
                ", triggered=" + triggered +
                '}';
    }
}
