package seedu.task.model;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

import java.util.EmptyStackException;
import java.util.Set;

import com.google.common.base.Throwables;

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
    void addTask(Task taskToAdd) throws UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given task at a given index */
    void addTask(int index, Task taskToAdd) throws UniqueTaskList.DuplicateTaskException;
    
    /** Get index of given task*/
    int getIndex(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Undo the most recent task*/
    void undo() throws EmptyStackException;
    
    /**Provide undo information to user**/
    String getUndoInformation();
}
