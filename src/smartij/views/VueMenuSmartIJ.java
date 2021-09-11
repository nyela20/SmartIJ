package smartij.views;


import smartij.model.SmartIG;


public class VueMenuSmartIJ implements PatternObserver {



    private final SmartIG smartIG;

    public VueMenuSmartIJ(SmartIG smartIG) {
        this.smartIG = smartIG;
        //menuquit.setGraphic(new ImageView(new Image("smartij/sources/images/menulogo/supprimer.png")));
    }

    /**
     * open a html file
     */
    public void open() {
        smartIG.open();
    }

    public void addUnit() {
        smartIG.addUnit();
    }

    /**
     * close application
     */
    public void quit() {
        System.exit(0);
    }

    public void removeAllUnit(){
        smartIG.removeAllUnit();
    }

    public void changeLevel() {
        smartIG.changeLevel();
    }

    public void addLevel() {
        smartIG.addLevel();
    }


    @Override
    public void reagir() {}
}
