package seedu.task.model.task;

import java.util.Comparator;

import seedu.task.commons.util.CollectionUtil;

//@@author A0138704E
/**
 * An enum storing different types of comparators for ReadOnlyTask
 */
public enum TaskComparator implements Comparator<ReadOnlyTask> {

    NAME {
        @Override
        public int compare(ReadOnlyTask t1, ReadOnlyTask t2) {
            return t1.getName().compareTo(t2.getName());
        }

    },

    END_DATE {
        @Override
        public int compare(ReadOnlyTask t1, ReadOnlyTask t2) {
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
    }

}
