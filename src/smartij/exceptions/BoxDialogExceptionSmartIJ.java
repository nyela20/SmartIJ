package smartij.exceptions;

import javafx.scene.control.Alert;


public class BoxDialogExceptionSmartIJ {

    public BoxDialogExceptionSmartIJ(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeight(100);
        alert.setWidth(100);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
