//@@author A0161247J
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.DateUtil;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.task.Name;
import seedu.task.model.task.TaskDate;

/**
 * Parser class used to parse an edit command
 */
public class EditParser extends Parser {
    private final Pattern NAME_FORMAT = Pattern.compile("^\\s*(\"(?<name>.*)\")\\s*.*");
    private final Pattern INDEX_FORMAT = Pattern.compile("^\\s*(?<index>\\d+).*");
    private final Pattern DEADLINE_ARGS_FORMAT = Pattern.compile("\\s*(?<index>\\d+)\\s*(?<endDate>\\d{2}-\\d{2}-\\d{4})\\s*(?<endTime>\\d{2}:\\d{2})?\\s*");
    private final Pattern EVENT_ARGS_FORMAT = Pattern.compile("\\s*(?<index>\\d+)\\s*(?<startDate>\\d{2}-\\d{2}-\\d{4})\\s*(?<startTime>\\d{2}:\\d{2})?\\s+(?<endDate>\\d{2}-\\d{2}-\\d{4})\\s*(?<endTime>\\d{2}:\\d{2})?\\s*");
    private final Pattern FLOATING_ARGS_FORMAT = Pattern.compile("\\s*(?<index>\\d+)\\s*(?<name>.+)");
    private final com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
    
    /**
     * Parses arguments in the context of the edit task command.
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command parseCommand(String args) {
        Command toReturn = null;
        boolean hasException = false;
        
        try {
            int index = getIndex(args);
            args = removeFromString(args, index);
            String name = getName(args);
            args = removeFromString(args, name);
            List<LocalDateTime> dates = getDates(args);
            
            if (isEventCommand(dates)) {
                toReturn = createEventTask(index, dates);
            } else if (isDeadlineCommand(dates)) {
                toReturn = createDeadlineTask(index, dates);
            } else if (isFloatingCommand(name, dates)) {
                toReturn = createFloatingTask(index, name);
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
                            EditCommand.MESSAGE_USAGE));
        }
        
        return toReturn;
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
    
    /**
     * Remove an integer from a string 
     */
    private String removeFromString(String original, int integer) {
        return removeFromString(original, "" + integer);
    }
    
    /**
     * Remove a string from another string
     */
    private String removeFromString(String original, String toRemove) {
        if (toRemove != null) {
            original = original.replace(toRemove, "");
        }
        
        return original;
    }
    
    /**
     * Retrieves index from string args
     * 
     * @throws IllegalArgumentException Unable to retrieve an integer from string
     */
    private int getIndex(String args) throws IllegalArgumentException {
        final Matcher matcher = INDEX_FORMAT.matcher(args);
        
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }
        
        String indexString = matcher.group("index");
        return tryParseIndex(indexString);
    }
    
    
    /**
     * Retrieves task name from string args which is separated by quotes "example name"
     * returns null if the name doesn't match
     */
    private String getName(String args) {
        final Matcher matcher = NAME_FORMAT.matcher(args);
        
        if (!matcher.matches()) {
            return null;
        }
        
        return matcher.group("name");
    }
    
    /**
     * Method used to retrieve the index from a string argument 
     * @param argIndex
     * @throws NullPointerException String argument is null
     * @throws IllegalArgumentException the string cannot be parsed to an integer
     */
    private int tryParseIndex(String argIndex) throws NullPointerException, IllegalArgumentException {
        Optional<Integer> index = parseIndex(argIndex);
        if(!index.isPresent()){
            throw new IllegalArgumentException();
        }
        
        return index.get();
    }
    
    private boolean isDeadlineCommand(List<LocalDateTime> dates) {
        return dates.size() == 1;
    }
    
    private boolean isEventCommand(List<LocalDateTime> dates) {
        return dates.size() == 2;
    }
    
    private boolean isFloatingCommand(String name, List<LocalDateTime> dates) {
        return dates.size() == 0 && name != null;
    }
    
    /**
     * Creates a EditCommand for a DeadlineTask given a name and a list containing a single date
     * 
     * @throws IllegalValueException There was an error trying to create the EditCommand
     * @throws IllegalArgumentException The number of dates inside the list of dates should be 1
     */
    private Command createDeadlineTask(int index, List<LocalDateTime> dates) throws IllegalArgumentException, IllegalValueException {
        if (dates.size() != 1) {
            throw new IllegalArgumentException();
        }
        
        LocalDateTime endDate = dates.get(0);
        
        return new EditCommand(index, new TaskDate(endDate));
    }
    
    /**
     * Creates an EditCommand for an EventTask given a name and a list of dates
     * 
     * @throws IllegalValueException There was an error creating the edit command
     * @throws IllegalArgumentException There should be 2 dates inside the list of dates
     */
    private Command createEventTask(int index, List<LocalDateTime> dates) throws IllegalArgumentException, IllegalValueException {
        if (dates.size() != 2) {
            throw new IllegalArgumentException();
        }
        
        LocalDateTime startDate = dates.get(0);
        LocalDateTime endDate = dates.get(1);
        
        return new EditCommand(index, new TaskDate(startDate), new TaskDate(endDate));
    }
    
    /**
     * Creates an AddCommand for a Task given a name
     * 
     * @throws IllegalValueException There was an error creating the EditCommand
     * @throws IllegalArgumentException The name argument shouldn't be null
     */
    private Command createFloatingTask(int index, String name) throws IllegalArgumentException, IllegalValueException {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        
        return new EditCommand(index, new Name(name));
    }
}
