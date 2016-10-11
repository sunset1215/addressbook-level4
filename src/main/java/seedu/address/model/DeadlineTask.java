package seedu.address.model;

import java.util.Date;

import seedu.address.model.person.Name;
import seedu.address.model.person.Task;
import seedu.address.model.person.TaskDate;

/*
 * Represents a task with a deadline
 */
public class DeadlineTask extends Task{
	private TaskDate endDate;
	
	/*
	 * Constructor for a task with a deadline
	 */
	public DeadlineTask(Name name, TaskDate endDate){
		super(name);
		this.endDate = endDate;
	}
	
	public TaskDate getEndDate(){
		return endDate;
	}
	
	/*
	 * Replaces this task's end date with the new end date
	 */
	public void setEndDate(TaskDate newEndDate){
		this.endDate = newEndDate;
	}

}
