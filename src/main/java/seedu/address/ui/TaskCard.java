package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.person.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "PersonListCard.fxml";

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

    private ReadOnlyTask task;
    private int displayedIndex;
    private String taskStartDate;
    private String taskEndDate;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        if(task.getStart() != null){
        	card.taskStartDate = task.getStart().toString();
        }
        if(task.getEnd() != null){
        	card.taskEndDate = task.getEnd().toString();
        }
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");

        if(task.getStart() != null){
            startDate.setText("Start date: " + taskStartDate);
        }
        else{
        	startDate.setText("");
        }
        
        if(task.getEnd() != null){
            endDate.setText("End date: " + taskEndDate);
        }
        else{
        	endDate.setText("");
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
