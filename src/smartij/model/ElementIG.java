package smartij.model;

public class ElementIG {
    private final int rowid;
    private final int cellid;
    private final Object[] objects;
    private final String name;


    public ElementIG(int rowid, Object[] objects, String name, NiveauIG niveauIG) {
        this.rowid = rowid;
        this.cellid = niveauIG.getCellid();
        this.objects = objects;
        this.name = name;
        niveauIG.setRowid(niveauIG.getRowid() +1);
    }

    public String getNameElement(){
        return name;
    }

    public String getUnitElement(){
        return  objects[1].toString();
    }

    public int getvalueElement(){
        return Integer.parseInt(objects[2].toString());
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
