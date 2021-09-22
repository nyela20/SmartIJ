package smartij.model;

import java.util.ArrayList;
import java.util.Iterator;

public class CategoryIG implements Iterable<ElementIG>{

    private final ArrayList<ElementIG> elementIGS = new ArrayList<>();
    private final String name;
    private double posx;
    private double posy;
    private boolean isSelected = false;
    private int rowid = 3;

    /**
     * Constructeur d'une cat
     * @param name le nom de la cat
     * @param posx l'abscisse de cat
     * @param posy l'ordonnée de cat
     */
    public CategoryIG(String name, int posx,int posy){
        this.name = name;
        this.posx = posx;
        this.posy = posy;
    }

    public void changeState() {
        isSelected = !isSelected;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public int getRowid() {
        return rowid;
    }

    public boolean isSelected() {
        return isSelected;
    }


    /**
     * retourne le nom de la catégorie
     * @return le nom de la catégorie
     */
    public String getNameCategory() {
        return name;
    }

    /**
     * ajouter un ElementIG dans la cat
     * @param elementIG l'élement à rajouter
     */
    public void addElement(ElementIG elementIG){
        elementIGS.add(elementIG);
        System.out.println("ajout : " + elementIGS.size() );
    }

    /**
     * retourne l'ordonnée de la cat
     * @return son ordonnée
     */
    public double getPosy() {
        return posy;
    }

    /**
     * retourne l'abscisse de la cat
     * @return l'abscisse
     */
    public double getPosx() {
        return posx;
    }

    /**
     * retourne la hauteur de la cat
     * @return la hauteur
     */
    public double getHeight() {
        return 300;
    }

    /**
     * retourne la largeur de la cat
     * @return la largeur
     */
    public double getWidth() {
        return 500;
    }

    /**
     * deplacer une cat
     * @param posx nouvelle abscisse
     * @param posy nouvelle ordonnée
     */
    public void move(double posx, double posy){
        this.posx = posx;
        this.posy = posy;
    }


    @Override
    public Iterator<ElementIG> iterator() {
        return elementIGS.iterator();
    }
}
