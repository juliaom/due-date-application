package com.comcave.due.model;

import java.io.IOException;

/**
 * Functional interface that represents consumer that can throw IOException.
 *
 * @param <T> type of accept argument
 */
@FunctionalInterface
public interface CheckedConsumer<T> {

    /**
     * Main and the only method of the interface.
     *
     * @param t passed parameter
     * @throws IOException possibly thrown exception
     */
    void accept(T t) throws IOException;
}
