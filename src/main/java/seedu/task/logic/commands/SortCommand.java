package seedu.task.logic.commands;

/**
 * Sorts the task book.
 * @author Vivian
 *
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Sorts task book and order by end date then name.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SORT_SUCCESS = "Task book has been sorted!";

    public SortCommand() {}
    
    @Override
    public CommandResult execute() {
        model.sort();
        return new CommandResult(MESSAGE_SORT_SUCCESS);
    }

}
