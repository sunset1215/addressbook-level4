package seedu.task.ui;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.model.TaskBookChangedEvent;
import seedu.task.commons.events.storage.StorageFilePathChangedEvent;
import seedu.task.commons.util.FileUtil;
import seedu.task.commons.util.FxViewUtil;

import java.util.Date;
import java.util.logging.Logger;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private GridPane mainPane;

    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label syncStatus;
    
    @FXML
    private Label numberOfTasksStatus;

    private AnchorPane placeHolder;

    private static final String FXML = "StatusBarFooter.fxml";

    public static StatusBarFooter load(Stage stage, AnchorPane placeHolder, String saveLocation, int numberOfTasks) {
        StatusBarFooter statusBarFooter = UiPartLoader.loadUiPart(stage, placeHolder, new StatusBarFooter());
        statusBarFooter.configure(saveLocation, numberOfTasks);
        return statusBarFooter;
    }

    //@@author A0138704E
    public void configure(String saveLocation, int numberOfTasks) {
        addMainPane();
        setSyncStatus(" Not updated yet in this session");
        setSaveLocation(saveLocation);
        setNumberOfTasksStatus(numberOfTasks);
        registerAsAnEventHandler(this);
    }
    //@@author
    private void addMainPane() {
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }
    
    //@@author A0138704E
    private void setSaveLocation(String location) {
        this.saveLocationStatus.setText(FileUtil.getFormattedPath(location));
    }
    
    private void setNumberOfTasksStatus(int numberOfTasks) {
        this.numberOfTasksStatus.setText("Total no. of tasks = " + numberOfTasks + " ");
    }
    //@@author
    private void setSyncStatus(String status) {
        this.syncStatus.setText(status);
    }

    @Override
    public void setNode(Node node) {
        mainPane = (GridPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Subscribe
    public void handleTaskBookChangedEvent(TaskBookChangedEvent abce) {
        String lastUpdated = (new Date()).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(" Last Updated: " + lastUpdated);
        setNumberOfTasksStatus(abce.data.getTaskList().size());
    }
    
    //@@author A0138704E
    /**
     * Set new save location for display
     */
    @Subscribe
    public void handleStorageFilePathChangedEvent(StorageFilePathChangedEvent event) {
        String newSaveLocation = event.getNewFilePath();
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Setting save location to " + newSaveLocation));
        setSaveLocation(newSaveLocation);
    }
    
}
