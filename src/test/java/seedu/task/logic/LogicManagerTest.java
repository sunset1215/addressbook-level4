package seedu.task.logic;

import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.events.model.TaskBookChangedEvent;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.events.ui.ShowHelpRequestEvent;
import seedu.task.commons.util.DateUtil;
import seedu.task.logic.Logic;
import seedu.task.logic.LogicManager;
import seedu.task.logic.commands.*;
import seedu.task.model.Model;
import seedu.task.model.ModelManager;
import seedu.task.model.ReadOnlyTaskBook;
import seedu.task.model.TaskBook;
import seedu.task.model.task.*;
import seedu.task.storage.StorageManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskBook latestSavedTaskBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskBookChangedEvent abce) {
        latestSavedTaskBook = new TaskBook(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempTaskBookFile = saveFolder.getRoot().getPath() + "TempTaskBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskBook = new TaskBook(model.getTaskBook()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'task book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskBook, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskBook(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    //@@author A0138704E
    @Test
    public void execute_clearCompleted_haveCompletedTasks() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(helper.generateTaskWithName("task 1"));
        expectedAB.addTask(helper.generateTaskWithName("task 2"));
        expectedAB.addTask(helper.generateTaskWithName("task 3"));
        
        model.addTask(helper.generateTaskWithName("task 1"));
        model.addTask(helper.generateTaskWithName("task 2"));
        model.addTask(helper.generateTaskWithName("task 3"));
        model.addTask(helper.generateCompletedTaskWithName("task 4"));
        model.addTask(helper.generateCompletedTaskWithName("task 5"));
        model.addTask(helper.generateCompletedTaskWithName("task 6"));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_CLEAR_COMPLETED_SUCCESS, expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_clearCompleted_noCompletedTasks() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(helper.generateTaskWithName("task 1"));
        expectedAB.addTask(helper.generateTaskWithName("task 2"));
        expectedAB.addTask(helper.generateTaskWithName("task 3"));
        
        model.addTask(helper.generateTaskWithName("task 1"));
        model.addTask(helper.generateTaskWithName("task 2"));
        model.addTask(helper.generateTaskWithName("task 3"));
        
        assertCommandBehavior("clear", ClearCommand.MESSAGE_CLEAR_COMPLETED_FAIL, expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_clearAll() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTaskWithName("task 1"));
        model.addTask(helper.generateTaskWithName("task 2"));
        model.addTask(helper.generateTaskWithName("task 3"));
        model.addTask(helper.generateCompletedTaskWithName("task 4"));
        model.addTask(helper.generateCompletedTaskWithName("task 5"));
        model.addTask(helper.generateCompletedTaskWithName("task 6"));
        
        assertCommandBehavior("clear /a", ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS, new TaskBook(), Collections.emptyList());
    }
    
    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add invalid command format", expectedMessage);
        assertCommandBehavior(
                "add \"invalid name @#$%^&*\" today", expectedMessage);
    }

    @Test
    public void execute_addFloatingTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.floatTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }
    //@@author 
    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.floatTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());
    }
    //@@author A0138704E
    @Test
    public void execute_addDeadline_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.deadline();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded) + " today",
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_addEvent_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.event();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded) + " today to tomorrow",
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_editFloatingTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeEdited = helper.floatTask();
        Task afterEdit = helper.generateTaskWithName("I have a new name");
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(afterEdit);
        model.addTask(toBeEdited);

        // execute command and verify result
        assertCommandBehavior("edit 1 \"I have a new name\"",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, afterEdit),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_editInvalidIndex() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeEdited = helper.floatTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeEdited);
        model.addTask(toBeEdited);

        // execute command and verify result
        assertCommandBehavior("edit 100 \"I have a new name\"",
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX,
                expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_editNullName() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeEdited = helper.floatTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeEdited);
        model.addTask(toBeEdited);

        // execute command and verify result
        assertCommandBehavior("edit 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_listDefault_showsTasksDueToday() throws Exception {
        // prepare expectations
        LocalDateTime today = DateUtil.getTodayAsLocalDateTime();
        LocalDateTime tomorrow = DateUtil.getTodayAsLocalDateTime().plusDays(1);
        TestDataHelper helper = new TestDataHelper();
        Task test, exam, meeting, lunch;
        test = helper.generateDeadlineWithName("test", today);
        exam = helper.generateDeadlineWithName("exam", today);
        meeting = helper.generateDeadlineWithName("meeting", tomorrow);
        lunch = helper.generateDeadlineWithName("lunch", tomorrow);
        TaskBook expectedAB = helper.generateTaskBook(test, exam, meeting, lunch);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(test, exam);
        
        helper.addToModel(model, test, exam, meeting, lunch);

        // execute command and verify result
        assertCommandBehavior("list",
                ListCommand.MESSAGE_LIST_TODAY_SUCCESS,
                expectedAB,
                expectedList);
    }
    
    @Test
    public void execute_listDate_showsTasksOnSpecifiedDate() throws Exception {
        // prepare expectations
        LocalDateTime today = DateUtil.getTodayAsLocalDateTime();
        LocalDateTime tomorrow = DateUtil.getTodayAsLocalDateTime().plusDays(1);
        TestDataHelper helper = new TestDataHelper();
        Task test, exam, meeting, lunch;
        test = helper.generateDeadlineWithName("test", today);
        exam = helper.generateDeadlineWithName("exam", today);
        meeting = helper.generateDeadlineWithName("meeting", tomorrow);
        lunch = helper.generateDeadlineWithName("lunch", tomorrow);
        TaskBook expectedAB = helper.generateTaskBook(test, exam, meeting, lunch);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(meeting, lunch);
        
        helper.addToModel(model, test, exam, meeting, lunch);

        // execute command and verify result
        assertCommandBehavior("list tomorrow",
                String.format(ListCommand.MESSAGE_LIST_DATE_SUCCESS, 
                        DateUtil.formatLocalDateToString(tomorrow.toLocalDate())),
                expectedAB,
                expectedList);
    }
    
    @Test
    public void execute_listAll_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = helper.generateTaskBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare task book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list /a",
                ListCommand.MESSAGE_LIST_ALL_SUCCESS,
                expectedAB,
                expectedList);
    }
    //@@author
    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new TaskBook());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskBook(), taskList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook(threeTasks);
        expectedAB.removeTask(threeTasks.get(1), "delete");
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_matchesPartialWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskBook expectedAB = helper.generateTaskBook(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, p2, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskBook expectedAB = helper.generateTaskBook(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskBook expectedAB = helper.generateTaskBook(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task floatTask() throws Exception {
            Name name = new Name("floating task");
            return new Task(name);
        }
        
        Task deadline() throws Exception {
            Name name = new Name("deadline");
            TaskDate endDate = new TaskDate(DateUtil.getTodayAsLocalDateTime());
            return new DeadlineTask(name, endDate);
        }
        
        Task event() throws Exception {
            Name name = new Name("deadline");
            TaskDate startDate = new TaskDate(DateUtil.getTodayAsLocalDateTime());
            TaskDate endDate = new TaskDate(DateUtil.getTodayAsLocalDateTime().plusDays(1));
            return new EventTask(name, startDate, endDate);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed)
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add \"");
            cmd.append(p.getName().toString());
            cmd.append("\"");

            return cmd.toString();
        }

        /**
         * Generates a TaskBook with auto-generated tasks.
         */
        TaskBook generateTaskBook(int numGenerated) throws Exception{
            TaskBook taskBook = new TaskBook();
            addToTaskBook(taskBook, numGenerated);
            return taskBook;
        }

        /**
         * Generates a TaskBook based on the list of Tasks given.
         */
        TaskBook generateTaskBook(List<Task> tasks) throws Exception{
            TaskBook taskBook = new TaskBook();
            addToTaskBook(taskBook, tasks);
            return taskBook;
        }
        
        /**
         * Generates a TaskBook based on Tasks given
         */
        TaskBook generateTaskBook(Task... tasks) throws Exception{
            TaskBook taskBook = new TaskBook();
            for (Task t : tasks) {
                taskBook.addTask(t);
            }
            return taskBook;
        }

        /**
         * Adds auto-generated Task objects to the given TaskBook
         * @param taskBook The TaskBook to which the Tasks will be added
         */
        void addToTaskBook(TaskBook taskBook, int numGenerated) throws Exception{
            addToTaskBook(taskBook, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskBook
         */
        void addToTaskBook(TaskBook taskBook, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskBook.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }
        
        /**
         * Adds the given Tasks to the given model
         */
        void addToModel(Model model, Task... tasksToAdd) throws Exception{
            for(Task t: tasksToAdd){
                model.addTask(t);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name)
            );
        }
        
        /**
         * Generates a Task object with given name and set status to complete.
         */
        Task generateCompletedTaskWithName(String name) throws Exception {
            Task task = new Task(new Name(name));
            task.setComplete();
            return task;
        }
        
        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateDeadlineWithName(String name, LocalDateTime date) throws Exception {
            return new DeadlineTask(
                    new Name(name),
                    new TaskDate(date)
            );
        }
        
    }
}
