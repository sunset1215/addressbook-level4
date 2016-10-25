package seedu.task.model;

import javafx.collections.ObservableList;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;
import seedu.task.model.task.DeadlineTask;
import seedu.task.model.task.EventTask;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.task.model.task.UniqueTaskList.TaskAlreadyCompletedException;
import seedu.task.model.task.UniqueTaskList.NoCompletedTasksFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-book level Duplicates are not allowed (by .equals
 * comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

	private UniqueTaskList tasks;
	private UniqueTagList tags;
	
	private UndoTaskStack undoTaskStack;
    private static final String UNDO_ADD_COMMAND = "add";
    private static final String UNDO_DELETE_COMMAND = "delete";
    private static final String UNDO_EDIT_COMMAND = "edit";
    private static final String UNDO_COMPLETE_COMMAND = "complete";
    private static final String UNDO_CLEAR_COMMAND = "clear";

	
	{
		tasks = new UniqueTaskList();
		tags = new UniqueTagList();
		undoTaskStack = new UndoTaskStack();
	}

	public TaskBook() {
	}

	/**
	 * Tasks and Tags are copied into this task book
	 */
	public TaskBook(ReadOnlyTaskBook toBeCopied) {
		this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
	}

	/**
	 * Tasks and Tags are copied into this task book
	 */
	public TaskBook(UniqueTaskList tasks, UniqueTagList tags) {
		this.tasks = copyUniqueTaskList(tasks);
		this.tags = copyUniqueTagList(tags);
		// the line of code below is the original code
		// I used the above method to copy the lists
		// because the original code changes all the tasks that is read from
		// the storage into tasks, instead of keeping them as event task or
		// deadline task or task.
		// resetData(tasks.getInternalList(), tags.getInternalList());
	}

	public static ReadOnlyTaskBook getEmptyTaskBook() {
		return new TaskBook();
	}

	//// list overwrite operations

	/*
	 * Returns a copy of the given unique tag list
	 */
	private UniqueTagList copyUniqueTagList(UniqueTagList tags) {
		UniqueTagList newList = new UniqueTagList();
		for (int i = 0; i < tags.size(); i++) {
			try {
				newList.add(tags.getTagFromIndex(i));
			} catch (DuplicateTagException e) {
				// this should not happen since we're just copying items over to
				// a new list
			}
		}
		return newList;
	}

	/*
	 * Returns a copy of the given unique task list
	 */
	private UniqueTaskList copyUniqueTaskList(UniqueTaskList tasks) {
		UniqueTaskList newList = new UniqueTaskList();
		for (int i = 0; i < tasks.size(); i++) {
			try {
				newList.add(tasks.getTaskFromIndex(i));
			} catch (DuplicateTaskException e) {
				// this should not happen since we're just copying items over to
				// a new list
			}
		}
		return newList;
	}

	public ObservableList<Task> getTasks() {
		return tasks.getInternalList();
	}

	public void setTasks(List<Task> tasks) {
		this.tasks.getInternalList().setAll(tasks);
	}

	public void setTags(Collection<Tag> tags) {
		this.tags.getInternalList().setAll(tags);
	}

	public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
		setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
		setTags(newTags);
	}

	public void resetData(ReadOnlyTaskBook newData) {
		resetData(newData.getTaskList(), newData.getTagList());
	}

	//// task-level operations

	/**
	 * Adds a task to the task book. Also checks the new task's tags and updates
	 * {@link #tags} with any new tags found, and updates the Tag objects in the
	 * task to point to those in {@link #tags}.
	 *
	 * @throws UniqueTaskList.DuplicateTaskException
	 *             if an equivalent task already exists.
	 */
	public void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
		tasks.add(task);
		undoTaskStack.pushAddToUndoStack(UNDO_ADD_COMMAND, task);
	}

	/**
	 * Adds a task to the task list at a given index. Also checks the new task's
	 * tags and updates {@link #tags} with any new tags found, and updates the
	 * Tag objects in the task to point to those in {@link #tags}.
	 *
	 * @throws UniqueTaskList.DuplicateTaskException
	 *             if an equivalent task already exists.
	 */
	public void addTask(int taskIndex, Task task) throws UniqueTaskList.DuplicateTaskException {
		tasks.add(taskIndex, task);
	}

	/**
	 * Ensures that every tag in this task: - exists in the master list
	 * {@link #tags} - points to a Tag object in the master list
	 */
	private void syncTagsWithMasterList(Task task) {
		final UniqueTagList taskTags = task.getTags();
		tags.mergeFrom(taskTags);

		// Create map with values = tag object references in the master list
		final Map<Tag, Tag> masterTagObjects = new HashMap<>();
		for (Tag tag : tags) {
			masterTagObjects.put(tag, tag);
		}

		// Rebuild the list of task tags using references from the master list
		final Set<Tag> commonTagReferences = new HashSet<>();
		for (Tag tag : taskTags) {
			commonTagReferences.add(masterTagObjects.get(tag));
		}
		task.setTags(new UniqueTagList(commonTagReferences));
	}

	public boolean removeTask(ReadOnlyTask key, String callingCommand) throws UniqueTaskList.TaskNotFoundException {
		int targetIndex = tasks.getIndex(key);

		if (tasks.remove(key)) {
			undoTaskStack.pushDeleteToUndoStack(key, callingCommand, targetIndex);
			return true;
		} else {
			throw new TaskNotFoundException();
		}
	}

	/**
	 * Completes a task in the task book.
	 * 
	 * @param target
	 *            task to be completed
	 * @throws TaskNotFoundException
	 *             if target task cannot be found
	 * @throws TaskAlreadyCompletedException
	 *             if target task is already marked as complete
	 */
	public void completeTask(ReadOnlyTask target) throws TaskNotFoundException, TaskAlreadyCompletedException {
		int targetIndex = tasks.getIndex(target);
		Task taskToComplete = tasks.getTaskFromIndex(targetIndex);
		if (taskToComplete.isComplete()) {
			throw new TaskAlreadyCompletedException();
		}
		taskToComplete.setComplete();
		
		undoTaskStack.pushCompleteToUndoStack(taskToComplete, UNDO_COMPLETE_COMMAND, targetIndex);
	}

	public int getIndex(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
		return tasks.getIndex(key);
	}

	/**
	 * Clears completed tasks from the task book
	 * 
	 * @throws NoCompletedTasksFoundException
	 *             if no completed tasks were found
	 */
	public void clearCompletedTasks() throws NoCompletedTasksFoundException {
		UniqueTaskList copyTasks = copyUniqueTaskList(tasks);
		List<Task> clearedTasks = new ArrayList<Task>();
		List<Integer> clearedTasksIndices = new ArrayList<Integer>();
		
		//compile set of tasks and indices being cleared to prepare for undo stack
		for (Task readTask : copyTasks) {
			if (readTask.isComplete()) {
				try {
					clearedTasksIndices.add(tasks.getIndex(readTask));
					
					Class<? extends ReadOnlyTask> clearedTask = readTask.getClass();

					if (clearedTask.equals(DeadlineTask.class)) {
						DeadlineTask cleared = new DeadlineTask(readTask.getName(), readTask.getEnd());
						clearedTasks.add(cleared);
					} else if (clearedTask.equals(EventTask.class)) {
						EventTask cleared = new EventTask(readTask.getName(), readTask.getStart(), readTask.getEnd());
						clearedTasks.add(cleared);
					} else {
						// cleared task must be a floating task
						Task cleared = new Task(readTask.getName());
						clearedTasks.add(cleared);
					}
				} catch (TaskNotFoundException e) {
					assert false : "The target task cannot be missing";
				}
			}
		}
		
		//actually remove the completed tasks
		for (Task readTask : copyTasks) {
			if (readTask.isComplete()) {
				try {			
					tasks.remove(readTask);
				} catch (TaskNotFoundException e) {
					assert false : "The target task cannot be missing";
				}
			}
		}
		if (copyTasks.size() == tasks.size()) {
			throw new NoCompletedTasksFoundException();
		}
		
		undoTaskStack.pushClearToUndoStack(clearedTasks, clearedTasksIndices, UNDO_CLEAR_COMMAND);
	}

	public void undoTask() {
		undoTaskStack.undo(tasks);
	}

	public String getUndoInformation() {
		return undoTaskStack.getUndoInformation();
	}

	//// tag-level operations

	public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
		tags.add(t);
	}

	//// util methods

	@Override
	public String toString() {
		return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() + " tags";
		// TODO: refine later
	}

	@Override
	public List<ReadOnlyTask> getTaskList() {
		return Collections.unmodifiableList(tasks.getInternalList());
	}

	@Override
	public List<Tag> getTagList() {
		return Collections.unmodifiableList(tags.getInternalList());
	}

	@Override
	public UniqueTaskList getUniqueTaskList() {
		return this.tasks;
	}

	@Override
	public UniqueTagList getUniqueTagList() {
		return this.tags;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof TaskBook // instanceof handles nulls
						&& this.tasks.equals(((TaskBook) other).tasks) && this.tags.equals(((TaskBook) other).tags));
	}

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing
		// your own
		return Objects.hash(tasks, tags);
	}

}
