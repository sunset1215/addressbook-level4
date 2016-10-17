package seedu.task.commons.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Super Tasker";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String taskBookFilePath = "data/taskbook.xml";
    private String taskBookName = "MyTaskBook";


    public Config(Config oldConfig,String filePath){
    	appTitle = oldConfig.appTitle;
    	logLevel = oldConfig.logLevel;
    	userPrefsFilePath = oldConfig.userPrefsFilePath;
    	taskBookFilePath = filePath;
    	taskBookName = oldConfig.taskBookName;
    }
   
    public Config() {
    	
    }
    
    
    public void readFromJSON(){
    	JSONParser parser = new JSONParser();
    	try{
    		Object obj = parser.parse(new FileReader(DEFAULT_CONFIG_FILE));
    		JSONObject jsonObject = (JSONObject) obj;
    		appTitle = (String) jsonObject.get("appTitle");
    		logLevel = (Level) jsonObject.get("logLevel");
    		userPrefsFilePath = (String) jsonObject.get("userPrefsFilePath");
    		taskBookFilePath = (String) jsonObject.get("taskBookFilePath");
    		taskBookName = (String) jsonObject.get("taskBookName");
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    }
    
    public void writeJSON(){
    	JSONObject obj = new JSONObject();
    	obj.put("appTitle", appTitle);
    	obj.put("logLevel", logLevel);
    	obj.put("userPrefsFilePath", userPrefsFilePath);
    	obj.put("taskBookFilePath", taskBookFilePath);
    	obj.put("taskBookName", taskBookName);
    	
    	try {

    		FileWriter file = new FileWriter(DEFAULT_CONFIG_FILE);
    		file.write(obj.toJSONString());
    		file.flush();
    		file.close();

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
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
