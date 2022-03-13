package com.comcave.due.view;

import com.comcave.due.model.DueDateEvent;
import com.comcave.due.model.DueDateEventStorage;
import com.comcave.due.model.DueDateFormatter;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Dialog for the new due date event creation.
 */
public class NewEventDialog {

    private static final DueDateEventStorage STORAGE = DueDateEventStorage.getInstance();

    private final DueDateFrame parent;

    private JButton cancelButton;

    private JDialog dialog;

    private JButton okButton;

    private DueDatePicker picker;

    private JTextField taskField;

    private JTextField titleField;

    private JCheckBox triggerBox;

    /**
     * Dialog's constructor.
     *
     * @param parent GUI frame
     */
    public NewEventDialog(DueDateFrame parent) {
        this.parent = Objects.requireNonNull(parent);
    }

    /**
     * Displays the dialog.
     */
    public void display() {
        SwingUtilities.invokeLater(() -> {
            dialog = new JDialog(parent, "New Event", true);
            dialog.setSize(parent.getSize());
            dialog.setResizable(false);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            JPanel panel = createPanel();
            setUpListeners();
            dialog.setContentPane(panel);
            dialog.setLocationRelativeTo(parent);
            dialog.pack();
            dialog.setVisible(true);
        });
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        JLabel titleLabel = new JLabel("Title:");
        panel.add(titleLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        titleField = new JTextField();
        panel.add(titleField, c);

        c.gridx = 0;
        c.gridy = 1;
        JLabel timeLabel = new JLabel("Time:");
        panel.add(timeLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        picker = new DueDatePicker(new Date());
        picker.setFormats(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
        picker.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
        panel.add(picker, c);

        c.gridx = 0;
        c.gridy = 2;
        JLabel triggerLabel = new JLabel("Trigger dialog:");
        panel.add(triggerLabel, c);

        c.gridx = 1;
        c.gridy = 2;
        triggerBox = new JCheckBox();
        triggerBox.setSelected(true);
        panel.add(triggerBox, c);

        c.gridx = 0;
        c.gridy = 3;
        JLabel taskLabel = new JLabel("Task:");
        panel.add(taskLabel, c);

        c.gridx = 1;
        c.gridy = 3;
        taskField = new JTextField();
        panel.add(taskField, c);

        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;
        c.gridy = 4;
        okButton = new JButton("OK");
        okButton.setEnabled(false);
        panel.add(okButton, c);

        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;
        c.gridy = 4;
        cancelButton = new JButton("Cancel");
        panel.add(cancelButton, c);

        return panel;
    }

    private static String dateToTime(Date date) {
        OffsetDateTime offsetTime = date.toInstant().atOffset(OffsetDateTime.now().getOffset());
        return DueDateFormatter.fromTime(offsetTime);
    }

    private void setUpListeners() {
        titleField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateOkButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateOkButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateOkButton();
            }
        });
        picker.addDueDateListener((oldDate, newDate) -> updateOkButton(newDate));
        okButton.addActionListener(e -> {
            String title = titleField.getText();
            String time = dateToTime(picker.getDate());
            boolean isToBeTriggered = triggerBox.isSelected();
            String task = taskField.getText();
            DueDateEvent event = new DueDateEvent(title, time, isToBeTriggered, task);
            STORAGE.add(event);
            parent.refreshEvents();
            dialog.dispose();
        });
        cancelButton.addActionListener(e -> dialog.dispose());
    }

    private void updateOkButton() {
        updateOkButton(picker.getDate());
    }

    private void updateOkButton(Date date) {
        String text = titleField.getText();
        okButton.setEnabled(text != null && !"".equals(text.trim()) && date != null);
    }
}
