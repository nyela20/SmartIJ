package smartij.model;

import java.util.ArrayList;
import java.util.Iterator;

public class CategoryIG implements Iterable<ElementIG>{

    private final ArrayList<ElementIG> elementIGS = new ArrayList<>();
    private final String name;
    private final int identifiant;
    private double posx;
    private double posy;
    private double height = 300;
    private double width = 300;

    public CategoryIG(String name, int identifiant, int posx,int posy){
        this.name = name;
        this.identifiant = identifiant;
        this.posx = posx;
        this.posy = posy;
    }

    public String getNameCategory() {
        return name;
    }

    public void addElement(ElementIG elementIG){
        elementIGS.add(elementIG);
    }

    public double getPosy() {
        return posy;
    }

    public double getPosx() {
        return posx;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void move(double posx, double posy){
        this.posx = posx;
        this.posy = posy;
    }

    @Override
    public Iterator<ElementIG> iterator() {
        return elementIGS.iterator();
    }
}
