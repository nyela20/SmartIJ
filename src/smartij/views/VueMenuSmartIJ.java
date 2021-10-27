package smartij.views;


import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import smartij.exceptions.ExceptionSmartIJ;
import smartij.model.CategoryIG;
import smartij.model.ElementIG;
import smartij.model.SmartIG;

import java.util.ArrayList;


public class VueMenuSmartIJ implements PatternObserver {

    @FXML
    private Menu menuSuggest;

    private final SmartIG smartIG;
    private ArrayList<Menu> menuArray = new ArrayList<>();

    public VueMenuSmartIJ(SmartIG smartIG) {
        this.smartIG = smartIG;
        this.smartIG.ajouter(this);
    }

    /**
     * open a html file
     */
    public void open(){
        smartIG.open();
    }

    public void addUnit() {
        try {
            smartIG.addUnit();
        } catch (ExceptionSmartIJ e) {
            e.printStackTrace();
        }
    }


    public void removeSelectedCat(){
        smartIG.removeCategorie();
    }

    public void generateFile(){
        smartIG.generateFile();
    }

    public void writeInFile(){
        try {
            smartIG.writeFile();
        } catch (ExceptionSmartIJ e) {
            e.printStackTrace();
        }
    }

    /**
     * close application
     */
    public void quit() {
        System.exit(0);
    }

    public void removeAllUnit(){
        smartIG.removeUnit();
    }

    public void changeLevel() {
        try {
            smartIG.changeLevel();
        } catch (ExceptionSmartIJ e) {
            e.printStackTrace();
        }
    }

    public void addLevel() {
        try {
            smartIG.addLevel();
        } catch (ExceptionSmartIJ e) {
            e.printStackTrace();
        }
    }

    private MenuItem get(String menu) throws ExceptionSmartIJ {
        throw new ExceptionSmartIJ("Aucun Menu trouvé");
    }



    private void addSuggestInMenu(Menu menu,String nameEl) {
        MenuItem elem = new MenuItem(nameEl);
        elem.setOnAction((actionEvent -> {
            try {
                smartIG.setActualCategory(menu.getText());
                smartIG.suggestForUser(smartIG.suggestWord(smartIG.getText(), elem.getText()), elem.getText());
            } catch (ExceptionSmartIJ e) {
                e.printStackTrace();
            }
        }));
        if(!alreadyExist(menu,nameEl)){
            menu.getItems().add(elem);
        }
    }

    private boolean alreadyExist(Menu menu, String nameEl){
        for(MenuItem menuItem : menu.getItems()){
            if(menuItem.getText().equals(nameEl)){
                return true;
            }
        }
        return false;
    }



    private boolean alreadyExist(String nameCategory){
        for(Menu menu : menuArray){
            if(menu.getText().equals(nameCategory)){
                return true;
            }
        }
        return false;
    }

    public void generateElementFromFile(){
        smartIG.generateElementFromFile();
    }


    private Menu getMenu(String nameMenu) throws ExceptionSmartIJ {
        for(Menu menu : menuArray){
            if(menu.getText().equals(nameMenu)){
                return menu;
            }
        }
        throw new ExceptionSmartIJ("Erreur : aucun menu trouvé");
    }

    private void addSuggestInMap() throws ExceptionSmartIJ {
        for (CategoryIG categoryIG : smartIG) {
            for (ElementIG elementIG : categoryIG) {
                Menu menu = new Menu(elementIG.getNameCategorie());
                if (!alreadyExist(menu.getText())) {
                    menuArray.add(menu);
                    menuSuggest.getItems().add(menu);
                }
                addSuggestInMenu(getMenu(menu.getText()), elementIG.getNameElement());
            }
        }
    }

    @Override
    public void reagir() {
        try {
            addSuggestInMap();
        } catch (ExceptionSmartIJ e) {}
    }
}
