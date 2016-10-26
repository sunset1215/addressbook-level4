package seedu.task.model.task;

import java.util.Comparator;

import seedu.task.commons.util.CollectionUtil;

//@@author A0138704E
/**
 * An enum storing different types of comparators for task
 */
public enum TaskComparator implements Comparator<Task> {

    NAME {
        @Override
        public int compare(Task t1, Task t2) {
            return t1.getName().compareTo(t2.getName());
        }

    },

    END_DATE {
        @Override
        public int compare(Task t1, Task t2) {
            if (!CollectionUtil.isAnyNull(t1.getEnd(), t2.getEnd())) {
                int endDateResult = t1.getEnd().compareTo(t2.getEnd());
                return endDateResult;
            } else if (t1.getEnd() == null && t2.getEnd() == null) {
                return 0;
            } else if (t1.getEnd() == null) {
                return 1;
            } else {
                return -1;
            }
        }
    },

    START_DATE {
        @Override
        public int compare(Task t1, Task t2) {
            if (!CollectionUtil.isAnyNull(t1.getStart(), t2.getStart())) {
                int startDateResult = t1.getStart().compareTo(t2.getStart());
                return startDateResult;
            } else if (t1.getStart() == null && t2.getStart() == null) {
                return 0;
            } else if (t1.getStart() == null) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    
}
