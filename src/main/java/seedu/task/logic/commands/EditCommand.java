package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.task.DeadlineTask;
import seedu.task.model.task.EventTask;
import seedu.task.model.task.Name;
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
            + "[END_DATETIME] [START_DATETIME END_DATETIME] [NEW_NAME]\n"
            + "Example: " + COMMAND_WORD + " 1" + " 12-10-2016";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    public static final String MESSAGE_EDIT_TASK_FAIL = "This task already exists in the task list";
    
    private static final int EDIT_CASE_DEADLINE = 0;
    private static final int EDIT_CASE_EVENT = 1;
    private static final int EDIT_CASE_FLOATING = 2;

    public final int targetIndex;
    private String newName;
    private TaskDate endDateTime;
    private TaskDate startDateTime;
    private int editCase;

    /**
     * Constructor for editing specified task into a floating task
     */
    public EditCommand(int targetIndex, String name) {
    	if (name == null) {
    		throw new IllegalArgumentException();
    	}
        assert !CollectionUtil.isAnyNull(endDateTime);
        this.targetIndex = targetIndex;
        this.newName = name;
        editCase = EDIT_CASE_FLOATING;
    }
    
    /**
     * Constructor for editing specified task into a deadline
     */
    public EditCommand(int targetIndex, TaskDate endDateTime) {
        assert !CollectionUtil.isAnyNull(endDateTime);
        this.targetIndex = targetIndex;
        this.endDateTime = endDateTime;
        editCase = EDIT_CASE_DEADLINE;
    }
    
    /**
     * Constructor for editing specified task into an event
     */
    public EditCommand(int targetIndex, TaskDate startDateTime, TaskDate endDateTime) {
        assert !CollectionUtil.isAnyNull(startDateTime, endDateTime);
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
        
        try {
        	Task taskEditedTo = createTaskEditedTo(taskToEdit);
            model.editTask(taskToEdit, taskEditedTo);
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskEditedTo));
            
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        	return new CommandResult(MESSAGE_EDIT_TASK_FAIL);
        } catch (DuplicateTaskException dte) {
            return new CommandResult(MESSAGE_EDIT_TASK_FAIL);
        } catch (IllegalValueException e) {
        	return new CommandResult(MESSAGE_EDIT_TASK_FAIL);
		}
    }

    /**
     * Returns a new task based on the task to be edited and the parameters of the edit command
     * @throws IllegalValueException 
     */
    private Task createTaskEditedTo(ReadOnlyTask taskToEdit) throws IllegalValueException {
        Task taskEditedTo = null;
        switch (editCase) {
        case EDIT_CASE_DEADLINE:
            taskEditedTo = new DeadlineTask(taskToEdit.getName(), endDateTime, taskToEdit.getStatus());
            break;
        case EDIT_CASE_EVENT:
            taskEditedTo = new EventTask(taskToEdit.getName(), startDateTime, endDateTime, taskToEdit.getStatus());
            break;
        case EDIT_CASE_FLOATING:
        	taskEditedTo = new Task(new Name(newName), taskToEdit.getStatus());
        	break;
        default:
            assert false : "The task to edit to must either be a deadline or an event";
        }
        return taskEditedTo;
    }
}
