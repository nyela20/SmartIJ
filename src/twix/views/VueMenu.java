package twix.views;

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
    }

    /**
     * close application
     */
    public void quit(){
        System.exit(0);
    }



    @Override
    public void reagir() {

    }
}
