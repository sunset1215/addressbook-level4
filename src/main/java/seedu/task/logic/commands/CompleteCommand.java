package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Status;
import seedu.task.model.task.UniqueTaskList.TaskAlreadyCompletedException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0138704E
/**
 * Completes a task identified using it's last displayed index from the task list.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed task: %1$s";
    public static final String MESSAGE_TASK_ALREADY_COMPLETED = "This task is already completed!";

    public final int targetIndex;

    public CompleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex - 1);
        try {
            model.completeTask(taskToComplete);
            model.updateFilteredListByStatus(Status.STATUS_PENDING);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        } catch (TaskAlreadyCompletedException tace) {
            return new CommandResult(MESSAGE_TASK_ALREADY_COMPLETED);
        }
        
        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
    }
    
}
