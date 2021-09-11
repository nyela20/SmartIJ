package smartij.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import smartij.model.ElementIG;
import smartij.model.SmartIG;

import java.io.IOException;
import java.util.ArrayList;

public class VueSmartIJ implements PatternObserver {

    @FXML
    private Label filename;
    @FXML
    private Label levelname;
    @FXML
    private Button buttonSearch;
    @FXML
    private VBox ElementIGS;
    @FXML
    private Label unitIGS;

    private final SmartIG smartIG;
    private final BorderPane pane;

    public VueSmartIJ(SmartIG smartIG, BorderPane pane) {
        this.smartIG = smartIG;
        this.smartIG.ajouter(this);
        this.pane = pane;
    }

    public void search() {
        try {
            smartIG.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printElement(){
        ArrayList<Node> n = new ArrayList<>();
        n.addAll(ElementIGS.getChildren());
        ElementIGS.getChildren().removeAll(n);
        for(ElementIG e : smartIG){
            ElementIGS.getChildren().add(new Label(e.getNameElement() + " -  " + e.getvalueElement() + " - " + e.getUnitElement()));
        }
    }

    public void printUnit(){
        unitIGS.setText("Tableau unités : " + smartIG.getAllUnit());
    }

    @Override
    public void reagir() throws IOException {
        //rafraîchir

        filename.setText("Vous avez choisi le fichier : " + smartIG.getFileName());
        if (smartIG.getActualLevelName() != null)
            levelname.setText("Level actuel : " + smartIG.getActualLevelName());
        if (smartIG.getState() == 1)
            buttonSearch.setVisible(true);
        printElement();
        printUnit();

        /*Menu*/
        FXMLLoader loaderMenu = new FXMLLoader();
        loaderMenu.setLocation(getClass().getResource("../fxml/menu.fxml"));
        loaderMenu.setControllerFactory(iC -> new VueMenuSmartIJ(smartIG));
        pane.setTop(loaderMenu.load());
    }


}
