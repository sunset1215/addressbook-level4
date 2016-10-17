package seedu.task.logic.commands;

import java.util.EmptyStackException;

/*
 * Command to undo most recent task
 */
public class UndoCommand extends Command{
	public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo the most recent task.";

    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Undo successful, reversed action: %s";
    public static final String MESSAGE_UNDO_TASK_FAIL = "Nothing to undo.";


    public UndoCommand() {}
    
	@Override
	public CommandResult execute() {
		try{
			model.undo();
		}
		catch(EmptyStackException e){
			return new CommandResult(MESSAGE_UNDO_TASK_FAIL);
		}
		return new CommandResult(String.format(MESSAGE_UNDO_TASK_SUCCESS, model.getUndoInformation()));
	}
	    
}
