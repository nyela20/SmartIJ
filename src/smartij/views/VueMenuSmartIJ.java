package smartij.views;

import smartij.model.SmartIG;


public class VueMenuSmartIJ implements PatternObserver {

    private final SmartIG smartIG;

    public VueMenuSmartIJ(SmartIG smartIG) {
        this.smartIG = smartIG;
    }

    /**
     * open a html file
     */
    public void open(){
        smartIG.open();
        //on ajoute les unité une fois le fichier ouvert
        smartIG.addUnit();
    }

    public void addUnit(){
            smartIG.addUnit();
    }

    /**
     * close application
     */
    public void quit(){
        System.exit(0);
    }

    public void changeLevel(){
        smartIG.changeLevel();
    }

    public void addLevel(){
        smartIG.addLevel();
    }


    @Override
    public void reagir() {

    }
}
