package seedu.task.model.task;

import java.util.Objects;

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
	
	public DeadlineTask(Name name, TaskDate taskEndDate, Status taskStatus) {
	    super(name, taskStatus);
	    endDate = taskEndDate;
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
	
	@Override
    public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(this.getName().toString());
	    sb.append(" due ");
	    sb.append(endDate.toString());
        return sb.toString();
    }
	
	@Override
	public TaskDate getEnd() {
		return endDate;
	}
	
	@Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineTask // instanceof handles nulls
                && this.hashCode() == other.hashCode());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), "endDate:" + endDate.toString());
    }
}
