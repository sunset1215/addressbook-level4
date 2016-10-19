package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.util.DateUtil;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.task.TaskDate;

/**
 * Parser class used to parse an edit command
 */
public class EditParser extends Parser{
	private final Pattern FLOATING_ARGS_FORMAT = Pattern.compile("\\s*(?<index>\\d+)\\s*(?<name>.+)");
	private final Pattern DEADLINE_ARGS_FORMAT = Pattern.compile("\\s*(?<index>\\d+)\\s*(?<endDate>\\d{2}-\\d{2}-\\d{4})\\s*");
	private final Pattern EVENT_ARGS_FORMAT = Pattern.compile("\\s*(?<index>\\d+)\\s*(?<startDate>\\d{2}-\\d{2}-\\d{4})\\s*(?<endDate>\\d{2}-\\d{2}-\\d{4})\\s*");
	
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
			if (isDeadlineCommand(args)) {
				toReturn = createDeadlineTask(args);
			} else if (isEventCommand(args)) {
				toReturn = createEventTask(args);
			} else if (isFloatingCommand(args)) {
				toReturn = createFloatingTask(args);
			} else {
				throw new IllegalArgumentException();
			}
		} catch (NullPointerException e) {
        	hasException = true;
        } catch (ParseException e) {
        	hasException = true;
        } catch (IndexOutOfBoundsException e) {
        	hasException = true;
        } catch (IllegalArgumentException e) {
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
	 * Method used to retrieve the index from a string argument 
	 * @param argIndex
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	private int tryParseIndex(String argIndex) throws NullPointerException, IllegalArgumentException {
        Optional<Integer> index = parseIndex(argIndex);
        if(!index.isPresent()){
            System.out.println("invalid index");
            throw new IllegalArgumentException();
        }
        
        return index.get();
	}
	
	private boolean isDeadlineCommand(String args) throws NullPointerException {
		final Matcher matcher = DEADLINE_ARGS_FORMAT.matcher(args);
		return matcher.matches();
	}
	
	private boolean isEventCommand(String args) throws NullPointerException {
		final Matcher matcher = EVENT_ARGS_FORMAT.matcher(args);
		return matcher.matches();
	}
	
	private boolean isFloatingCommand(String args) throws NullPointerException {
		final Matcher matcher = FLOATING_ARGS_FORMAT.matcher(args);
		return matcher.matches();
	}
	
	/**
	 * Creates a DeadlineTask given a list of arguments expects args to have the form
	 * "[INDEX] 00-00-0000"
	 * 
	 * @throws ParseException 
	 * @throws IllegalArgumentException
	 */
	private Command createDeadlineTask(String args) throws IllegalArgumentException, ParseException {
		Matcher matcher = DEADLINE_ARGS_FORMAT.matcher(args);
		
		if (!matcher.matches()) {
			throw new IllegalArgumentException();
		}
		
		String indexString = matcher.group("index").trim();
		String endDateString = matcher.group("endDate").trim();
		
		int index = tryParseIndex(indexString);
        Date endDate = DateUtil.parseStringToDate(endDateString);
        return new EditCommand(index, new TaskDate(endDate));
	}
	
	/**
	 * Creates an EventTask given a string argument that has the form
	 * "[INDEX] 00-00-0000 00-00-0000"
	 * 
	 * @throws ParseException
	 * @throws IllegalArgumentException 
	 */
	private Command createEventTask(String args) throws ParseException, IllegalArgumentException {
		Matcher matcher = EVENT_ARGS_FORMAT.matcher(args);
		
		if (!matcher.matches()) {
			throw new IllegalArgumentException();
		}
		
		String indexString = matcher.group("index").trim();
		String startDateString = matcher.group("startDate").trim();
		String endDateString = matcher.group("endDate").trim();
		
		int index = tryParseIndex(indexString);
		Date startDate = DateUtil.parseStringToDate(startDateString);
        Date endDate = DateUtil.parseStringToDate(endDateString);
        return new EditCommand(index, new TaskDate(startDate), new TaskDate(endDate));
	}
	
	/**
	 * Creates a Task given an index and a name
	 * "[INDEX] thisisanewname"
	 * 
	 * @throws ParseException 
	 * @throws IllegalArgumentException
	 */
	private Command createFloatingTask(String args) throws IllegalArgumentException, ParseException {
		Matcher matcher = FLOATING_ARGS_FORMAT.matcher(args);
		
		if (!matcher.matches()) {
			throw new IllegalArgumentException();
		}
		
		String indexString = matcher.group("index").trim();
		String name = matcher.group("name").trim();
		
		int index = tryParseIndex(indexString);
        return new EditCommand(index, name);
	}
}
