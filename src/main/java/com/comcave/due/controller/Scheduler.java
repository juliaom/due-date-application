package com.comcave.due.controller;

import com.comcave.due.model.DueDateEvent;
import com.comcave.due.model.DueDateEventStorage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Scheduler that checks events each 5 seconds and triggers them in case the conditions are met.
 */
public class Scheduler implements Runnable {

    private static final DueDateEventStorage STORAGE = DueDateEventStorage.getInstance();

    private final JFrame parent;

    /**
     * Scheduler constructor.
     *
     * @param parent GUI parent
     */
    public Scheduler(JFrame parent) {
        this.parent = Objects.requireNonNull(parent);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // Ignore this.
            }
            checkEvents();
        }
    }

    private void checkEvents() {
        boolean changed = false;
        try {
            OffsetDateTime now = OffsetDateTime.now();
            for (DueDateEvent event : STORAGE) {
                if (!event.isTriggered()) {
                    try {
                        changed = true;
                        OffsetDateTime time = event.toTime();
                        if (!now.isBefore(time)) {
                            triggerEvent(event);
                        }
                    } finally {
                        event.setTriggered(true);
                    }
                }
            }
        } finally {
            if (changed) {
                STORAGE.rewriteEvents();
            }
        }
    }

    private void triggerEvent(DueDateEvent event) {
        String task = event.getTask();
        if (task != null && !"".equals(task.trim())) {
            new Thread(() -> {
                try {
                    Runtime.getRuntime().exec(event.getTask());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        if (event.isToBeTriggered()) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(parent, event.getTitle()));
        }
    }
}
