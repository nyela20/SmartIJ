package smartij.model;

import smartij.exceptions.ExceptionSmartIJ;
import smartij.views.PatternObserver;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class PatternObervable  {
    private ArrayList<PatternObserver> observateurPatternObserver = new ArrayList<>();

    public PatternObervable() {}


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
        Iterator<PatternObserver> patternObserverIterator = observateurPatternObserver.iterator();
        while (patternObserverIterator.hasNext()) {
            PatternObserver patternObserver = patternObserverIterator.next();
            try {
                patternObserver.reagir();
            } catch (IOException | ExceptionSmartIJ e) {
                e.printStackTrace();
            }
        }
    }

}
