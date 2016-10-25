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

	private Stack<String[]> previousActions = new Stack();
	private Stack<Task> previousTask = new Stack();
	private String previousActionUndoString;

	{
		tasks = new UniqueTaskList();
		tags = new UniqueTagList();
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
		String[] action = new String[] { "add", "-1" };
		previousActions.push(action);
		previousTask.push(task);
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
		pushDeleteToUndoStack(key, callingCommand);

		if (tasks.remove(key)) {
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
	}

	/**
	 * Pushes delete action and deleted task to a stack in the case of an undo
	 * command. Keeps track of whether this delete action was a straight delete
	 * command or a delete action of an edit.
	 * 
	 * @throws UniqueTaskList.TaskNotFoundException
	 *             if the task does not exist.
	 **/
	private void pushDeleteToUndoStack(ReadOnlyTask key, String callingCommand) throws TaskNotFoundException {
		String targetIndex = Integer.toString(tasks.getIndex(key));

		String[] action = new String[] { callingCommand, targetIndex };
		previousActions.push(action);

		Class<? extends ReadOnlyTask> deletedTask = key.getClass();

		if (deletedTask.equals(DeadlineTask.class)) {
			DeadlineTask deleted = new DeadlineTask(key.getName(), key.getEnd());
			previousTask.push(deleted);
		} else if (deletedTask.equals(EventTask.class)) {
			EventTask deleted = new EventTask(key.getName(), key.getStart(), key.getEnd());
			previousTask.push(deleted);
		} else {
			// deleted task must be a floating task
			Task deleted = new Task(key.getName());
			previousTask.push(deleted);
		}
	}

	/*
	 * Separate actions for undo add and undo delete
	 */
	public boolean undoRemoveTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
		if (tasks.remove(key)) {
			return true;
		} else {
			throw new UniqueTaskList.TaskNotFoundException();
		}
	}

	public void undoAddTask(int taskIndex, Task task) throws UniqueTaskList.DuplicateTaskException {
		tasks.add(taskIndex, task);
	}

	// --------------------------------------------------------------------------------
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
	}

	public void undoTask() {
		if (!previousActions.isEmpty()) {
			String[] userActionSet = previousActions.pop();
			Task userTask = previousTask.pop();

			String userAction = userActionSet[0];
			int taskIndex = Integer.parseInt(userActionSet[1]);

			switch (userAction) {
			// previous action was an add; delete the added task
			case "add":
				try {
					undoRemoveTask(userTask);
					previousActionUndoString = userAction + " " + userTask.toString();
				} catch (TaskNotFoundException e) {
					e.printStackTrace();
				}
				break;
			// previous action was a delete; add back the deleted task
			case "delete":
				try {
					undoAddTask(taskIndex, userTask);
					previousActionUndoString = userAction + " " + (taskIndex + 1);
				} catch (DuplicateTaskException e) {
					e.printStackTrace();
				}
				break;
			// previous action was an edit; delete edited task and add back old
			// task
			case "edit":
				try {
					previousActionUndoString = userAction + " " + (taskIndex + 1);
					undoRemoveTask(userTask);
					undoAddTask(taskIndex, userTask);
				} catch (DuplicateTaskException | TaskNotFoundException e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("Error occurred in undo stack");
			}
		} else {
			throw new EmptyStackException();
		}
	}

	public String getUndoInformation() {
		return previousActionUndoString;
	}

	/** Sorts the task book order by end date, then name */
	public void sort() {
	    tasks.sort();
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
