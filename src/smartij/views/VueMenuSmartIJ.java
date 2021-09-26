package smartij.views;


import javafx.scene.control.MenuItem;
import smartij.exceptions.ExceptionSmartIJ;
import smartij.model.SmartIG;


public class VueMenuSmartIJ implements PatternObserver {



    private final SmartIG smartIG;

    public VueMenuSmartIJ(SmartIG smartIG) {
        this.smartIG = smartIG;
    }

    /**
     * open a html file
     */
    public void open() {
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
        //smartIG.removeSelectedCat();
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


    @Override
    public void reagir() {}
}
