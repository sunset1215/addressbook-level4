package seedu.task.logic.commands;

import seedu.task.model.TaskBook;
import seedu.task.model.task.UniqueTaskList.NoCompletedTasksFoundException;

/**
 * Clears completed tasks from the task book.
 * If option clear all is specified, clears the task book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String OPTION_CLEAR_ALL = "/a";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears completed tasks from the task book.\n"
            + "Parameters: [OPTION]\n"
            + "Example: " + COMMAND_WORD + " or " + COMMAND_WORD + " " + OPTION_CLEAR_ALL;
    
    public static final String MESSAGE_CLEAR_ALL_SUCCESS = "Task book has been cleared!";
    public static final String MESSAGE_CLEAR_COMPLETED_SUCCESS = "Completed tasks have been cleared!";
    public static final String MESSAGE_CLEAR_COMPLETED_FAIL = "There are no completed tasks to be cleared!";

    private String option;
    
    public ClearCommand(String option) {
        this.option = option;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        if (option.isEmpty()) {
            try {
                model.clearCompletedTasks();
            } catch (NoCompletedTasksFoundException e) {
                return new CommandResult(MESSAGE_CLEAR_COMPLETED_FAIL);
            }
            return new CommandResult(MESSAGE_CLEAR_COMPLETED_SUCCESS);
        }

        model.resetData(TaskBook.getEmptyTaskBook());
        return new CommandResult(MESSAGE_CLEAR_ALL_SUCCESS);
    }
}
