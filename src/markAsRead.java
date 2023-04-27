import javafx.scene.control.Button;

public class markAsRead extends Button{
    String style = "-fx-background-color: #000000; " +
        "-fx-text-fill: #FFFFFF; " +
        "-fx-font-size: 14px; " +
        "-fx-padding: 10px 20px";
    public markAsRead(String text){
        setText(text);
        setStyle(style);
    }
}
