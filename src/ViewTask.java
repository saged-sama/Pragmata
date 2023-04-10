import java.time.LocalDate;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewTask {
    public TaskList tasklist = new TaskList();
    AddButton addTaskButton = new AddButton("Add Task");
    RemoveButton removeTaskButton = new RemoveButton("Remove Task");
    EditButton editTaskButton = new EditButton("Edit Task");
    ListView<String> taskListView = new ListView<>();
    TextField taskDescField = new TextField();
    DatePicker taskDatePicker = new DatePicker();
    VBox root = null;
    public Scene scene = null;
    public ViewTask(){
        taskDescField.setStyle("-fx-background-color: white;" + 
            "-fx-border-color: black;" +
            "-fx-border-width: 1px;" +
            "-fx-text-fill: black;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10px 20px;"
        );
        taskDescField.setPrefWidth(395);
        taskDescField.setPromptText("Make a quick note...");
        taskDatePicker.setStyle("-fx-background-color: yellow;" +
            "-fx-text-fill: red;" + 
            "-fx-font-size: 14px;" +
            "-fx-padding: 5px 20px;"
        );
        taskDatePicker.setPrefWidth(305);
        taskDatePicker.setPromptText("Due: DD/MM/YYYY");
        addTaskButton.setOnAction(e->{
            String description = taskDescField.getText();
            LocalDate dueDate = taskDatePicker.getValue();
            if(description != null && !description.isEmpty() && dueDate != null){
                Task task = new Task(description, dueDate);
                tasklist.addTask(task);
                taskListView.getItems().add(task.toString());
                taskDescField.clear();
                taskDatePicker.setValue(null);
            }
        });
        removeTaskButton.setOnAction(e->{
            int selectIndx = taskListView.getSelectionModel().getSelectedIndex();
            if(selectIndx >= 0){
                tasklist.removeTask(selectIndx);
                taskListView.getItems().remove(selectIndx);
            }
        });
        editTaskButton.setOnAction(e->{
            String description = taskDescField.getText();
            LocalDate dueDate = taskDatePicker.getValue();
            int selectIndx = taskListView.getSelectionModel().getSelectedIndex();
            if(selectIndx >= 0){
                Task task = new Task(description, dueDate);
                tasklist.editTask(selectIndx, task);
                taskListView.getItems().set(selectIndx, task.toString());
            }
        });
        root = new VBox();
        root.getChildren().addAll(
            new HBox( new VBox(taskDescField, new HBox(taskDatePicker, addTaskButton))),
            taskListView,
            new HBox(removeTaskButton, editTaskButton)
        );
        scene = new Scene(root, 400, 500);
    }
}
