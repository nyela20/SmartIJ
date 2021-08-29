package twix.views;


import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;
import twix.model.TwixIG;

import java.util.ArrayList;

public class VueBoite extends ChoiceDialog{

    private final TwixIG twixIG;

    public VueBoite(ArrayList<String> suggestion, TwixIG twixIG,String userword) {
        this.twixIG = twixIG;
        aspect(userword);
        add(suggestion);
        showAndWait();
    }

    public void aspect(String userword){
        setHeight(200);
        setWidth(200);
        setHeaderText(userword);
        setTitle("Proposition");
        setContentText("Select");
    }

    public void add(ArrayList<String> suggestion){
        for(String s : suggestion){
            getItems().add(s);
        }
    }
}
