package com.comcave.due.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

import static com.comcave.due.Constants.DATA_FILE;
import static com.comcave.due.Constants.DATA_FILE_NAME;
import static com.comcave.due.Constants.HEADERS;

/**
 * Represents the due date event storage. In fact, it is a decorator over a list of the events
 * that backs them up with the CSV file.
 */
public class DueDateEventStorage implements List<DueDateEvent> {

    private static final CSVFormat CSV_FORMAT;

    static {
        CSVFormat.Builder builder = CSVFormat.Builder.create();
        builder.setDelimiter(";");
        builder.setHeader(HEADERS);
        builder.setSkipHeaderRecord(true);
        CSV_FORMAT = builder.build();
    }

    private static final DueDateEventStorage instance = new DueDateEventStorage();

    private final List<DueDateEvent> events = new ArrayList<>();

    public static DueDateEventStorage getInstance() {
        return instance;
    }

    @Override
    public int size() {
        return events.size();
    }

    @Override
    public boolean isEmpty() {
        return events.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return events.contains(o);
    }

    @Override
    public Iterator<DueDateEvent> iterator() {
        return events.iterator();
    }

    @Override
    public Object[] toArray() {
        return events.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return a;
    }

    @Override
    public boolean add(DueDateEvent dueDateEvent) {
        events.add(dueDateEvent);
        executeWrite(printer -> printEvent(printer, dueDateEvent));
        return true;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = events.remove(o);
        rewriteEvents();
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return events.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends DueDateEvent> c) {
        writeEvents(c);
        return events.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends DueDateEvent> c) {
        boolean result = events.addAll(index, c);
        rewriteEvents();
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = events.removeAll(c);
        rewriteEvents();
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = events.retainAll(c);
        rewriteEvents();
        return result;
    }

    @Override
    public void clear() {
        try {
            deleteFile();
            createFile();
            events.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DueDateEvent get(int index) {
        return events.get(index);
    }

    @Override
    public DueDateEvent set(int index, DueDateEvent element) {
        DueDateEvent result = events.set(index, element);
        rewriteEvents();
        return result;
    }

    @Override
    public void add(int index, DueDateEvent element) {
        events.add(index, element);
        rewriteEvents();
    }

    @Override
    public DueDateEvent remove(int index) {
        DueDateEvent result = events.remove(index);
        rewriteEvents();
        return result;
    }

    @Override
    public int indexOf(Object o) {
        return events.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return events.lastIndexOf(o);
    }

    @Override
    public ListIterator<DueDateEvent> listIterator() {
        return events.listIterator();
    }

    @Override
    public ListIterator<DueDateEvent> listIterator(int index) {
        return events.listIterator(index);
    }

    @Override
    public List<DueDateEvent> subList(int fromIndex, int toIndex) {
        return events.subList(fromIndex, toIndex);
    }

    @Override
    public void replaceAll(UnaryOperator<DueDateEvent> operator) {
        events.replaceAll(operator);
        rewriteEvents();
    }

    @Override
    public void sort(Comparator<? super DueDateEvent> c) {
        events.sort(c);
        rewriteEvents();
    }

    @Override
    public Spliterator<DueDateEvent> spliterator() {
        return events.spliterator();
    }

    /**
     * Rewrites the events on the CSV file side.
     */
    public void rewriteEvents() {
        try {
            deleteFile();
            createFile();
            writeEvents(events);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DueDateEventStorage() {
        try {
            if (DATA_FILE.exists()) {
                try (Reader in = new FileReader(DATA_FILE_NAME)) {
                    Iterable<CSVRecord> records = CSV_FORMAT.parse(in);
                    for (CSVRecord record : records) {
                        String title = record.get(HEADERS[0]);
                        String time = record.get(HEADERS[1]);
                        boolean toBeTriggered = true;
                        String trigger = record.get(HEADERS[2]);
                        if (trigger != null) {
                            toBeTriggered = Boolean.parseBoolean(trigger);
                        }
                        String task = record.get(HEADERS[3]);
                        events.add(new DueDateEvent(title, time, toBeTriggered, task));
                    }
                }
            } else {
                createFile();
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static void createFile() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(DATA_FILE_NAME), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            try (CSVPrinter printer = new CSVPrinter(writer, CSV_FORMAT)) {
                printer.printRecord(HEADERS[0], HEADERS[1], HEADERS[2], HEADERS[3], HEADERS[4]);
            }
        }
    }

    private static void deleteFile() throws IOException {
        Files.deleteIfExists(Paths.get(DATA_FILE_NAME));
    }

    private static void executeWrite(CheckedConsumer<CSVPrinter> consumer) {
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get(DATA_FILE_NAME), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
                try (CSVPrinter printer = new CSVPrinter(writer, CSV_FORMAT)) {
                    consumer.accept(printer);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void printEvent(CSVPrinter printer, DueDateEvent event) throws IOException {
        String title = event.getTitle();
        String time = event.getTime();
        String toBeTriggered = Boolean.toString(event.isToBeTriggered());
        String task = event.getTask();
        String triggered = Boolean.toString(event.isTriggered());
        printer.printRecord(title, time, toBeTriggered, task, triggered);
    }

    private static void writeEvents(Collection<? extends DueDateEvent> events) {
        executeWrite(printer -> {
            for (DueDateEvent event : events) {
                printEvent(printer, event);
            }
        });
    }
}
