package twix.views;


import javafx.scene.control.ChoiceDialog;
import twix.model.TwixIG;

import java.util.ArrayList;

public class VueBoite {
    private final ArrayList<String> ArrayProposition = new ArrayList<>();
    private final TwixIG twixIG = new TwixIG();

    public VueBoite() {
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>();
        choiceDialog.setHeaderText("Select one");
        choiceDialog.setTitle("Proposition");
        choiceDialog.setContentText("Select");
        choiceDialog.showAndWait();
    }
}