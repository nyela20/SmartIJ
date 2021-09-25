package smartij.views;


import javafx.scene.control.ChoiceDialog;
import smartij.exceptions.ExceptionSmartIJ;
import smartij.model.ElementIG;
import smartij.model.SmartIG;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VueBoxChoiceSmartIJ extends ChoiceDialog {

    private final SmartIG smartIG;
    private final String userWord;

    public VueBoxChoiceSmartIJ(ArrayList<String> suggestion, SmartIG smartIG, String userWord) {
        super();
        this.smartIG = smartIG;
        this.userWord = userWord;
        aspect();
        add(suggestion);
        Optional<String> result = showAndWait();
        result.ifPresent(name -> {
            try {
                traiter(getResult().toString());
            } catch (ExceptionSmartIJ e) {
                e.printStackTrace();
            }
        });
    }

    public void traiter(String choice) throws ExceptionSmartIJ {
        ArrayList<String> suggest = new ArrayList<>(List.of(choice.split(" ")));
        write(createKnownElement(suggest));
    }

    private ElementIG createKnownElement(ArrayList<String> suggest) throws ExceptionSmartIJ {
        return smartIG.createKnownElement(suggest, userWord);
    }

    /**
     * On écrit l'élément dans le fichier xlsx
     *
     * @param elementIG l'élèment
     */
    private void write(ElementIG elementIG) throws ExceptionSmartIJ {
        //smartIG.write(elementIG);
    }

    public void aspect() {
        setHeight(200);
        setWidth(200);
        setHeaderText(userWord);
        setTitle("Proposition");
        setContentText("Select");
    }


    public void add(ArrayList<String> suggestion) {
        for (String s : suggestion) {
            getItems().add(s);
        }
        //ajouter choix manuel
        getItems().add("Saisir...");
    }

}
