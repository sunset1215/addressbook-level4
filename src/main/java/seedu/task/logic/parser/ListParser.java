/**
 * @author A0161247J
 */
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;

import seedu.task.commons.util.DateUtil;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCommand;

/**
 * Parser class used to parse a list command
 */
public class ListParser extends Parser {
    
    private final com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
    private LocalDate date = null;
    
    @Override
    public Command parseCommand(String arguments) {
        arguments = arguments.trim();
        if (!isValidArgs(arguments)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        if (date != null) {
            return new ListCommand(DateUtil.formatLocalDateToString(date));
        }
        return new ListCommand(arguments);
    }

    /**
     * Returns true if arguments for clear command are valid
     */
    private boolean isValidArgs(String arguments) {
        return arguments.isEmpty() 
                || arguments.equals(ListCommand.OPTION_LIST_ALL)
                || arguments.equals(ListCommand.OPTION_LIST_COMPLETE)
                || isLocalDateTime(arguments);
    }

    private boolean isLocalDateTime(String arguments) {
        List<LocalDateTime> dates = getDates(arguments);
        if (dates.size() == 1) {
            date = dates.get(0).toLocalDate();
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Retrieves task dates from string using the Natty parser
     */
    private List<LocalDateTime> getDates(String args) {
        List<DateGroup> dateGroups = parser.parse(args);
        if (dateGroups.size() == 0) {
            return new ArrayList<LocalDateTime>();
        }
        
        DateGroup group = dateGroups.get(0);
        return extractLocalDates(group);
    }
    
    /**
     * Extracts the local dates as a list of LocalDateTime from a given DateGroup object
     */
    private List<LocalDateTime> extractLocalDates(DateGroup dateGroup) {
        List<Date> dates = dateGroup.getDates();
        
        List<LocalDateTime> localDates = new ArrayList<>();
        for (Date date : dates) {
            LocalDateTime local = LocalDateTime
                    .ofInstant(date.toInstant(), ZoneId.systemDefault());
            localDates.add(local);
        }
        return localDates;
    }

}
