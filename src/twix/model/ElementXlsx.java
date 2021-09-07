package twix.model;

public class ElementXlsx {
    private final int rowid;
    private final int cellid;
    private final Object[] objects;
    private final String name;


    public ElementXlsx(int rowid, Object[] objects, String name, LevelXlsx levelXlsx) {
        this.rowid = rowid;
        this.cellid = levelXlsx.getCellid();
        this.objects = objects;
        this.name = name;
        levelXlsx.setRowid(rowid++);
    }

    public String getNameElement(){
        return name;
    }

    public int getUnitElement(){
        return (int) objects[1];
    }

    public int getvalueElement(){
        return (int) objects[2];
    }

    public int getCellid() {
        return cellid;
    }


    public int getRowid() {
        return rowid;
    }


    public Object[] getObjects() {
        return objects;
    }

    public int numberOfElement(){
        return objects.length;
    }

}
