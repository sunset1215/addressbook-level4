package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.person.TaskDate;

public class EditParser extends Parser{

	/**
     * Parses arguments in the context of the edit task command.
     * @param args full command args string
     * @return the prepared command
     */
	@Override
	public Command parseCommand(String args) {
        final String DEADLINE_FLAG = "-d";
        final String EVENT_FLAG = "-e";
        String[] argsArray = args.trim().split(" ");
        
        // check if # of args is correct
        if (!(argsArray.length == 3 || argsArray.length == 4)) {
            System.out.println("wrong # of args");
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        // parse index
        String argIndex = argsArray[0];
        Optional<Integer> index = parseIndex(argIndex);
        if(!index.isPresent()){
            System.out.println("invalid index");
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        // parse deadline/event flag
        String taskFlag = argsArray[1];
        
        try {
            if (taskFlag.equals(DEADLINE_FLAG) && argsArray.length == 3) {
                // handle deadline args
                String endDateTime = argsArray[2];
                Date endDate = DateUtil.parseStringToDate(endDateTime);
                return new EditCommand(index.get(), new TaskDate(endDate));
            }
            else if (taskFlag.equals(EVENT_FLAG) && argsArray.length == 4) {
                // handle event args
                String startDateTime = argsArray[2];
                String endDateTime = argsArray[3];
                Date startDate = DateUtil.parseStringToDate(startDateTime);
                Date endDate = DateUtil.parseStringToDate(endDateTime);
                return new EditCommand(index.get(), new TaskDate(startDate), new TaskDate(endDate));
            }
            else {
                // unable to parse
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        } catch (ParseException e) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

    }
}
