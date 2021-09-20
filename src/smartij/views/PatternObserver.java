package smartij.views;

import smartij.exceptions.ExceptionSmartIJ;

import java.io.IOException;

public interface PatternObserver {
    void reagir() throws IOException, ExceptionSmartIJ;
}
