package smartij.exceptions;

public class ExceptionSmartIJ extends Exception {

    public ExceptionSmartIJ(String message) {
        super(message);
        System.err.println(message);
    }
}
