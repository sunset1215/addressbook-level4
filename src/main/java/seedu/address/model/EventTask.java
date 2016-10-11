package seedu.address.model;

import java.util.Date;

import seedu.address.model.person.Name;
import seedu.address.model.person.Task;
import seedu.address.model.person.TaskDate;

/*
 * Represents a task as an event, i.e. it will have a start and end time
 */
public class EventTask extends Task{
	private TaskDate startDate, endDate;
	
	public EventTask(Name name, TaskDate startDate, TaskDate endDate) {
		super(name);
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public TaskDate getStartDate(){
		return startDate;
	}
	
	/*
	 * Replaces this task's end date with the new end date
	 */
	public void setStartDate(TaskDate startDate){
		this.startDate = startDate;
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
