package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.*;

import java.time.LocalDateTime;

/**
 * Adds a task to our task book
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. " 
    + "\nParameters: TASK_NAME [END_DATETIME] [START_DATETIME END_DATETIME] " 
    + "\nExample: " + COMMAND_WORD + " Dave's Birthday Party 12-01-2016 14:00 12-02-2016 16:00";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Constructor for adding an event task
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, LocalDateTime startDate, LocalDateTime endDate)
            throws IllegalValueException {
    	
        this.toAdd = new EventTask(
                new Name(taskName),
                new TaskDate(startDate),
                new TaskDate(endDate)
        );
    }
    
    /**
     * Constructor for adding a deadline task
     * 
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, LocalDateTime endDate)
            throws IllegalValueException {
        
        this.toAdd = new DeadlineTask(
                new Name(taskName),
                new TaskDate(endDate)
        );
    }
    
    /**
     * Constructor for adding a floating task
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName)
            throws IllegalValueException {
        
        this.toAdd = new Task(
                new Name(taskName)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getFilteredTaskList().size() - 1));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
