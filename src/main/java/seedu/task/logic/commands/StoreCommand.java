package seedu.task.logic.commands;

import java.io.IOException;
import java.util.Optional;

import seedu.task.commons.core.Config;
import seedu.task.commons.events.storage.StorageFilePathChangedEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.core.ComponentManager;

public class StoreCommand extends Command{
	
	public static final String COMMAND_WORD = "store";
	public static final String MESSAGE_USAGE = COMMAND_WORD + "C/documents " 
		    + "\nParameters:  [ FILE_LOCATION] " 
		    + "\nExample: " + COMMAND_WORD + " C/Documents/Tasks";
	
	public static final String MESSAGE_SUCCESS = "Storage Location Updated: %1$s";
	
	private Config newConfig;
	
	public StoreCommand() {
		
	}
	
	public StoreCommand(String fileLocation) {
		Optional<Config> oldConfig = null;
		try {
			oldConfig = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
		} catch (DataConversionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newConfig = new Config(oldConfig.get(), fileLocation);
	}
	
	

	@Override
	public CommandResult execute() {
		try {
			ConfigUtil.saveConfig(newConfig, Config.USER_CONFIG_FILE);
			model.indicateStorageFilePathChanged(newConfig.getTaskBookFilePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new CommandResult(String.format(MESSAGE_SUCCESS, newConfig.getTaskBookFilePath()));
	}

}
