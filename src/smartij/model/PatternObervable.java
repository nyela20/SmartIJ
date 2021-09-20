package smartij.model;

import smartij.exceptions.ExceptionSmartIJ;
import smartij.views.PatternObserver;

import java.io.IOException;
import java.util.ArrayList;

public abstract class PatternObervable {
    private final ArrayList<PatternObserver> observateurPatternObserver = new ArrayList<>();

    public PatternObervable() {
    }

    /**
     * ajoute un PatternObserver
     * @param patternObserver la patternObserver
     */
    public void ajouter(PatternObserver patternObserver) {
        observateurPatternObserver.add(patternObserver);
    }

    /**
     * notifier les vues
     */
    public void notifierObservateur() {
        for (PatternObserver patternObserver : observateurPatternObserver) {
            try {
                patternObserver.reagir();
            } catch (IOException | ExceptionSmartIJ e) {
                e.printStackTrace();
            }
        }
    }
}
