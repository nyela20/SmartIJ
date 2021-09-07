package twix.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import twix.model.TwixIG;

import java.io.IOException;

public class VueTwix implements Observateur{

    @FXML
    private Label filename;
    @FXML
    private Label levelname;
    @FXML
    private Button buttonSearch;

    private final TwixIG twixIG;
    private final BorderPane pane;

    public VueTwix(TwixIG twixIG, BorderPane pane){
        this.twixIG = twixIG;
        this.twixIG.ajouter(this);
        this.pane = pane;
    }

    public void search(){
        try {
            twixIG.search();
        } catch (Exception e) {
            e.printStackTrace();
            //ExceptionTwix exceptionTwix = new ExceptionTwix(e.getMessage());
            //System.out.println(exceptionTwix.getMessage());
        }
    }


    @Override
    public void reagir() throws IOException {
        filename.setText("Vous avez choisi le fichier : " + twixIG.getFileName());
        if(twixIG.getActualLevelName() != null)
           levelname.setText("Level actuel : " + twixIG.getActualLevelName());
        if(twixIG.getState() == 1){
            buttonSearch.setVisible(true);
        }

        /*Menu*/
        FXMLLoader loaderMenu = new FXMLLoader();
        loaderMenu.setLocation(getClass().getResource("../fxml/menu.fxml"));
        loaderMenu.setControllerFactory(iC -> new VueMenu(twixIG));
        pane.setTop(loaderMenu.load());

    }


}
