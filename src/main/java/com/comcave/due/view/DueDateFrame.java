package com.comcave.due.view;

import com.comcave.due.model.DueDateEvent;
import com.comcave.due.model.DueDateEventStorage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import static com.comcave.due.Constants.TITLES;

/**
 * GUI Frame.
 */
public class DueDateFrame extends JFrame {

    private static final DueDateEventStorage STORAGE = DueDateEventStorage.getInstance();

    private JTable table;

    /**
     * Default constructor.
     */
    public DueDateFrame() {
        super("Due Date Application");
    }

    /**
     * Closes frame's window.
     */
    public void closeWindow() {
        processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Creates table that lists all the available due date events.
     */
    public void createTable() {
        JPanel rootPanel = new JPanel(new BorderLayout(2, 2));
        rootPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Available Events",
                TitledBorder.CENTER, TitledBorder.TOP));
        table = new JTable();
        refreshEvents();
        JScrollPane scrollPane = new JScrollPane(table);
        rootPanel.add(scrollPane, BorderLayout.CENTER);
        setContentPane(rootPanel);
    }

    /**
     * Refreshes due date events in the table.
     */
    public void refreshEvents() {
        DefaultTableModel dm = (DefaultTableModel) table.getModel();
        dm.getDataVector().removeAllElements();
        int eventSize = STORAGE.size();
        String[][] data = new String[eventSize][TITLES.length];
        for (int i = 0; i < eventSize; ++i) {
            DueDateEvent event = STORAGE.get(i);
            data[i][0] = event.getTitle();
            data[i][1] = event.getTime();
            data[i][2] = Boolean.toString(event.isToBeTriggered());
            data[i][3] = event.getTask();
        }
        TableModel tableModel = new DefaultTableModel(data, TITLES);
        table.setModel(tableModel);
        table.revalidate();
    }

    /**
     * Makes additional preparations for the frame.
     */
    public void setUp() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setJMenuBar(new DueDateMenuBar(this));
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) size.getWidth() / 2;
        int height = (int) size.getHeight() / 2;
        setLocation(width / 2, height / 2);
        setSize(width, height);
    }
}
