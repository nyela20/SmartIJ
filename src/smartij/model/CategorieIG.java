package smartij.model;

import java.util.ArrayList;
import java.util.Iterator;

public class CategorieIG implements Iterable<ElementIG>{

    private final ArrayList<ElementIG> elementIGS = new ArrayList<>();
    private final String name;

    public CategorieIG(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Iterator<ElementIG> iterator() {
        return elementIGS.iterator();
    }
}
