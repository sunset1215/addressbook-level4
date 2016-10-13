package seedu.task.model.task;

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

}
