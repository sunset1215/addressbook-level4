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
	
	private Config config;
	
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
		config = new Config(oldConfig.get(), fileLocation);
	}
	
	

	@Override
	public CommandResult execute() {
		try {
			ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
			raise(new StorageFilePathChangedEvent(config.getTaskBookFilePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new CommandResult("It works!!!!!");
	}

}
