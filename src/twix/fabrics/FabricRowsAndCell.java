package twix.fabrics;

public class FabricRowsAndCell {
    private static final FabricRowsAndCell instance = new FabricRowsAndCell();
    private int cellid;

    private FabricRowsAndCell(){
        cellid = 3;
    }

    public static FabricRowsAndCell getInstance(){
        return instance;
    }

    public int getcellidLevel(){
        cellid++;
        return cellid-1;
    }
}
