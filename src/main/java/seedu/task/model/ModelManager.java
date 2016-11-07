//@@author A0153658W
package seedu.task.model;

import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.Config;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskBookChangedEvent;
import seedu.task.commons.events.storage.StorageFilePathChangedEvent;
import seedu.task.commons.events.ui.DatePickedOnCalendarEvent;
import seedu.task.commons.events.ui.DisplayDirectoryChooserRequestEvent;
import seedu.task.commons.events.ui.ListButtonEvent;
import seedu.task.commons.events.ui.ListCompleteButtonEvent;
import seedu.task.commons.events.ui.ListPendingButtonEvent;
import seedu.task.commons.events.ui.DisplayDirectoryChooserRequestEvent.DirectoryChooserOperationCancelledException;
import seedu.task.commons.events.ui.ListAllButtonEvent;
import seedu.task.commons.events.ui.TaskPanelDataChangedEvent;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.DateUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Status;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.NoCompletedTasksFoundException;
import seedu.task.model.task.UniqueTaskList.TaskAlreadyCompletedException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * Represents the in-memory model of the task book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given TaskBook
     * TaskBook and its variables should not be null
     */
    public ModelManager(TaskBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task book: " + src + " and user prefs " + userPrefs);

        taskBook = new TaskBook(src);
        filteredTasks = new FilteredList<>(taskBook.getTasks());
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        filteredTasks = new FilteredList<>(taskBook.getTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateTaskBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }
    //@@author A0138704E
    /** Raises an event to indicate the task list panel data has changed */
    private void indicateTaskListPanelDataChanged() {
        raise(new TaskPanelDataChangedEvent());
    }
    //@@author A0153658W
    @Override
    public synchronized void deleteTask(ReadOnlyTask target, String callingCommand) throws TaskNotFoundException {
        taskBook.removeTask(target, callingCommand);
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredListByStatus(Status.STATUS_PENDING);
        indicateTaskBookChanged();
    }
	
    @Override
    public synchronized void addTask(int taskIndex, Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(taskIndex, task);
        updateFilteredListByStatus(Status.STATUS_PENDING);
        indicateTaskBookChanged();
    }
    
    @Override
    public synchronized void editTask(int taskIndex, Task taskToEdit, Task resultTask) throws UniqueTaskList.DuplicateTaskException, TaskNotFoundException {
        taskBook.editTask(taskIndex, taskToEdit, resultTask);
        indicateTaskBookChanged();
    }
    
    //@@author A0138704E
	@Override
    public void completeTask(ReadOnlyTask target) throws TaskNotFoundException, TaskAlreadyCompletedException {
        taskBook.completeTask(target);
        indicateTaskBookChanged();
        indicateTaskListPanelDataChanged();
    }
	//@@author A0153723J 
	@Override
	public String changeStorageFilePath(String newFilePath) throws DirectoryChooserOperationCancelledException, IOException {
		if(newFilePath.isEmpty()) {
			newFilePath = getNewFilePathFromDirectoryChooser();
		}
		newFilePath += File.separatorChar + "taskbook.xml";
		raise(new StorageFilePathChangedEvent(newFilePath, taskBook));
		ConfigUtil.saveConfig(new Config(newFilePath), Config.USER_CONFIG_FILE);
		return newFilePath;
	}

	/**
	 * Returns the file path user has selected with the directory chooser
	 * @throws DirectoryChooserOperationCancelledException if user cancels the operation
	 */
    private String getNewFilePathFromDirectoryChooser() throws DirectoryChooserOperationCancelledException {
        DisplayDirectoryChooserRequestEvent event = new DisplayDirectoryChooserRequestEvent();
        raise(event);
        String newFilePath = event.getSelectedFilePath();
        if(newFilePath.isEmpty()) {
        	throw new DirectoryChooserOperationCancelledException();
        }
        return newFilePath;
    }
    //@@author A0138704E
    @Override
    public void sort() {
        taskBook.sort();
        indicateTaskBookChanged();
    }
    
    @Override
    public void clearCompletedTasks() throws NoCompletedTasksFoundException, TaskNotFoundException {
	    taskBook.clearCompletedTasks();
        indicateTaskBookChanged();
    }
    //@@author A0153658W
	@Override
    public void clearAllTasks() throws TaskNotFoundException {
	    taskBook.clearAllTasks();
        indicateTaskBookChanged();
    }
	
    @Override
    public void undo(){
        taskBook.undoTask();
        indicateTaskBookChanged();
        indicateTaskListPanelDataChanged();
    }
    
    public String getUndoInformation(){
        return taskBook.getUndoInformation();
    }

	@Override
	public int getIndex(ReadOnlyTask target) throws TaskNotFoundException {
		return taskBook.getIndex(target);
	}
	
    //=========== Filtered Task List Accessors ===============================================================
	//@@author A0153658W-reused
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }
    //@@author A0138704E
    @Override
    public void updateFilteredListByStatus(boolean status) {
        updateFilteredTaskList(new PredicateExpression(new StatusQualifier(status)));
    }
    
    @Override
    public void updateFilteredListByDate(LocalDate date) {
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(date)));
    }
    //@@author
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    //@@author A0138704E
    /**
     * Class used to filter tasks by status
     */
    private class StatusQualifier implements Qualifier {
        
        private boolean status;
        
        StatusQualifier(boolean status) {
            this.status = status;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getStatus().isComplete() == status;
        }

        @Override
        public String toString() {
            String taskStatus;
            if (status) {
                taskStatus = Status.STATUS_COMPLETE_STRING;
            } else {
                taskStatus = Status.STATUS_PENDING_STRING;
            }
            return "status=" + taskStatus;
        }
    }
    
    /**
     * Class used to filter tasks by end date
     */
    private class DateQualifier implements Qualifier {
        
        private LocalDate date;
        
        DateQualifier(LocalDate date) {
            this.date = date;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.getEnd() == null) {
                return false;
            } else {
                return DateUtil.isEqual(task.getEnd().getTaskDate().toLocalDate(), date);
            }
        }

        @Override
        public String toString() {
            return "endDate=" + DateUtil.formatLocalDateToString(date);
        }
    }
    
    //@@author A0153723J
    //==================== Event Handling Code =================================================================
    
    @Subscribe
    private void handleDatePickedOnCalendarEvent(DatePickedOnCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updateFilteredListByDate(event.date);
    }
    
    @Subscribe
    private void handleListButtonEvent(ListButtonEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updateFilteredListByDate(event.date);
    }
    
    @Subscribe
    private void handleListAllButtonEvent(ListAllButtonEvent event){
    	logger.info(LogsCenter.getEventHandlingLogMessage(event));
    	updateFilteredListToShowAll();
    }
    
    @Subscribe
    private void handleListPendingButtonEvent(ListPendingButtonEvent event){
    	logger.info(LogsCenter.getEventHandlingLogMessage(event));
    	updateFilteredListByStatus(Status.STATUS_PENDING);
    }
    
    @Subscribe
    private void handleListCompleteButtonEvent(ListCompleteButtonEvent event){
    	logger.info(LogsCenter.getEventHandlingLogMessage(event));
    	updateFilteredListByStatus(Status.STATUS_COMPLETE);
    }

}
