package smartij.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import smartij.model.SmartIG;

import java.io.IOException;

public class VueSmartIJ implements PatternObserver {

    @FXML
    private Label filename;
    @FXML
    private Label levelname;
    @FXML
    private Button buttonSearch;

    private final SmartIG smartIG;
    private final BorderPane pane;

    public VueSmartIJ(SmartIG smartIG, BorderPane pane){
        this.smartIG = smartIG;
        this.smartIG.ajouter(this);
        this.pane = pane;
    }

    public void search(){
        try {
            smartIG.search();
        } catch (Exception e) {
            e.printStackTrace();
            //ExceptionSmartIJ exceptionTwix = new ExceptionSmartIJ(e.getMessage());
            //System.out.println(exceptionTwix.getMessage());
        }
    }


    @Override
    public void reagir() throws IOException {
        filename.setText("Vous avez choisi le fichier : " + smartIG.getFileName());
        if(smartIG.getActualLevelName() != null)
           levelname.setText("Level actuel : " + smartIG.getActualLevelName());
        if(smartIG.getState() == 1){
            buttonSearch.setVisible(true);
        }

        /*Menu*/
        FXMLLoader loaderMenu = new FXMLLoader();
        loaderMenu.setLocation(getClass().getResource("../fxml/menu.fxml"));
        loaderMenu.setControllerFactory(iC -> new VueMenuSmartIJ(smartIG));
        pane.setTop(loaderMenu.load());

    }


}
