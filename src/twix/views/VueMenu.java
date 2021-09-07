package twix.views;

import twix.exceptions.ExceptionTwix;
import twix.model.TwixIG;


public class VueMenu implements Observateur{

    private final TwixIG twixIG;

    public VueMenu(TwixIG twixIG) {
        this.twixIG = twixIG;
    }

    /**
     * open a html file
     */
    public void open(){
        twixIG.open();
        //on ajoute les unité une fois le fichier ouvert
        twixIG.addUnit();
    }

    public void addUnit(){
            twixIG.addUnit();
    }

    /**
     * close application
     */
    public void quit(){
        System.exit(0);
    }

    public void changeLevel(){
        twixIG.changeLevel();
    }

    public void addLevel(){
        twixIG.addLevel();
    }


    @Override
    public void reagir() {

    }
}
