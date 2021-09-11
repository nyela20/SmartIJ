package smartij.fabrics;

public class FabricRowsIdAndCellId {
    private static final FabricRowsIdAndCellId instance = new FabricRowsIdAndCellId();
    private int cellid;

    private FabricRowsIdAndCellId() {
        cellid = 2;
    }

    public static FabricRowsIdAndCellId getInstance() {
        return instance;
    }

    public int getcellidLevel() {
        cellid++;
        return cellid - 1;
    }
}
