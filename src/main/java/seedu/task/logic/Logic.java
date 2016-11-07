package seedu.task.logic;

import javafx.collections.ObservableList;
import seedu.task.logic.commands.CommandResult;
import seedu.task.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the filtered list of pending tasks */
    ObservableList<ReadOnlyTask> getPendingTaskList();
    
    /** Returns the filtered list of tasks due today */
    ObservableList<ReadOnlyTask> getTodayTaskList();
    
    /** Returns the size of task book */
    int getSizeOfTaskBook();

}
