package seedu.task.logic.parser;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.*;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input.
 */
public class ParserManager extends Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public ParserManager() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddParser().parseCommand(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectParser().parseCommand(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteParser().parseCommand(arguments);
            
        case CompleteCommand.COMMAND_WORD:
            return new CompleteParser().parseCommand(arguments);
            
        case EditCommand.COMMAND_WORD:
            return new EditParser().parseCommand(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearParser().parseCommand(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindParser().parseCommand(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListParser().parseCommand(arguments);

        case StoreCommand.COMMAND_WORD:
        	return new StoreParser().parseCommand(arguments);
        	
        case SortCommand.COMMAND_WORD:
            return new SortCommand();
            
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
            
        case UndoCommand.COMMAND_WORD:
        	return new UndoCommand();
        	
        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }
}