package com.comcave.due.model;

import org.junit.Test;

import java.time.OffsetDateTime;

import static com.comcave.due.TestConstants.TEST_EVENT_TIME;
import static com.comcave.due.TestConstants.TEST_EVENT_TIME_STR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Class that contains tests for the {@link com.comcave.due.model.DueDateFormatter} class.
 */
public class DueDateFormatterTest {

    /**
     * Tests the {@link DueDateFormatter#fromTime(OffsetDateTime)} method.
     */
    @Test
    public void testFromTime() {
        String time = DueDateFormatter.fromTime(TEST_EVENT_TIME);
        assertThat(time, equalTo(TEST_EVENT_TIME_STR));
    }

    /**
     * Tests the {@link DueDateFormatter#toTime(String)} method.
     */
    @Test
    public void testToTime() {
        OffsetDateTime time = DueDateFormatter.toTime(TEST_EVENT_TIME_STR);
        assertThat(time, equalTo(TEST_EVENT_TIME));
    }
}
