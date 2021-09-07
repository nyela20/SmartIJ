package twix.model;

import java.util.ArrayList;
import java.util.List;

public class LevelXlsx {
    private String levelname;
    private int rowid;
    private final int cellid;

    public LevelXlsx(String levelname, int cellid){
        this.levelname = levelname;
        this.cellid = cellid;
        this.rowid = 3;
    }

    public int getCellid(){
        return cellid;
    }

    public int getRowid(){
        return rowid;
    }


    public String getLevelname(){
        return levelname;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }
}
