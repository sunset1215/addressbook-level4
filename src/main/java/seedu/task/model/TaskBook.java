//@@author A0153658W 
package seedu.task.model;

import javafx.collections.ObservableList;
import seedu.task.commons.core.Messages;
import seedu.task.model.task.DeadlineTask;
import seedu.task.model.task.EventTask;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.task.model.task.UniqueTaskList.TaskAlreadyCompletedException;
import seedu.task.model.task.UniqueTaskList.NoCompletedTasksFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-book level Duplicates are not allowed (by .equals
 * comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private UniqueTaskList tasks;
    private UndoTaskStack undoTaskStack;

    private static final String UNDO_ADD_COMMAND = "add";
    private static final String UNDO_DELETE_COMMAND = "delete";
    private static final String UNDO_EDIT_COMMAND = "edit";
    private static final String UNDO_COMPLETE_COMMAND = "complete";
    private static final String UNDO_CLEAR_COMMAND = "clear";
    private static final String UNDO_CLEAR_ALL_COMMAND = "clear all";

    {
        tasks = new UniqueTaskList();
        undoTaskStack = new UndoTaskStack();
    }

    public TaskBook() {
    }

    // @@author

    /**
     * Tasks are copied into this task book
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * Tasks are copied into this task book
     */
    public TaskBook(UniqueTaskList tasks) {
        this.tasks = copyUniqueTaskList(tasks);
        // the line of code below is the original code
        // I used the above method to copy the lists
        // because the original code changes all the tasks that is read from
        // the storage into tasks, instead of keeping them as event task or
        // deadline task or task.
        // resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskBook getEmptyTaskBook() {
        return new TaskBook();
    }

    //// list overwrite operations

    /*
     * Returns a copy of the given unique task list
     */
    private UniqueTaskList copyUniqueTaskList(UniqueTaskList tasks) {
        UniqueTaskList newList = new UniqueTaskList();
        for (int i = 0; i < tasks.size(); i++) {
            try {
                newList.add(tasks.getTaskFromIndex(i));
            } catch (DuplicateTaskException e) {
                // this should not happen since we're just copying items over to
                // a new list
            }
        }
        return newList;
    }

    // @@author A0153658W-reused
    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks) {
        System.out.println(newTasks.toString());
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getTaskList());
    }
    // @@author A0153658W
    //// task-level operations

    /**
     * Adds a task to the task book.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     */
    public void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        tasks.add(task);
        undoTaskStack.pushAddToUndoStack(UNDO_ADD_COMMAND, task, -1);
    }

    /**
     * Adds a task to the task list at a given index.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     */
    public void addTask(int taskIndex, Task task) throws UniqueTaskList.DuplicateTaskException {
        tasks.add(taskIndex, task);
    }

    public boolean removeTask(ReadOnlyTask key, String callingCommand) throws UniqueTaskList.TaskNotFoundException {
        int targetIndex = tasks.getIndex(key);

        if (tasks.remove(key)) {
            undoTaskStack.pushDeleteToUndoStack(key, callingCommand, targetIndex);
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Edits a task to the task at a given index.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     * @throws TaskNotFoundException
     */
    public void editTask(int taskIndex, Task taskToEdit, Task resultTask)
            throws UniqueTaskList.DuplicateTaskException, TaskNotFoundException {
        undoTaskStack.pushEditToUndoStack(UNDO_EDIT_COMMAND, taskToEdit, taskIndex);

        try {
            tasks.edit(taskIndex, resultTask);
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException();
        }
    }

    // @@author A0138704E
    /**
     * Completes a task in the task book.
     * 
     * @param target
     *            task to be completed
     * @throws TaskNotFoundException
     *             if target task cannot be found
     * @throws TaskAlreadyCompletedException
     *             if target task is already marked as complete
     */
    public void completeTask(ReadOnlyTask target) throws TaskNotFoundException, TaskAlreadyCompletedException {
        int targetIndex = tasks.getIndex(target);
        Task taskToComplete = tasks.getTaskFromIndex(targetIndex);
        if (taskToComplete.isComplete()) {
            throw new TaskAlreadyCompletedException();
        }
        taskToComplete.setComplete();

        undoTaskStack.pushCompleteToUndoStack(taskToComplete, UNDO_COMPLETE_COMMAND, targetIndex);
    }

    // @@author A0153658W
    public int getIndex(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        return tasks.getIndex(key);
    }

    /**
     * Clears completed tasks from the task book
     * 
     * @throws NoCompletedTasksFoundException
     *             if no completed tasks were found
     * @throws TaskNotFoundException
     */
    public void clearCompletedTasks() throws NoCompletedTasksFoundException, TaskNotFoundException {
        UniqueTaskList copyTasks = copyUniqueTaskList(tasks);
        List<Task> clearedTasks = new ArrayList<Task>();
        List<Integer> clearedTasksIndices = new ArrayList<Integer>();

        prepareCompletedTasksForUndo(copyTasks, clearedTasks, clearedTasksIndices);

        // actually remove the completed tasks
        for (Task readTask : copyTasks) {
            if (readTask.isComplete()) {
                try {
                    tasks.remove(readTask);
                } catch (TaskNotFoundException e) {
                    throw new TaskNotFoundException();
                }
            }
        }
        if (copyTasks.size() == tasks.size()) {
            throw new NoCompletedTasksFoundException();
        }

        undoTaskStack.pushClearCompletedToUndoStack(clearedTasks, clearedTasksIndices, UNDO_CLEAR_COMMAND);
    }

    /*
     * Helper method to compile set of tasks and indices for clearing all
     * completed tasks to prepare for undo stack
     */
    private void prepareCompletedTasksForUndo(UniqueTaskList copyTasks, List<Task> clearedTasks,
            List<Integer> clearedTasksIndices) throws TaskNotFoundException {
        for (Task readTask : copyTasks) {
            if (readTask.isComplete()) {
                try {
                    clearedTasksIndices.add(tasks.getIndex(readTask));

                    Class<? extends ReadOnlyTask> clearedTask = readTask.getClass();

                    if (clearedTask.equals(DeadlineTask.class)) {
                        DeadlineTask cleared = new DeadlineTask(readTask.getName(), readTask.getEnd());
                        clearedTasks.add(cleared);
                    } else if (clearedTask.equals(EventTask.class)) {
                        EventTask cleared = new EventTask(readTask.getName(), readTask.getStart(), readTask.getEnd());
                        clearedTasks.add(cleared);
                    } else {
                        // cleared task must be a floating task
                        Task cleared = new Task(readTask.getName());
                        clearedTasks.add(cleared);
                    }
                } catch (TaskNotFoundException e) {
                    throw new TaskNotFoundException();
                }
            }
        }
    }

    /**
     * Clears all tasks from the task book
     * 
     * @throws TaskNotFoundException
     */
    public void clearAllTasks() throws TaskNotFoundException {
        UniqueTaskList copyTasks = copyUniqueTaskList(tasks);
        List<Task> clearedTasks = new ArrayList<Task>();
        List<Integer> clearedTasksIndices = new ArrayList<Integer>();
        List<String> clearedStatus = new ArrayList<String>();

        prepareClearedTasksForUndo(copyTasks, clearedTasks, clearedTasksIndices, clearedStatus);

        // actually remove the completed tasks
        for (Task readTask : copyTasks) {
            try {
                tasks.remove(readTask);
            } catch (TaskNotFoundException e) {
                throw new TaskNotFoundException();
            }
        }

        undoTaskStack.pushClearAllToUndoStack(clearedTasks, clearedTasksIndices, clearedStatus, UNDO_CLEAR_ALL_COMMAND);
    }

    /*
     * Helper method to compile set of tasks and indices for clearing all tasks
     * to prepare for undo stack
     */
    private void prepareClearedTasksForUndo(UniqueTaskList copyTasks, List<Task> clearedTasks,
            List<Integer> clearedTasksIndices, List<String> clearedStatus) throws TaskNotFoundException {
        // compile set of tasks and indices being cleared to prepare for undo
        // stack
        for (Task readTask : copyTasks) {
            try {
                clearedTasksIndices.add(tasks.getIndex(readTask));
                clearedStatus.add(readTask.getStatus().toString());

                Class<? extends ReadOnlyTask> clearedTask = readTask.getClass();

                if (clearedTask.equals(DeadlineTask.class)) {
                    DeadlineTask cleared = new DeadlineTask(readTask.getName(), readTask.getEnd());
                    clearedTasks.add(cleared);
                } else if (clearedTask.equals(EventTask.class)) {
                    EventTask cleared = new EventTask(readTask.getName(), readTask.getStart(), readTask.getEnd());
                    clearedTasks.add(cleared);
                } else {
                    // cleared task must be a floating task
                    Task cleared = new Task(readTask.getName());
                    clearedTasks.add(cleared);
                }

            } catch (TaskNotFoundException e) {
                throw new TaskNotFoundException();
            }
        }
    }

    public void undoTask() {
        undoTaskStack.undo(tasks);
    }

    public String getUndoInformation() {
        return undoTaskStack.getUndoInformation();
    }

    // @@author
    /** Sorts the task book order by end date, then name */
    public void sort() {
        tasks.sort();
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                        && this.tasks.equals(((TaskBook) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(tasks);
    }

}
