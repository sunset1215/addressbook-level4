package seedu.task.logic.commands;

import java.time.LocalDate;

import seedu.task.commons.util.DateUtil;
import seedu.task.model.task.Status;

//@@author A0138704E
/**
 * Lists all tasks in the task book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String OPTION_LIST_TODAY = "";
    public static final String OPTION_LIST_ALL = "/a";
    public static final String OPTION_LIST_COMPLETE = "/c";
    public static final String OPTION_LIST_PENDING = "/p";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists tasks from the task book.\n"
            + "Parameters: [OPTION]\n"
            + "Example: " + COMMAND_WORD + " or " 
                        + COMMAND_WORD + " " + OPTION_LIST_ALL + " or "
                        + COMMAND_WORD + " " + OPTION_LIST_COMPLETE + " or "
                        + COMMAND_WORD + " " + OPTION_LIST_PENDING;

    public static final String MESSAGE_LIST_TODAY_SUCCESS = "Listed tasks due today";
    public static final String MESSAGE_LIST_ALL_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_LIST_COMPLETE_SUCCESS = "Listed completed tasks";
    public static final String MESSAGE_LIST_PENDING_SUCCESS = "Listed pending tasks";
    public static final String MESSAGE_LIST_DATE_SUCCESS = "Listed tasks on %1$s";

    private String option;

    public ListCommand(String option) {
        this.option = option;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        switch (option) {
        
        case OPTION_LIST_TODAY:
            model.updateFilteredListByDate(DateUtil.getTodayAsLocalDate());
            return new CommandResult(MESSAGE_LIST_TODAY_SUCCESS);
            
        case OPTION_LIST_ALL:
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_LIST_ALL_SUCCESS);
            
        case OPTION_LIST_COMPLETE:
            model.updateFilteredListByStatus(Status.STATUS_COMPLETE);
            return new CommandResult(MESSAGE_LIST_COMPLETE_SUCCESS);
            
        case OPTION_LIST_PENDING:
            model.updateFilteredListByStatus(Status.STATUS_PENDING);
            return new CommandResult(MESSAGE_LIST_PENDING_SUCCESS);
            
        default:
            LocalDate specifiedDate = DateUtil.parseStringToLocalDate(option);
            model.updateFilteredListByDate(specifiedDate);
            return new CommandResult(String.format(MESSAGE_LIST_DATE_SUCCESS, 
                    DateUtil.formatLocalDateToString(specifiedDate)));
        }   
    }
}
