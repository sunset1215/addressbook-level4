package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.DateUtil;
import seedu.task.logic.commands.SortCommand;
import seedu.task.model.task.TaskComparator;
import seedu.task.model.task.TaskDate;
import seedu.task.testutil.TaskBuilder;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

//@@author A0138704E
public class SortCommandTest extends TaskBookGuiTest {
    
    @Test
    public void sort_invalidOption() {
        commandBox.runCommand("sort /invalid");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void sort_emptyList() {
        commandBox.runCommand("clear /a");
        commandBox.runCommand("sort");
        assertResultMessage(SortCommand.MESSAGE_SORT_SUCCESS);
    }
    
    @Test
    public void sort_nonEmptyList() throws IllegalValueException {
        //build simple list with some tasks due today
        TestTask test, exam, assignment, dinner, movie, meeting, movie2, meeting2;
        TestTask[] currentList = new TestTask[0];
        TaskDate today = new TaskDate(DateUtil.getTodayAsLocalDateTime());
        TaskDate date1 = new TaskDate(DateUtil.parseStringToLocalDateTime("14 Oct 2016 15:00"));
        TaskDate date2 = new TaskDate(DateUtil.parseStringToLocalDateTime("12 Oct 2016 16:00"));
        TaskDate date3 = new TaskDate(DateUtil.parseStringToLocalDateTime("16 Oct 2016 14:00"));
        TaskDate date4 = new TaskDate(DateUtil.parseStringToLocalDateTime("10 Nov 2016 12:00"));
        TaskDate date5 = new TaskDate(DateUtil.parseStringToLocalDateTime("18 Sep 2016 10:00"));

        movie = new TaskBuilder().withName("movie").build();
        movie2 = new TaskBuilder().withName("another movie").build();
        test = new TaskBuilder().withName("test").withEndDate(today).build();
        exam = new TaskBuilder().withName("exam").withEndDate(date1).build();
        assignment = new TaskBuilder().withName("assignment").withEndDate(date2).build();
        dinner = new TaskBuilder().withName("dinner").withEndDate(date3).build();
        meeting = new TaskBuilder().withName("meeting").withEndDate(date4).build();
        meeting2 = new TaskBuilder().withName("meeting2").withEndDate(date5).build();
        currentList = TestUtil.addTasksToList(new TestTask[0], test, exam, assignment, dinner, movie, meeting, movie2, meeting2);

        //setup expectations
        commandBox.runCommand("clear /a");
        commandBox.runCommand("add \"test\" today");
        commandBox.runCommand("add \"exam\" oct 14 3pm");
        commandBox.runCommand("add \"assignment\" oct 12 4pm");
        commandBox.runCommand("add \"dinner\" oct 16 2pm");
        commandBox.runCommand("add \"movie\"");
        commandBox.runCommand("add \"meeting\" 10 nov 12pm");
        commandBox.runCommand("add \"another movie\"");
        commandBox.runCommand("add \"meeting2\" 18 sep 10am");
        
        currentList = TestUtil.sortTaskList(currentList, TaskComparator.NAME);
        currentList = TestUtil.sortTaskList(currentList, TaskComparator.END_DATE);
        
        commandBox.runCommand("list /a");
        assertSortSuccess(currentList);
    }
    
    /**
     * Runs the sort command and confirms the result is correct.
     * @param currentList A copy of the current list of tasks.
     */
    private void assertSortSuccess(TestTask[] currentList) {
        commandBox.runCommand("sort");
        
        //confirm the task at target list index has the same status as the task to complete and is completed
        assertTrue(taskListPanel.isListMatching(currentList));

        //confirm the result message is correct
        assertResultMessage(SortCommand.MESSAGE_SORT_SUCCESS);
    }

}
