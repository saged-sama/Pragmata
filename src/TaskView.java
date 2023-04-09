import java.time.LocalDate;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaskView {
    public TaskList tasklist = new TaskList();
    Button addTaskButton = new Button("Add Task");
    Button removeTaskButton = new Button("Remove Task");
    ListView<String> taskListView = new ListView<>();
    TextField taskDescField = new TextField();
    DatePicker taskDatePicker = new DatePicker();
    VBox root = null;
    public Scene scene = null;
    public TaskView(){
        taskDescField.setPrefWidth(400);
        taskDatePicker.setPrefWidth(300);
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
        root = new VBox();
        scene = new Scene(root, 400, 500);
        root.getChildren().addAll(
            new HBox( new VBox(taskDescField, new HBox(taskDatePicker, addTaskButton))),
            taskListView,
            removeTaskButton
        );
    }
}
