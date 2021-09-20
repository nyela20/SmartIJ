package smartij.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import smartij.listenerDnd.ListenerDrop;
import smartij.listenerDnd.ListenerSetDrag;
import smartij.model.CategoryIG;
import smartij.model.ElementIG;
import smartij.model.SmartIG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class VueSmartIJ implements PatternObserver {

    @FXML
    private Label filename;
    @FXML
    private Label levelname;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonAddLevel;
    @FXML
    private Button buttonChangeLevel;
    @FXML
    private VBox ElementIGS;
    @FXML
    private Label unitIGS;
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

    public void search() {
        try {
            smartIG.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCategory(){
        smartIG.addCategory();
    }



    public void printElement(){
        /*
        ArrayList<Node> n = new ArrayList<>();
        n.addAll(ElementIGS.getChildren());
        ElementIGS.getChildren().removeAll(n);
        for(ElementIG e : smartIG){
            ElementIGS.getChildren().add(new Label(e.obj().toString()));
        }
         */
    }

    public void open() {
        smartIG.open();
    }

    public void changeLevel() {
        smartIG.changeLevel();
    }

    public void addLevel() {
        smartIG.addLevel();
    }



    @Override
    public void reagir() throws IOException {
        postitPane.getChildren().clear();

        //rafraîchir
        filename.setText("Vous avez choisi le fichier : " + smartIG.getFileName());
        if (smartIG.getActualLevelName() != null)
            levelname.setText("Level actuel : " + smartIG.getActualLevelName());
        if (smartIG.getState() == 1) {
            buttonAddLevel.setVisible(true);
            if(smartIG.getnumberOfLevel() > 0){
                buttonChangeLevel.setVisible(true);
                buttonSearch.setVisible(true);
            }
        }

        for(CategoryIG categoryIG : smartIG){
            //à modifier
            postitPane.getChildren().add(new VuePostit(categoryIG.getNameCategory(), categoryIG,smartIG));
        }

        /*
        printElement();
        printUnit();
         */

        /*Menu*/
        FXMLLoader loaderMenu = new FXMLLoader();
        loaderMenu.setLocation(getClass().getResource("../fxml/menu.fxml"));
        loaderMenu.setControllerFactory(iC -> new VueMenuSmartIJ(smartIG));
        pane.setTop(loaderMenu.load());
    }


}
