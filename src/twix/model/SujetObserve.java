package twix.model;

import twix.views.Observateur;

import java.io.IOException;
import java.util.ArrayList;

public abstract class SujetObserve {
    private final ArrayList<Observateur> observateurObservateur = new ArrayList<>();

    public SujetObserve(){ }
    /**
     * ajoute une Observateur
     * @param observateur la observateur
     */
    public void ajouter(Observateur observateur) {
        observateurObservateur.add(observateur);
        //observateur.reagir();
    }

    /**
     * notifier les vues
     */
    public void notifierObservateur() {
        for (Observateur observateur : observateurObservateur) {
            try {
                observateur.reagir();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
