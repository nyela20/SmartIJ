package smartij.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import smartij.exceptions.ExceptionSmartIJ;
import smartij.listenerDnd.ListenerDrop;
import smartij.listenerDnd.ListenerSetDrag;
import smartij.model.CategoryIG;
import smartij.model.ElementIG;
import smartij.model.SmartIG;
import java.io.IOException;

public class VueSmartIJ implements PatternObserver {

    @FXML
    private Label filename;
    @FXML
    private Label levelname;
    @FXML
    private Label unitIGS;
    @FXML
    private Button buttonAddCat;
    @FXML
    private Button buttonAddLevel;
    @FXML
    private Button buttonChangeLevel;
    @FXML
    private Pane postitPane;


    private final SmartIG smartIG;
    private final BorderPane pane;

    public VueSmartIJ(SmartIG smartIG, BorderPane pane) {
        this.smartIG = smartIG;
        this.smartIG.ajouter(this);
        this.pane = pane;
        pane.setOnDragOver(new ListenerSetDrag());
        pane.setOnDragDropped(new ListenerDrop(smartIG));
    }


    public void addCategory() {
        try {
            smartIG.addCategory();
        } catch (ExceptionSmartIJ e) {
            System.err.println(e.getMessage());
        }
    }

    public void open() {
        smartIG.open();
    }

    public void changeLevel() {
        try {
            smartIG.changeLevel();
        } catch (ExceptionSmartIJ e) {
            System.err.println(e.getMessage());
        }
    }

    public void addLevel() {
        try {
            smartIG.addLevel();
        } catch (ExceptionSmartIJ e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void reagir() throws IOException, ExceptionSmartIJ {
        postitPane.getChildren().clear();

        filename.setText("You have chosen the file : " + smartIG.getFileName());
        if (smartIG.getActualLevelName() != null)
            levelname.setText("Actual level : " + smartIG.getActualLevelName());
        unitIGS.setText("Unit list : " + smartIG.getUnits());

        //Le bouton ajouter Niveau
        buttonAddLevel.setVisible(false);
        buttonChangeLevel.setVisible(false);
        buttonAddCat.setVisible(false);

        if (smartIG.getState() == 1) {
            buttonAddLevel.setVisible(true);
            if (smartIG.getnumberOfLevel() > 0) {
                buttonChangeLevel.setVisible(true);
                if (smartIG.getActualLevelName() != null && !smartIG.getActualLevelName().isEmpty())
                    buttonAddCat.setVisible(true);
                //dessin des catégorie
                for (CategoryIG categoryIG : smartIG) {
                    VuePostit vuePostit = new VuePostit(categoryIG.getNameCategory(), categoryIG, smartIG);
                    vuePostit.setOpacity(0.9);
                    postitPane.getChildren().add(vuePostit);

                }
            }
        }
    }
}