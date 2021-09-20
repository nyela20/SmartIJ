package smartij.fabrics;

public class FabricIdPostit {
    private static final FabricIdPostit instance = new FabricIdPostit();
    private int id;

    private FabricIdPostit() {
        id = 0;
    }

    public static FabricIdPostit getInstance() {
        return instance;
    }

    public int getIdPostit() {
        id++;
        return id - 1;
    }
}
