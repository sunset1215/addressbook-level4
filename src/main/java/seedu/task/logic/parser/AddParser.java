//@@author A0161247J
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Parser class used to parse a add command
 */
public class AddParser extends Parser {
    private final Pattern NAME_FORMAT = Pattern.compile("^\\s*(\"(?<name>.*)\")\\s*.*");
    private final com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
    
    @Override
    public Command parseCommand(String args) {
        Command toReturn = null;
        boolean hasException = false;
        
        try {
            String name = getName(args);
            String dateString = removeFromString(args, name);
            List<LocalDateTime> dates = getDates(dateString);
            
            if (isEventCommand(dates)) {
                toReturn = createEventTask(name, dates);
            } else if (isDeadlineCommand(dates)) {
                toReturn = createDeadlineTask(name, dates);
            } else if (isFloatingCommand(name, dates)) {
                toReturn = createFloatingTask(name);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (NullPointerException e) {
            hasException = true;
        } catch (IllegalArgumentException e) {
            hasException = true;
        } catch (IllegalValueException e) {
            hasException = true;
        }
        
        if (hasException) {
            toReturn = new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                            AddCommand.MESSAGE_USAGE));
        }
        
        return toReturn;
    }
    
    /**
     * Retrieves task name from string args which is separated by quotes "example name"
     */
    private String getName(String args) throws IllegalArgumentException {
        final Matcher matcher = NAME_FORMAT.matcher(args);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }
        
        return matcher.group("name");
    }
    
    /**
     * Retrieves task dates from string args
     */
    private List<LocalDateTime> getDates(String args) {
        List<DateGroup> dateGroups = parser.parse(args);
        if (dateGroups.size() == 0) {
            return new ArrayList<LocalDateTime>();
        }
        
        DateGroup group = dateGroups.get(0);
        return extractLocalDates(group);
    }
    
    private String removeFromString(String original, String toRemove) {
        if (toRemove != null) {
            original = original.replace(toRemove, "");
        }
        
        return original;
    }
    
    
    /**
     * Extracts the local dates from a given dateGroup
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
    
    private boolean isDeadlineCommand(List<LocalDateTime> dates) throws NullPointerException {
        return dates.size() == 1;
    }
    
    private boolean isEventCommand(List<LocalDateTime> dates) throws NullPointerException {
        return dates.size() == 2;
    }
    
    private boolean isFloatingCommand(String name, List<LocalDateTime> dates) throws NullPointerException {
        return dates.size() == 0 && name != null;
    }
    
    /**
     * Creates a EditCommand for a DeadlineTask given a name and a list containing a single date
     * 
     * @throws IllegalValueException 
     * @throws IllegalArgumentException 
     */
    private Command createDeadlineTask(String name, List<LocalDateTime> dates) throws IllegalArgumentException, IllegalValueException {
        if (dates.size() != 1 || name == null) {
            throw new IllegalArgumentException();
        }
        
        LocalDateTime endDate = dates.get(0);
        
        return new AddCommand(name, endDate);
    }
    
    /**
     * Creates an EditCommand for an EventTask given a name and a list of dates
     * 
     * @throws IllegalValueException 
     * @throws IllegalArgumentException 
     */
    private Command createEventTask(String name, List<LocalDateTime> dates) throws IllegalArgumentException, IllegalValueException {
        if (dates.size() != 2 || name == null) {
            throw new IllegalArgumentException();
        }
        
        LocalDateTime startDate = dates.get(0);
        LocalDateTime endDate = dates.get(1);
        
        return new AddCommand(name, startDate, endDate);
    }
    
    /**
     * Creates an AddCommand for a Task given a name
     * 
     * @throws IllegalValueException
     * @throws IllegalArgumentException 
     */
    private Command createFloatingTask(String name) throws IllegalArgumentException, IllegalValueException {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        
        return new AddCommand(name);
    }
}
