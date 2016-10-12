package seedu.address.logic.commands;


import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.DeadlineTask;
import seedu.address.model.EventTask;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;
import seedu.address.model.person.TaskDate;

/**
 * Deletes a task identified using it's last displayed index from the task list.
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

    public EditCommand(int targetIndex, TaskDate endDateTime) {
        this.targetIndex = targetIndex;
        this.endDateTime = endDateTime;
        editCase = EDIT_CASE_DEADLINE;
    }
    
    public EditCommand(int targetIndex, TaskDate endDateTime, TaskDate startDateTime) {
        this.targetIndex = targetIndex;
        this.endDateTime = endDateTime;
        this.startDateTime = startDateTime;
        editCase = EDIT_CASE_EVENT;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Task taskToEdit = (Task) lastShownList.get(targetIndex - 1);

        Task resultTask;
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
        taskToEdit = resultTask;

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

}
