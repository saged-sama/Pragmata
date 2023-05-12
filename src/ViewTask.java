import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.MultipleSelectionModel;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;

public class ViewTask {
    TaskList tasklist = new TaskList();
    AddButton addTaskButton = new AddButton("Add Task");
    RemoveButton removeTaskButton = new RemoveButton("Remove Task");
    EditButton editTaskButton = new EditButton("Edit Task");
    markAsRead markAsReadButton = new markAsRead("Mark as Read");
    ListView<String> taskListView = new ListView<>();
    TextField taskDescField = new TextField();
    DatePicker taskDatePicker = new DatePicker();
    VBox root = null;
    public Scene scene = null;

    public ViewTask() {
        taskDescField.setStyle("-fx-background-color: white;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1px;" +
                "-fx-text-fill: black;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10px 20px;");
        taskDescField.setPrefWidth(395);
        taskDescField.setPromptText("Make a quick task note...");
        taskDatePicker.setStyle("-fx-background-color: yellow;" +
                "-fx-text-fill: red;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 5px 20px;");
        taskDatePicker.setPrefWidth(305);
        taskDatePicker.setPromptText("Due: MM/DD/YYYY");
        taskListView.setStyle("-fx-background-color: transparent;" +
        "-fx-padding: 10px 20px;");
        taskListView.setPrefSize(50, 300);
        addTaskButton.setOnAction(e -> {
            String description = taskDescField.getText();
            LocalDate dueDate = taskDatePicker.getValue();
            if (description != null && !description.isEmpty() && dueDate != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt", true))) {
                    writer.write(description + " " + dueDate.toString() + "\n");
                    writer.flush();
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                Task task = new Task(description, dueDate);
                tasklist.addTask(task);
                taskListView.getItems().add(task.toString());
                taskDescField.clear();
                taskDatePicker.setValue(null);
            }
        });
        removeTaskButton.setOnAction(e -> {
            int selectIndx = taskListView.getSelectionModel().getSelectedIndex();
            if (selectIndx >= 0) {
                tasklist.removeTask(selectIndx);
                taskListView.getItems().remove(selectIndx);
                
                try (BufferedWriter wri = new BufferedWriter(new FileWriter("tasks.txt"))) {
                    wri.write("");
                    wri.flush();
                    wri.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt", true));
                    for(Task task: tasklist.tasks){
                        String description = task.getDescription();
                        LocalDate dueDate = task.getdueDate();
                        writer.write(description + " " + dueDate.toString() + "\n");
                    }
                    writer.flush();
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        editTaskButton.setOnAction(e -> {
            String description = taskDescField.getText();
            LocalDate dueDate = taskDatePicker.getValue();
            int selectIndx = taskListView.getSelectionModel().getSelectedIndex();
            if (selectIndx >= 0) {
                Task task = new Task(description, dueDate);
                tasklist.editTask(selectIndx, task);
                taskListView.getItems().set(selectIndx, task.toString());
            }
            try (BufferedWriter wri = new BufferedWriter(new FileWriter("tasks.txt"))) {
                wri.write("");
                wri.flush();
                wri.close();
                BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt", true));
                for(Task task: tasklist.tasks){
                    String ddescription = task.getDescription();
                    LocalDate ddueDate = task.getdueDate();
                    writer.write(ddescription + " " + ddueDate.toString() + "\n");
                }
                writer.flush();
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        markAsReadButton.setOnAction(event -> {
            int selectIndx = taskListView.getSelectionModel().getSelectedIndex();
            if (selectIndx >= 0) {

                MultipleSelectionModel<String> selectionModel = taskListView.getSelectionModel();

                ObservableList<String> selectedItems = selectionModel.getSelectedItems();


                if (!selectedItems.isEmpty()) {
                    String selectedItem = selectedItems.get(0);
                    String modifiedItem = selectedItem + "\nCompleted✓✓✓✓✓";
                    int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
                    taskListView.getItems().set(selectedIndex, modifiedItem);
                }

            }
        });
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        Image image = new Image("bg.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        root.setBackground(background);

        HBox hbox1 = new HBox(new VBox(taskDescField, new HBox(taskDatePicker, addTaskButton)));
        hbox1.setAlignment(Pos.CENTER);
        HBox hbox2 = new HBox(removeTaskButton, editTaskButton, markAsReadButton);
        hbox2.setAlignment(Pos.CENTER);
        root.getChildren().addAll(hbox1,taskListView, hbox2);
        scene = new Scene(root, 400, 500);
    }
}
