package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.ParseException;
import java.util.Date;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.DateUtil;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Parser class used to parse a add command
 */
public class AddParser extends Parser {
	
	@Override
	public Command parseCommand(String args){
        
        final String DEADLINE_FLAG = "-d";
        final String EVENT_FLAG = "-e";
        String[] argsArray = splitAddArguments(args.trim());
        
        try {
            // when only task name is present in parameter
            if (argsArray.length == 1) {
                return new AddCommand(argsArray[0]);
            }
            
            // check if # of args is correct
            if (!(argsArray.length == 3 || argsArray.length == 4)) {
                System.out.println("wrong # of args");
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
            
            String taskName = argsArray[0];
            // parse deadline/event flag
            String taskFlag = argsArray[1];
            
            try {
                if (taskFlag.equals(DEADLINE_FLAG) && argsArray.length == 3) {
                    // handle deadline args
                    String endDateTime = argsArray[2];
                    Date endDate = DateUtil.parseStringToDate(endDateTime);
                    return new AddCommand(taskName, endDate);
                }
                else if (taskFlag.equals(EVENT_FLAG) && argsArray.length == 4) {
                    // handle event args
                    String startDateTime = argsArray[2];
                    String endDateTime = argsArray[3];
                    Date startDate = DateUtil.parseStringToDate(startDateTime);
                    Date endDate = DateUtil.parseStringToDate(endDateTime);
                    return new AddCommand(taskName, startDate, endDate);
                }
                else {
                    // unable to parse
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }
            } catch (ParseException e) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
	
	/**
     * Splits arguments of the add command
     * @param args
     * @return string array containing the split arguments
     */
    private String[] splitAddArguments(String args) {
        int indexFlag = args.indexOf('-');
        // args only contain task name
        if (indexFlag == -1) {
            String[] argsArray = new String[1];
            argsArray[0] = args;
            return argsArray;
        }
        // extract task name
        String taskName = args.substring(0, args.indexOf('-')).trim();
        String remainingArgs = args.substring(args.indexOf('-'), args.length());
        String[] remainingArgsArray = remainingArgs.split(" ");
        String[] argsArray = new String[remainingArgsArray.length + 1];
        argsArray[0] = taskName;
        for (int i = 0; i < remainingArgsArray.length; i++) {
            argsArray[i + 1] = remainingArgsArray[i];
        }
        
        return argsArray;
    }
}
