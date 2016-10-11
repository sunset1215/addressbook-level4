package seedu.address.logic.commands;

import java.text.ParseException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.DeadlineTask;
import seedu.address.model.EventTask;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;
import seedu.address.model.person.TaskDate;
import seedu.address.model.person.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [-d e/END_DATETIME] [-e s/START_DATETIME e/END_DATETIME]\n"
            + "Example: " + COMMAND_WORD + " 1" + " -d e/12-10-2016";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    
    private static final int EDIT_CASE_DEADLINE = 0;
    private static final int EDIT_CASE_EVENT = 1;

    public final int targetIndex;
    private String endDateTime;
    private String startDateTime;
    private int editCase;

    public EditCommand(int targetIndex, String endDateTime) {
        this.targetIndex = targetIndex;
        this.endDateTime = endDateTime;
        editCase = EDIT_CASE_DEADLINE;
    }
    
    public EditCommand(int targetIndex, String endDateTime, String startDateTime) {
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
        try {
            switch (editCase) {
            case EDIT_CASE_DEADLINE:
                resultTask = new DeadlineTask(taskToEdit.getName(), new TaskDate(endDateTime));
                break;
            case EDIT_CASE_EVENT:
                resultTask = new EventTask(taskToEdit.getName(), new TaskDate(startDateTime), new TaskDate(endDateTime));
                break;
            default:
                return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
            }
            taskToEdit = resultTask;
        } catch (ParseException e) {
            return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
        

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

}
