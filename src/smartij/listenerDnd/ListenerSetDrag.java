package smartij.listenerDnd;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

public class ListenerSetDrag implements EventHandler<DragEvent> {

    /**
     * Ecouteur qui sert uniquement à autoriser le DragAndDrop
     */
    public ListenerSetDrag() {
    }

    @Override
    public void handle(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        event.consume();
    }
}