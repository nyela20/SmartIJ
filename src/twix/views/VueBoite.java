package twix.views;


import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import twix.model.ElementXlsx;
import twix.model.TwixIG;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        traiter(getResult().toString(),suggestion);
    }

    public void traiter(String choice,ArrayList<String> suggestion) {
        ElementXlsx elementXlsx = null;
        ArrayList<String> suggest = new ArrayList<>();
        suggest.addAll(List.of(choice.split(" ")));
        //Création de l'Element
        //Dernière ligne non écrite de la colonne
        int rowidLastElement = twixIG.getLevel(twixIG.getActualLevelName()).getRowid();
        //Création des objects
        Object obj[] = new Object[3];
        obj[0] = userWord;
        obj[1] = suggest.get(0);
        obj[2] = suggest.get(1);
        if (Objects.equals(choice, "Saisir...")) {
            TextInputDialog boxChoice = new TextInputDialog();
            boxChoice.showAndWait();
            if(boxChoice.getEditor().getText().isEmpty()) {
                traiter("Saisir...",suggestion);
            }
            else{
                 elementXlsx = new ElementXlsx(rowidLastElement,obj,userWord,twixIG.getLevel(twixIG.getActualLevelName()));
                 write(elementXlsx);
            }
        }else {
            if (!choice.isEmpty()) {
                elementXlsx = new ElementXlsx(rowidLastElement,obj,userWord,twixIG.getLevel(twixIG.getActualLevelName()));
                write(elementXlsx);
            }
        }
    }

    /**
     * On écrti l'élément dans le fichier xlsx
     * @param elementXlsx l'élèment
     */
    public void write(ElementXlsx elementXlsx){
        twixIG.write(elementXlsx);
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
