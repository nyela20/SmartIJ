package smartij.views;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class VueBoxDialogSmartIJ extends TextInputDialog {

    private String answer;

    public VueBoxDialogSmartIJ(String header, String cctxt, String title) {
        super();
        setHeaderText(header);
        setContentText(cctxt);
        setTitle(title);
        Optional<String> result = showAndWait();
        result.ifPresent(name -> answer = result.get());
    }


    public String getAnswer() {
        return answer;
    }
}
