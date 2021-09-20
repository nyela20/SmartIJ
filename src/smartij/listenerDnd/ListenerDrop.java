package smartij.listenerDnd;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import smartij.model.CategoryIG;
import smartij.model.SmartIG;

public class ListenerDrop implements EventHandler<DragEvent> {
    private final SmartIG smartIG;

    /**
     * Ecouteur du dropped de Dnd pour une EtapeIG
     *
     */
    public ListenerDrop(SmartIG smartIG) {
        this.smartIG = smartIG;
    }


    @Override
    public void handle(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        CategoryIG categoryIG = null;
        try {
            categoryIG = smartIG.getCategory(dragboard.getString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert categoryIG != null;
        smartIG.moveCategory(categoryIG,event.getSceneX() - categoryIG.getWidth() / 2,event.getSceneY() -  categoryIG.getHeight() / 2);
        event.setDropCompleted(true);
        event.consume();
    }
}
