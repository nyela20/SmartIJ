package smartij.exceptions;

public class ExceptionSmartIJ extends Exception {

    public ExceptionSmartIJ(String message) {
        super(message);
        new BoxDialogExceptionSmartIJ(message);
        System.err.println(message);
    }
}
