package smartij.fabrics;

public class FabricRowsIdAndCellId {
    private static final FabricRowsIdAndCellId instance = new FabricRowsIdAndCellId();
    private int cellid;
    private int rowid;

    private FabricRowsIdAndCellId() {
        cellid = 2;
        rowid = 3;
    }

    public static FabricRowsIdAndCellId getInstance() {
        return instance;
    }

    public int getcellidLevel() {
        cellid++;
        return cellid - 1;
    }

    public int getRowidLevel() {
        rowid++;
        return rowid - 1;
    }

    public void incrementRowid(){
        rowid++;
    }
}
