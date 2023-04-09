import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public void start(Stage primaryStage){
        TaskView taskView = new TaskView();
        primaryStage.setTitle("Pragmata");
        primaryStage.setScene(taskView.scene);
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}