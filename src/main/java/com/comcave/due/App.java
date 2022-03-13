package com.comcave.due;

import com.comcave.due.controller.Scheduler;
import com.comcave.due.view.DueDateFrame;

import javax.swing.SwingUtilities;

/**
 * Class that contains main entry point of the application.
 */
public class App {
    /**
     * Main entry point of the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DueDateFrame frame = new DueDateFrame();
            frame.setUp();
            frame.createTable();
            frame.pack();
            frame.setVisible(true);
            new Thread(new Scheduler(frame)).start();
        });
    }
}
