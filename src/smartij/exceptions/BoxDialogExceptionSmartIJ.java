package smartij.exceptions;

import javafx.scene.control.Alert;


public class BoxDialogExceptionSmartIJ {
    public BoxDialogExceptionSmartIJ(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setResizable(true);
        alert.setContentText(message);
        alert.showAndWait();
        //try
    }
}
