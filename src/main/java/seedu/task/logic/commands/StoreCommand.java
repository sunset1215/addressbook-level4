package seedu.task.logic.commands;

import java.io.File;
import java.io.IOException;

import javafx.stage.DirectoryChooser;
import seedu.task.commons.core.Config;
import seedu.task.commons.events.ui.DisplayDirectoryChooserRequestEvent.SelectedFilePathEmptyException;
import seedu.task.commons.util.ConfigUtil;
import javafx.stage.Stage;

/**
 * Stores current task book in the specified location.
 */
public class StoreCommand extends Command{
	
	public static final String COMMAND_WORD = "store";
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Stores current data in the given location. " 
		    + "\nParameters: [FILE_LOCATION] " 
		    + "\nExample: " + COMMAND_WORD + " C:\\Users\\Jim\\Desktop";
	
	public static final String MESSAGE_SUCCESS = "Storage Location Updated: %1$s";
	public static final String MESSAGE_FAIL = "Unable to save new location into config";
	
	private String newSaveLocation;
	
	public StoreCommand() {
			
		
	}
	
	public StoreCommand(String fileLocation) {
		newSaveLocation = fileLocation;
	}

	@Override
	public CommandResult execute() {
		try {
			newSaveLocation = model.changeStorageFilePath(newSaveLocation);
		} catch (SelectedFilePathEmptyException e) {
			return new CommandResult(MESSAGE_FAIL);
		} catch (IOException e) {
			return new CommandResult(MESSAGE_FAIL);
		}
		return new CommandResult(String.format(MESSAGE_SUCCESS, newSaveLocation));
	}

}
