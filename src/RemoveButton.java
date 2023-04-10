import javafx.scene.control.Button;

public class RemoveButton extends Button{
    String style = "-fx-background-color: #E31029; " +
        "-fx-text-fill: #FFFFFF; " +
        "-fx-font-size: 14px; " +
        "-fx-padding: 10px 20px";
    public RemoveButton(String text){
        setText(text);
        setStyle(style);
    }
}
