package seedu.task.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";
    public static final String USER_CONFIG_FILE = "userConfig.json";

    // Config values customizable through config file
    private String appTitle = "Super Tasker";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String taskBookFilePath = "data/taskbook.xml";
    private String taskBookName = "MyTaskBook";

    public Config() {
    }
    
    /**
     * Constructs a Config with a specified file path
     */
    public Config(String newFilePath) {
        taskBookFilePath = newFilePath;
    }
    
    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public String getTaskBookFilePath() {
        return taskBookFilePath;
    }

    public void setTaskBookFilePath(String taskBookFilePath) {
        this.taskBookFilePath = taskBookFilePath;
    }

    public String getTaskBookName() {
        return taskBookName;
    }

    public void setTaskBookName(String taskBookName) {
        this.taskBookName = taskBookName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof Config)){ //this handles null as well.
            return false;
        }

        Config o = (Config)other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(taskBookFilePath, o.taskBookFilePath)
                && Objects.equals(taskBookName, o.taskBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, taskBookFilePath, taskBookName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + taskBookFilePath);
        sb.append("\nTaskBook name : " + taskBookName);
        return sb.toString();
    }

}
