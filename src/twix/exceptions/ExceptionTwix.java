package twix.exceptions;

import javafx.scene.control.Alert;

public class ExceptionTwix extends Exception {


    public ExceptionTwix(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeight(100);
        alert.setWidth(100);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
