package twix.views;


import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import twix.model.TwixIG;

import java.util.ArrayList;
import java.util.Objects;

public class VueBoite extends ChoiceDialog{

    private final TwixIG twixIG;

    public VueBoite(ArrayList<String> suggestion, TwixIG twixIG,String userword) {
        super();
        this.twixIG = twixIG;
        aspect(userword);
        add(suggestion);
        showAndWait();
        if(getResult() == null) setResult("");
        traiter(getResult().toString());
    }

    public void traiter(String choice) {
        if (Objects.equals(choice, "Saisir...")) {
            TextInputDialog boxChoice = new TextInputDialog();
            boxChoice.showAndWait();
            if(boxChoice.getEditor().getText().isEmpty()) {
                traiter("Saisir...");
            }
            else{
                System.out.println("Je suis égale à " + boxChoice.getEditor().getText());
            }
        }else {
            if (!choice.isEmpty()) {
                System.out.println("Je suis égale à " + choice);
            }
        }
    }

    public void ecrire(String choice){

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
        //ajouter choix manuel
        getItems().add("Saisir...");
    }

}
