package seedu.task.storage;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskBook;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.task.model.TaskBook}.
 */
public interface TaskListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskListFilePath();

    /**
     * Returns TaskList data as a {@link ReadOnlyTaskBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskBook> readTaskList() throws DataConversionException, IOException;

    /**
     * @see #getTaskBookFilePath()
     */
    Optional<ReadOnlyTaskBook> readTaskList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskBook} to the storage.
     * @param taskList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(ReadOnlyTaskBook taskList) throws IOException;

    /**
     * @see #saveTaskList(ReadOnlyTaskBook)
     */
    void saveTaskList(ReadOnlyTaskBook taskList, String filePath) throws IOException;

}
