//@@author A0153658W
package seedu.task.logic.commands;

import java.util.EmptyStackException;
import seedu.task.model.task.Status;

/*
 * Command to undo most recent task
 */
public class UndoCommand extends Command{
	public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo the most recent task.";

    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Undo successful, reversed action: %s";
    public static final String MESSAGE_UNDO_STACK_END = "Nothing to undo.";


    public UndoCommand() {}
    
	@Override
	public CommandResult execute() {
		try{
			model.undo();
			model.updateFilteredListByStatus(Status.STATUS_PENDING);
		}
		catch(EmptyStackException e){
			return new CommandResult(MESSAGE_UNDO_STACK_END);
		}
		return new CommandResult(String.format(MESSAGE_UNDO_TASK_SUCCESS, model.getUndoInformation()));
	}
	    
}
