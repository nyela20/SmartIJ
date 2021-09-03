package twix.views;


import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import twix.model.TwixIG;

import java.util.ArrayList;
import java.util.Objects;

public class VueBoite extends ChoiceDialog{

    private final TwixIG twixIG;
    private final String userWord;

    public VueBoite(ArrayList<String> suggestion, TwixIG twixIG, String userWord) {
        super();
        this.twixIG = twixIG;
        this.userWord = userWord;
        aspect();
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
                write(boxChoice.getResult());
            }
        }else {
            if (!choice.isEmpty()) {
                write(choice);
            }
        }
    }

    /**
     * ecriture dans .xlsx
     * @param choice l'écrit
     */
    public void write(String choice){
        twixIG.writeXlsx(choice);
    }

    public void aspect(){
        setHeight(200);
        setWidth(200);
        setHeaderText(userWord);
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
