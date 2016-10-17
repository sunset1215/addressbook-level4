package seedu.address.logic.commands;

import seedu.address.commons.core.Config;

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
		this.config.setAddressBookFilePath(fileLocation);
	}
	
	

	@Override
	public CommandResult execute() {
		
		return null;
	}

}
