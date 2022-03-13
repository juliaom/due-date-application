package com.comcave.due.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.OffsetDateTime;

import static com.comcave.due.TestConstants.TEST_EVENT_TASK;
import static com.comcave.due.TestConstants.TEST_EVENT_TASK_2;
import static com.comcave.due.TestConstants.TEST_EVENT_TIME;
import static com.comcave.due.TestConstants.TEST_EVENT_TIME_STR;
import static com.comcave.due.TestConstants.TEST_EVENT_TIME_STR_2;
import static com.comcave.due.TestConstants.TEST_EVENT_TITLE;
import static com.comcave.due.TestConstants.TEST_EVENT_TITLE_2;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Class that contains tests for the {@link com.comcave.due.model.DueDateEvent} class.
 */
public class DueDateEventTest {

    private DueDateEvent event;

    @Before
    public void setUp() {
        event = new DueDateEvent(TEST_EVENT_TITLE, TEST_EVENT_TIME_STR, true, TEST_EVENT_TASK);
    }

    @After
    public void tearDown() {
        event = null;
    }

    /**
     * Tests the created due-date event.
     */
    @Test
    public void testCreatedEvent() {
        assertThat(event, notNullValue());
        assertThat(event.getTitle(), equalTo(TEST_EVENT_TITLE));
        assertThat(event.getTime(), equalTo(TEST_EVENT_TIME_STR));
        assertThat(event.isToBeTriggered(), equalTo(true));
        assertThat(event.getTask(), equalTo(TEST_EVENT_TASK));
        assertThat(event.isTriggered(), equalTo(false));
    }

    /**
     * Tests the {@link DueDateEvent#equals(Object)} and the {@link DueDateEvent#hashCode()} methods.
     */
    @Test
    public void testEqualsHashCode() {
        DueDateEvent first = new DueDateEvent(TEST_EVENT_TITLE, TEST_EVENT_TIME_STR, false,
                TEST_EVENT_TASK_2);
        DueDateEvent second = new DueDateEvent(TEST_EVENT_TITLE_2, TEST_EVENT_TIME_STR, true,
                TEST_EVENT_TASK);
        DueDateEvent third = new DueDateEvent(TEST_EVENT_TITLE, TEST_EVENT_TIME_STR_2, true,
                TEST_EVENT_TASK);
        DueDateEvent fourth = new DueDateEvent(TEST_EVENT_TITLE_2, TEST_EVENT_TIME_STR_2, false,
                TEST_EVENT_TASK_2);
        testEqualsHashCode(event, first, true);
        testEqualsHashCode(event, second, false);
        testEqualsHashCode(event, third, false);
        testEqualsHashCode(event, fourth, false);
    }

    /**
     * Tests the {@link DueDateEvent#toString()} method.
     */
    @Test
    public void testToString() {
        String eventStr = event.toString();
        assertThat(eventStr, containsString(TEST_EVENT_TITLE));
        assertThat(eventStr, containsString(TEST_EVENT_TIME_STR));
        assertThat(eventStr, containsString(TEST_EVENT_TASK));
    }

    /**
     * Tests the {@link DueDateEvent#toTime()} method.
     */
    @Test
    public void testToTime() {
        OffsetDateTime time = event.toTime();
        assertThat(time, equalTo(TEST_EVENT_TIME));
    }

    private static void testEqualsHashCode(DueDateEvent first, DueDateEvent second, boolean equals) {
        assertThat(first.equals(second), equalTo(equals));
        if (equals) {
            assertThat(first.hashCode(), equalTo(second.hashCode()));
        }
    }
}
