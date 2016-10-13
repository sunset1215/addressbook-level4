package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.DeadlineTask;
import seedu.task.model.task.EventTask;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the task list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[-d END_DATETIME] [-e START_DATETIME END_DATETIME]\n"
            + "Example: " + COMMAND_WORD + " 1" + " -d 12-10-2016";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    
    private static final int EDIT_CASE_DEADLINE = 0;
    private static final int EDIT_CASE_EVENT = 1;

    public final int targetIndex;
    private TaskDate endDateTime;
    private TaskDate startDateTime;
    private int editCase;

    /**
     * Constructor for editing specified task to a deadline
     * @param targetIndex specified task
     * @param endDateTime deadline end date/time
     */
    public EditCommand(int targetIndex, TaskDate endDateTime) {
        this.targetIndex = targetIndex;
        this.endDateTime = endDateTime;
        editCase = EDIT_CASE_DEADLINE;
    }
    
    /**
     * Constructor for editing specified task to an event
     * @param targetIndex specified task
     * @param endDateTime event start date/time
     * @param startDateTime event end date/time
     */
    public EditCommand(int targetIndex, TaskDate startDateTime, TaskDate endDateTime) {
        this.targetIndex = targetIndex;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        editCase = EDIT_CASE_EVENT;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        int taskIndex;
        Task resultTask = null;
        
        try {
			taskIndex = model.getIndex(taskToEdit);
			model.deleteTask(taskToEdit);
		
	        switch (editCase) {
	        case EDIT_CASE_DEADLINE:
	            resultTask = new DeadlineTask(taskToEdit.getName(), endDateTime);
	            break;
	        case EDIT_CASE_EVENT:
	            resultTask = new EventTask(taskToEdit.getName(), startDateTime, endDateTime);
	            break;
	        default:
	            return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
	        }
	        
	        try {
				model.addTask(taskIndex, resultTask);
			} catch (DuplicateTaskException e) {
				e.printStackTrace();
			}
	        
        } catch (TaskNotFoundException e) {
			e.printStackTrace();
		}

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, resultTask));
    }

}
