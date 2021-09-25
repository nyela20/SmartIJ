package smartij.model;

public class NiveauIG {
    private final String levelname;
    private int rowid;
    private final int cellid;

    public NiveauIG(String levelname, int cellid) {
        this.levelname = levelname;
        this.cellid = cellid;
        this.rowid = 3;
    }

    public int getCellid() {
        return cellid;
    }

    public int getRowid() {
        return rowid;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public void incrementRowid(){
        rowid++;
    }

    public void decrementRowid(){
        rowid--;
    }
}
