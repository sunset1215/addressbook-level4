//@@author A0153658W
package seedu.task.logic.commands;

import java.util.ArrayList;

/*
 * Maintains a log for the commands that the user has typed. This is used
 * for the previous/forward command feature, where the user can click the up 
 * key to bring up previous commands, similar to a bash shell
 */
public class UserCommandLog {
    private ArrayList<String> userCommandLog;
    private int currentCommandIndex;

    public UserCommandLog() {
        userCommandLog = new ArrayList<String>();
        currentCommandIndex = 0;
    }

    public void addCommandToUserLog(String userCommand) {
        userCommandLog.add(userCommand);
        currentCommandIndex = userCommandLog.size();
    }

    public String getPreviousCommand() {
        String previousCommand = "";
        if (currentCommandIndex - 1 >= 0) {
            currentCommandIndex -= 1;
            previousCommand = userCommandLog.get(currentCommandIndex);
        } else {
            previousCommand = userCommandLog.get(0);
        }
        return previousCommand;
    }

    public String getNextCommand() {
        String nextCommand = "";

        if (currentCommandIndex + 1 < userCommandLog.size()) {
            currentCommandIndex += 1;
            nextCommand = userCommandLog.get(currentCommandIndex);
        } else {
            nextCommand = "";
            currentCommandIndex = userCommandLog.size();
        }
        return nextCommand;
    }
}