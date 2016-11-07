//@@author A0153658W-reused
package seedu.task.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.task.commons.exceptions.DuplicateDataException;
import seedu.task.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}
    
    /**
     * Signals that an operation marking a task as complete in the list would fail because
     * it is already marked as complete.
     */
    public static class TaskAlreadyCompletedException extends Exception {}
    
    /**
     * Signals that the operation clearing completed tasks from the task book has not deleted
     * any task because there were no completed tasks to be found.
     */
    public static class NoCompletedTasksFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }
    
    /**
     * Adds a task to the list at given index.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(int taskIndex, Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(taskIndex, toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }
    
    /**
     * Edits the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public void edit(int taskIndex, Task toEdit) throws TaskNotFoundException {
        assert toEdit != null;
        internalList.set(taskIndex, toEdit);
    }
    
    /**
     * Returns the task at the given index.
     */
    public Task getTaskFromIndex(int index) {
        return internalList.get(index);
    }
    
    /**
     * Returns the index of the given task.
     */
    public int getIndex(ReadOnlyTask target) throws TaskNotFoundException{
    	return internalList.indexOf(target);
    }
    
    /**
     * Replaces the task in the list at the given index
     * 
     * @throws DuplicateTaskException if task to replace to already exists
     */
    public void replace(int index, Task editTo) throws DuplicateTaskException {
        assert editTo != null;
        if (contains(editTo)) {
            throw new DuplicateTaskException();
        }
        internalList.set(index, editTo);
    }
    
    /**
     * Returns the size of the list.
     */
    public int size() {
        return internalList.size();
    }
    
    /**
     * Sorts the task book order by end date then name.
     */
    public void sort() {
        FXCollections.sort(internalList, TaskComparator.NAME);
        FXCollections.sort(internalList, TaskComparator.END_DATE);
    }
    
    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
