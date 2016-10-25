package seedu.task.model.task;

import java.util.Comparator;

import seedu.task.commons.util.CollectionUtil;

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
            }
            return 0;
        }
    },

    START_DATE {
        @Override
        public int compare(Task t1, Task t2) {
            if (!CollectionUtil.isAnyNull(t1.getStart(), t2.getStart())) {
                int startDateResult = t1.getStart().compareTo(t2.getStart());
                return startDateResult;
            }
            return 0;
        }
    },
    
    TYPE {
        @Override
        public int compare(Task t1, Task t2) {
            if (!CollectionUtil.isAnyNull(t1.getStart(), t2.getStart())) {
                int startDateResult = t1.getStart().compareTo(t2.getStart());
                return startDateResult;
            }
            return 0;
        }
    }

}
