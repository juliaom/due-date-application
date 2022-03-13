package com.comcave.due.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.util.Objects;

/**
 * Menu bar for the application GUI frame.
 */
public class DueDateMenuBar extends JMenuBar {

    private final DueDateFrame parent;

    /**
     * Menu bar's constructor.
     *
     * @param parent GUI frame
     */
    public DueDateMenuBar(DueDateFrame parent) {
        this.parent = Objects.requireNonNull(parent);
        JMenu fileMenu = new JMenu("File");
        createNewItem(fileMenu);
        createExitItem(fileMenu);
        add(fileMenu);
    }

    private void createNewItem(JMenu menu) {
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(e -> {
            NewEventDialog dialog = new NewEventDialog(parent);
            dialog.display();
        });
        menu.add(newItem);
    }

    private void createExitItem(JMenu menu) {
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            parent.closeWindow();
            System.exit(0);
        });
        menu.add(exitItem);
    }
}
