//@@author A0153658W
package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
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
            + "[END_DATETIME] [START_DATETIME END_DATETIME] [NEW_NAME]\n" + "Example: " + COMMAND_WORD + " 1"
            + " 12-10-2016";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    public static final String MESSAGE_EDIT_TASK_FAIL = "This task already exists in the task list";

    private static final int EDIT_CASE_DEADLINE = 0;
    private static final int EDIT_CASE_EVENT = 1;
    private static final int EDIT_CASE_FLOATING = 2;

    public final int targetIndex;
    private Name newName;
    private TaskDate endDateTime;
    private TaskDate startDateTime;
    private int editCase;

    /**
     * Constructor for editing specified task into a floating task
     */
    public EditCommand(int targetIndex, Name name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
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

        Task taskToEdit = (Task) lastShownList.get(targetIndex - 1);
        int taskIndex;
        Task resultTask = null;
        try {
            taskIndex = model.getIndex(taskToEdit);

            switch (editCase) {
            case EDIT_CASE_DEADLINE:
                resultTask = new DeadlineTask(taskToEdit.getName(), endDateTime);
                break;
            case EDIT_CASE_EVENT:
                resultTask = new EventTask(taskToEdit.getName(), startDateTime, endDateTime);
                break;
            case EDIT_CASE_FLOATING:
                resultTask = new Task(newName);
                break;
            default:
                assert false : "All cases should have been handled by EditParser.";
            }
            try {
                model.editTask(taskIndex, taskToEdit, resultTask);
            } catch (DuplicateTaskException e) {
                return new CommandResult(MESSAGE_EDIT_TASK_FAIL);
            }
        } catch (TaskNotFoundException e) {
            return new CommandResult(String.format(Messages.MESSAGE_TASK_NOT_FOUND, taskToEdit.toString()));
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, resultTask));
    }
}
