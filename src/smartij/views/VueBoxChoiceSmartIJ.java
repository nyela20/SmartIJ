package smartij.views;


import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import smartij.model.ElementIG;
import smartij.model.SmartIG;

import java.util.*;

public class VueBoxChoiceSmartIJ extends ChoiceDialog{

    private final SmartIG smartIG;
    private final String userWord;

    public VueBoxChoiceSmartIJ(ArrayList<String> suggestion, SmartIG smartIG, String userWord) {
        super();
        this.smartIG = smartIG;
        this.userWord = userWord;
        aspect();
        add(suggestion);
        showAndWait();
        if(getResult() == null) setResult("");
        traiter(getResult().toString());
    }

    public void traiter(String choice) {
        ArrayList<String> suggest = new ArrayList<>(List.of(choice.split(" ")));
        if (Objects.equals(choice, "Saisir...")) {
            TextInputDialog boxChoice = new TextInputDialog();
            boxChoice.showAndWait();
            Optional<String> result = boxChoice.showAndWait();
            result.ifPresent(name -> {
                write(createKnownElement(suggest));
            });
        } else {
            write(createKnownElement(suggest));
        }
    }

    private ElementIG createKnownElement(ArrayList<String> suggest){
        int rowidLastElement = smartIG.getLevel(smartIG.getActualLevelName()).getRowid();
        Object[] obj = new Object[3];
        obj[0] = userWord;
        obj[1] = suggest.get(0);
        obj[2] = suggest.get(1);
        ElementIG elementIG;
        elementIG = new ElementIG(rowidLastElement,obj,userWord, smartIG.getLevel(smartIG.getActualLevelName()));
        return elementIG;
    }

    /**
     * On écrti l'élément dans le fichier xlsx
     * @param elementIG l'élèment
     */
    public void write(ElementIG elementIG){
        smartIG.write(elementIG);
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
