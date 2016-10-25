package seedu.task.model;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.DeadlineTask;
import seedu.task.model.task.EventTask;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

public class UndoTaskStack {
	private Stack<String> previousActionType = new Stack<>();
	private Stack<Task> previousTask = new Stack<>();
	private Stack<Integer> previousActionIndex = new Stack<>(); 
	
	private Stack<List<Task>> previousClearedTasks = new Stack<>();
	private Stack<List<Integer>> previousClearedIndices = new Stack<>();
	
	private String previousActionUndoString;

	public UndoTaskStack(){
	}
	
	/**
	 * Pushes the add command to the undo stack.
	 * 
	 * @params callingCommand, addedTask
	 * 
	 * callingCommand will be "add"
	 * addedTask is the task added
	 * actionIndex will be -1; there is no index specified when adding a task,
	 * 			just pushing to keep the stacks balanced
	 **/
	public void pushAddToUndoStack(String callingCommand, Task addedTask){
		previousActionType.push(callingCommand);
		previousActionIndex.push(-1);
		previousTask.push(addedTask);
	}
	
	/**
	 * Pushes the delete command to the undo stack.
	 * 
	 * @params key, callingCommand, targetIndex
	 * 
	 * key is the task that the user deleted or edited
	 * callingCommand will either be "delete" or "edit"
	 * targetIndex holds the index specified by the user that they deleted or edited
	 **/
	public void pushDeleteToUndoStack(ReadOnlyTask key, String callingCommand, int targetIndex) {
		previousActionType.push(callingCommand);
		previousActionIndex.push(targetIndex);

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
	
	/**
	 * Pushes the delete command to the undo stack.
	 * 
	 * @params taskToComplete, callingCommand, targetIndex
	 * 
	 * taskToComplete is the task marked for completion
	 * callingCommand will be "complete"
	 * targetIndex holds the index specified by the user to complete
	 **/
	public void pushCompleteToUndoStack(Task taskToComplete, String callingCommand, int targetIndex) {
		previousActionType.push(callingCommand);
		previousActionIndex.push(targetIndex);
		previousTask.push(taskToComplete);
	}
	
	
	/**
	 * Pushes the clear command to the undo stack.
	 * 
	 * @params clearedTasks, clearedTasksIndices, callingCommand
	 * 
	 * clearedTasks is the set of tasks that were cleared
	 * clearedTasksIndices is the set of indices corresponding to the tasks cleared
	 * callingCommand is the command passed in (it'll always be "clear")
	 * 
	 * actionIndex will be -1; there is no index specified when clearing a set of tasks, 
	 * 			just pushing to keep the stacks balanced
	 **/
	public void pushClearToUndoStack(List<Task> clearedTasks, List<Integer> clearedTaskIndices, String callingCommand) {
		previousActionType.push(callingCommand);
		previousActionIndex.push(-1);
		
		previousClearedTasks.push(clearedTasks);
		previousClearedIndices.push(clearedTaskIndices);
		try {
			previousTask.push(new Task(new Name("filler")));
		} catch (IllegalValueException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Undo the previous action by popping off the stack
	 **/
	public void undo(UniqueTaskList tasks){
		if (!previousTask.isEmpty()) {			
			Task userTask = previousTask.pop();
			String userAction = previousActionType.pop();
			int taskIndex = previousActionIndex.pop();

			
			switch (userAction) {
			// previous action was an add; delete the added task
			case "add":
				try {
					tasks.remove(userTask);
					previousActionUndoString = userAction + " " + userTask.toString();
				} catch (TaskNotFoundException e) {
					e.printStackTrace();
				}
				break;
			// previous action was a delete; add back the deleted task
			case "delete":
				try {
					tasks.add(taskIndex, userTask);
					previousActionUndoString = userAction + " " + (taskIndex + 1);
				} catch (DuplicateTaskException e) {
					e.printStackTrace();
				}
				break;
			// previous action was an edit; delete edited task and add back old task
			case "edit":
				try {
					tasks.remove(userTask);
					tasks.add(taskIndex, userTask);
					previousActionUndoString = userAction + " " + (taskIndex + 1);
				} catch (DuplicateTaskException | TaskNotFoundException e) {
					e.printStackTrace();
				}
				break;
			//previous action was a complete; set the task back to pending
			case "complete":
				previousActionUndoString = userAction + " " + (taskIndex + 1);
				userTask = tasks.getTaskFromIndex(taskIndex);
				userTask.setPending();
				break;
			//previous action was a clear; add back the tasks that were completed
			case "clear":
				previousActionUndoString = userAction;
				List<Task> lastCleared = previousClearedTasks.pop();
				List<Integer> lastClearedIndices = previousClearedIndices.pop();
				
				for(int i = 0; i < lastCleared.size(); i++){
					int indexToUnclear = lastClearedIndices.get(i);
					Task taskToUnclear = lastCleared.get(i);
					taskToUnclear.setComplete();
					try {
						tasks.add(indexToUnclear, taskToUnclear);
					} catch (DuplicateTaskException e) {
						e.printStackTrace();
					}
				}
				break;
			default:
				System.out.println("Error occurred in undo stack");
			}
		} else {
			throw new EmptyStackException();
		}
	}
	
	/*
	 * Returns undo information back to UI
	 * i.e. "Undo successful, reversed action: delete 1"
	 */
	public String getUndoInformation(){
		return previousActionUndoString;
	}
}
