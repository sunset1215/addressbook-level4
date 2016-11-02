package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label status;

    private ReadOnlyTask task;
    private int displayedIndex;
    private String taskStartDate;
    private String taskEndDate;
    private String taskStatus;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        if (task.getStart() != null) {
            card.taskStartDate = task.getStart().toString();
        }
        if (task.getEnd() != null) {
            card.taskEndDate = task.getEnd().toString();
        }
        card.taskStatus = task.getStatus().toString();
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        startDate.setText(formatDateDisplayString("Start date:", taskStartDate));
        endDate.setText(formatDateDisplayString("End date:", taskEndDate));
        status.setText("Status: " + taskStatus);
        highlightCompletedTasks();
    }

    /**
     * Apply a colored background to completed tasks
     */
    private void highlightCompletedTasks() {
        if (task.getStatus().isComplete()) {
            cardPane.setStyle("-fx-background-color: LightGreen;");
        } else {
            cardPane.setStyle(null);
        }
    }
    
    /**
     * Formats date strings for display as date values might be null
     */
    private String formatDateDisplayString(String prefix, String strDate) {
        if (strDate == null) {
            return "";
        } else {
            return prefix + " " + strDate;
        }
    }
    
    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
