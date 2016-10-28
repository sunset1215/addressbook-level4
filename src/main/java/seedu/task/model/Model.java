package seedu.task.model;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.ui.DisplayDirectoryChooserRequestEvent.DirectoryChooserOperationCancelledException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.NoCompletedTasksFoundException;
import seedu.task.model.task.UniqueTaskList.TaskAlreadyCompletedException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

import java.io.IOException;
import java.util.EmptyStackException;
import java.time.LocalDate;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the TaskBook */
    ReadOnlyTaskBook getTaskBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target, String callingCommand) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task taskToAdd) throws DuplicateTaskException;
    
    /** Adds the given task at a given index */
    void addTask(int index, Task taskToAdd) throws UniqueTaskList.DuplicateTaskException;
    
    /** Edits the given task */
    void editTask(int taskIndex, Task taskToEdit, Task resultTask) throws DuplicateTaskException;
    
    /** Get index of given task*/
    int getIndex(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Completes the given task */
    void completeTask(ReadOnlyTask taskToComplete) throws TaskNotFoundException, TaskAlreadyCompletedException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredTaskList(Set<String> keywords);

    /** Changes storage file path */
    String changeStorageFilePath(String newFilePath) throws DirectoryChooserOperationCancelledException, IOException;
    
    /** Undo the most recent task */
    void undo() throws EmptyStackException;
    
    /**Provide undo information to user**/
    String getUndoInformation();

    /** 
     * Updates the filter of the filtered task list to filter tasks by status
     * @param status true = completed tasks, false = pending tasks 
     */
    void updateFilteredListByStatus(boolean status);
    
    /** Updates the filter of the filtered task list to filter tasks by task end date */
    void updateFilteredListByDate(LocalDate date);

    /** Clears completed tasks from the task book */
    void clearCompletedTasks() throws NoCompletedTasksFoundException;
    
    /** Clears all tasks from the task book */
    void clearAllTasks();

    /** Sorts the task book, order by end date then name */
    void sort();

}
