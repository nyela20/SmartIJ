package smartij.model;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import smartij.exceptions.BoxDialogExceptionSmartIJ;
import smartij.exceptions.ExceptionSmartIJ;
import smartij.fabrics.FabricRowsIdAndCellId;
import smartij.views.VueBoxChoiceSmartIJ;
import smartij.views.VueBoxDialogSmartIJ;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class SmartIG extends PatternObervable implements Iterable<CategoryIG> {

    private int state = 0;
    private File file = null;
    private File fileCalc = null;
    private String actualLevel;
    private String actualCategory;
    private final ArrayList<String> unitIGS = new ArrayList<>();
    private final ArrayList<NiveauIG> niveauIGS = new ArrayList<>();
    private final ArrayList<CategoryIG> categoryIGS = new ArrayList<>();
    private final XSSFWorkbook workbook = new XSSFWorkbook();
    private final XSSFSheet sheet = workbook.createSheet();


    public SmartIG() {
        super();
        addUnits("u", "m2", "ml", "m²");
    }

    /**
     * ajouter une liste d'unités
     *
     * @param stringsUnit la liste d'unités
     */
    private void addUnits(String... stringsUnit) {
        this.unitIGS.addAll(List.of(stringsUnit));
    }

    /**
     * change le nom de la catégorie actuelle
     *
     * @param newcat la nouvelle catégorie
     */
    public void setCategory(String newcat) {
        this.actualCategory = newcat;
    }


    /**
     * le nombre de niveau
     *
     * @return le nombre de niveau
     */
    public int getnumberOfLevel() {
        return niveauIGS.size();
    }

    /**
     * supprime toutes le unités en mémoire
     */
    public void removeAllUnit() {
        unitIGS.clear();
        notifierObservateur();
    }

    /**
     * retourne le nom de la catégorie actuelle
     * @return le nom de la catégorie actuelle
     */
    public String getActualCategoryName(){
        return actualCategory;
    }

    /**
     * la fonction supprime une unité
     *
     * @param unitName la nom de l'unité
     */
    public void removeUnit(String unitName) {
        ChoiceDialog<Object> choiceDialog = new ChoiceDialog<>();
        choiceDialog.getItems().addAll(unitIGS);
        choiceDialog.showAndWait();
        if (choiceDialog.getResult() != null && !(choiceDialog.getResult().toString().isEmpty())) {
            removeUnit(choiceDialog.getResult().toString());
            notifierObservateur();
        }
    }

    /**
     * retourne le nom du niveau actuel
     *
     * @return le nom du niveau actuel
     */
    public String getActualLevelName() {
        return actualLevel;
    }


    /**
     * retourne une categorie
     *
     * @param namecat le nom de la categorie à retourner
     * @return une categorie
     */
    public CategoryIG getCategory(String namecat) throws ExceptionSmartIJ {
        for (CategoryIG c : categoryIGS) {
            if (c.getNameCategory().equals(namecat))
                return c;
        }
        throw new ExceptionSmartIJ("Erreur mémoire : CatégorieIG introuvable");
    }


    /**
     * retourne un niveau
     *
     * @param name le nom du niveau
     * @return un niveau
     */
    private NiveauIG getLevel(String name) throws ExceptionSmartIJ {
        for (NiveauIG niveauIG : niveauIGS) {
            if (equals(niveauIG.getLevelname(), name))
                return niveauIG;
        }
        throw new ExceptionSmartIJ("Erreur mémoire : NiveauIG introuvable");
    }

    /**
     * recherche et retourne un élément en mémoire
     *
     * @param name son nom
     * @return un Element
     */
    private ElementIG getElement(String name) throws ExceptionSmartIJ {
        for (CategoryIG categoryIG : this) {
            for (ElementIG elementIG : categoryIG) {
                if (Objects.equals(name, elementIG.getNameElement())) {
                    return elementIG;
                }
            }
        }
        throw new ExceptionSmartIJ("Erreur mémoire : ElementIG introuvable");
    }


    /**
     * la fonction sert à verifier si un élement similaire existe déjà
     * en mémoire
     *
     * @param elementIG l'élèment à vérifier
     * @return vrai sinon faux
     */
    private boolean alreadyExist(ElementIG elementIG) {
        for (CategoryIG categoryIG : this) {
            for (ElementIG elementIG1 : categoryIG) {
                if (elementIG1.getNameElement().equals(elementIG.getNameElement())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * la fonction sert à verifier si un niveau similaire existe déjà
     * en mémoire
     *
     * @param niveauIG l'élèment à vérifier
     * @return vrai sinon faux
     */
    private boolean alreadyExist(NiveauIG niveauIG) {
        for (NiveauIG niveauIG1 : niveauIGS) {
            if (niveauIG1.getLevelname().equals(niveauIG.getLevelname())) {
                return true;
            }
        }
        return false;
    }

    /**
     * la fonction verifie si une categorie
     * comme celui en paramètre existe déjà
     * @param categoryIG la référence
     * @return true or false
     */
    private boolean alreadyExist(CategoryIG categoryIG){
        for(CategoryIG categoryIG1 : this){
            if(categoryIG1.getNameCategory().equals(categoryIG.getNameCategory())){
                return true;
            }
        }
        return false;
    }

    /**
     * retourne le state actuel
     *
     * @return le state acutel
     */
    public int getState() {
        return state;
    }

    /**
     * retourne le nom d'un fichier
     *
     * @return le nom d'un fichier807D6E
     */
    public String getFileName() throws ExceptionSmartIJ {
        return file.getName();
    }


    /**
     * retourne le numéro de la dernière ligne libre d'un niveau
     *
     * @param nameLevel le nom du niveau
     * @return le nunméro de la dernièreligne
     */
    private int lastRowIdLevel(String nameLevel) throws ExceptionSmartIJ {
        return Objects.requireNonNull(getLevel(nameLevel)).getRowid();
    }

    private int lastRowIdCategory(String nameCategory) throws ExceptionSmartIJ{
        return Objects.requireNonNull(getCategory(nameCategory).getRowid());
    }

    /**
     * La fonction retourne vrai si le String est composé uniquement d'entier
     * de [0 à 9]*, sinon faux
     *
     * @param chaine la chaine à verifier
     * @return vrai si c'est un entier, sinon faux
     */
    private boolean estUnEntier(String chaine) {
        try {
            Integer.parseInt(chaine);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * retourne le nombre de mot dans une "phrase"
     *
     * @param s le mot
     * @return le nombre de mot
     */
    private int numberOfWord(String s) {
        return s.split(" ").length;
    }

    /**
     * la fonction déplace une interface de CategorieIG
     *
     * @param categoryIG la catégorie
     * @param posx       la position x
     * @param posy       la position y
     */
    public void moveCategory(CategoryIG categoryIG, double posx, double posy) throws ExceptionSmartIJ {
        getCategory(categoryIG.getNameCategory()).move(posx, posy);
        notifierObservateur();
    }

    /**
     * la fonction supprime les catégorie selectionnés
     */
    public void removeSelectedCat(){
        Iterator<CategoryIG> it = iterator();
        while (it.hasNext()) {
            CategoryIG categoryIG = it.next();
            if(categoryIG.isSelected()){
                it.remove();
            }
        }
        notifierObservateur();
    }

    /**
     * La fonction change l'Etat (selectionnée ou non) d'une catégorie
     * @param categoryIG la catégorie
     */
    public void changeCatState(CategoryIG categoryIG){
        categoryIG.changeState();
        notifierObservateur();
    }

    /**
     * La fonction vérifie que deux mots sont pareil
     */
    private static boolean equals(String a, String b) {
        if (a.length() == 0) return false;
        if (b.length() < a.length()) return false;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i))
                return false;
        }
        return true;
    }


    /**
     * Ouverture du fichier de l'utilisateur
     */
    public void open() {
        try {
            JFileChooser jFileChooser = new JFileChooser("src/smartij/tools/page/pagetxt");
            jFileChooser.setMultiSelectionEnabled(false);
            jFileChooser.setApproveButtonText("choisir");
            int res = jFileChooser.showSaveDialog(jFileChooser.getParent());
            if (res == JFileChooser.APPROVE_OPTION) {
                file = jFileChooser.getSelectedFile();
                createXlsx(file.getParentFile().getPath());
                if(file.exists()) {
                    state = 1;
                }else{
                    state = 0;
                    new BoxDialogExceptionSmartIJ("Aucun affichage car le fichier choisi n'est pas valide");
                }
            } else {
                jFileChooser.cancelSelection();
            }
            notifierObservateur();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * lecture du fichier
     */
    private StringBuilder read() {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedreader = null;
        FileReader filereader = null;
        try {
            filereader = new FileReader(file.getPath());
            if (file.length() != 0) {
                bufferedreader = new BufferedReader(filereader);
                String strCurrentLine;
                while ((strCurrentLine = bufferedreader.readLine()) != null) {
                    stringBuilder.append(strCurrentLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedreader != null)
                    bufferedreader.close();
                if (filereader != null)
                    filereader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder;
    }

    /**
     * La fonction va chercher la dernière ligne
     * libre parmi les niveau et renvoie le
     * numéro de cette ligne
     * @return
     */
    private int rowMax(){
        int maxNiveau = 0;
        for(NiveauIG niveauIG : niveauIGS){
            if(niveauIG.getRowid() > maxNiveau)
                maxNiveau = niveauIG.getRowid();
        }
        return maxNiveau;
    }

    /**
     * La fonction incrément toutes
     * niveaux au max
     */
    private void rowforMax(){
        int rowMax = rowMax();
        for(NiveauIG niveauIG : niveauIGS){
            niveauIG.setRowid(rowMax);
        }
    }

    private void updateRowForAllLevel() {
        rowforMax();
        for (NiveauIG niveauIG : niveauIGS) {
            niveauIG.setRowid(niveauIG.getRowid() + 1);
        }
    }

/*
    public void write(ElementIG elementIG) throws ExceptionSmartIJ {
        XSSFRow row;
        row = sheet.createRow(elementIG.getRowid());
        writepre(row, elementIG);
        for (int i = 0; i < elementIG.numberOfObjects(); i += 2) {
            Cell cell = row.createCell(Objects.requireNonNull(getLevel(elementIG.getStringValueElement(i + 3))).getCellid());
            cell.setCellValue(elementIG.getvalueElement(i + 2));
        }
    }
  */



/*
    public void write(CategoryIG categoryIG){
        categoryIG.setRowid(rowMax());
        System.out.println("(C) setter r-> " + categoryIG.getRowid());
        rowforIncrement();
        System.out.println("(!)  rowforIncrement (!)");
        //affichage
        for(CategoryIG categoryIG1 : this){
            for(ElementIG elementIG : categoryIG){
                System.out.println("(!) rfi effect " + elementIG.getNameElement() + " r-> " + elementIG.getRowid());
            }
        }
        //affichage
        XSSFRow row;
        row = sheet.createRow(categoryIG.getRowid());
        Cell cellName = row.createCell(0);
        cellName.setCellValue(categoryIG.getNameCategory());
    }

 */


    /**
     * La fonction écrit tout dans le fichier
     * @throws ExceptionSmartIJ
     */
    public void writeFile() throws ExceptionSmartIJ {
        for(CategoryIG categoryIG : this){
            System.out.println("(C) write " + categoryIG.getNameCategory() + " r-> " + categoryIG.getRowid());
            //write(categoryIG);
            for(ElementIG elementIG : categoryIG) {
                //write(elementIG);
                System.out.println("(NE)" + elementIG.getNameLevel() + " (CE) " + elementIG.getNameCategorie() + " nr -> " + getLevel(elementIG.getNameLevel()).getRowid() + " nc-> " + getLevel(elementIG.getNameLevel()).getCellid());
                System.out.println("(E)" + elementIG.getNameElement() + " r-> " + elementIG.getRowid() + " obj-> " + elementIG.obj());
            }
        }
    }


    private void writepre(XSSFRow row, ElementIG e) {
        int cellid = 0;
        Cell cellName = row.createCell(cellid);
        cellName.setCellValue(e.getNameElement());
        cellid++;
        Cell cellUnit = row.createCell(cellid);
        cellUnit.setCellValue(e.getUnitElement());
    }


    /**
     * Find the word in the file
     */
    public void search() throws ExceptionSmartIJ {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText("Choisir un élément");
        textInputDialog.showAndWait();
        String userWord = textInputDialog.getEditor().getText();
        String resultSplit = read().toString().replaceAll(" ", "\n");
        ArrayList<String> text = new ArrayList<>();
        Collections.addAll(text, resultSplit.split("\n"));
        autoCorrect(text);
        if (!userWord.isEmpty() && !text.isEmpty()) {
            if (!suggestWord(text, userWord).isEmpty()) {
                suggestForUser(suggestWord(text, userWord), userWord);
            } else {
                throw new ExceptionSmartIJ("Aucun mot correspondant");
            }
        }
    }


    /**
     * La fonction va créer un fichier .xlsx
     *
     * @param pathname le chemin vers le fichier
     */
    private void createXlsx(String pathname) {
        try {
            if (!Files.exists(Paths.get(pathname + "/file.xlsx"))) {
                Path file = Files.createFile(Paths.get(pathname + "/file.xlsx"));
                fileCalc = file.toFile();
            } else {
                Files.delete(Paths.get(pathname + "/file.xlsx"));
                createXlsx(pathname);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * La foction autoCorrect va servir à corriger les erreurs possibles
     * qui ont été engendrées durant la convertion du .pdf en .txt
     * Par exemple  : deux mots qui sont collée
     * : un saut de ligne non respécté
     * : etc ...
     *
     * @param text le texte
     */
    private void autoCorrect(ArrayList<String> text) {
        //autocorrection séparation mot
        for (String word : text) {
            for (String unit : this.unitIGS) {
                StringBuilder s = new StringBuilder(word);
                if (word.startsWith(unit)) {
                    s.replace(0, 0, unit + "\n");
                    for (int i = 0; i < unit.length(); i++) s.deleteCharAt(unit.length() + 1);
                    text.set(text.indexOf(word), s.toString());
                }
            }
        }
        String tmp = text.toString().replaceAll(",", "\n");
        String tmp2 = tmp.replaceAll(" ", "");
        text.clear();
        Collections.addAll(text, tmp2.split("\n"));
    }



    /**
     * Changer de niveau implique le changement de colonne dans le tableau
     */
    public void changeLevel() throws ExceptionSmartIJ {
        if (getnumberOfLevel() > 0) {
            ChoiceDialog<Object> choiceDialog = new ChoiceDialog<>();
            ArrayList<String> tmp = new ArrayList<>();
            for(NiveauIG niveauIG : niveauIGS){
                tmp.add(niveauIG.getLevelname());
            }
            choiceDialog.getItems().addAll(tmp);
            choiceDialog.showAndWait();
            if (choiceDialog.getResult() != null && !(choiceDialog.getResult().toString().isEmpty())) {
                this.actualLevel = choiceDialog.getResult().toString();
                rowforMax();
                System.out.println("(rowForMax) all r-> " + getLevel(actualLevel).getRowid());
                notifierObservateur();
            }
        }
    }

    /**
     * ajouter une catégorie
     */
    public void addCategory() throws ExceptionSmartIJ {
        if (actualLevel != null && !(actualLevel.isEmpty())) {
            VueBoxDialogSmartIJ vueBoxDialogSmartIJ = new VueBoxDialogSmartIJ("Nouvelle catégorie", "Ajouter une catégorie", "ajouter");
            Optional<String> result = Optional.ofNullable(vueBoxDialogSmartIJ.getResult());
            result.ifPresent(name -> {
                if (!result.get().isEmpty()) {
                    CategoryIG categoryIG = new CategoryIG(result.get(), 100, 100);
                    if (!alreadyExist(categoryIG)) {
                        categoryIG.setRowid(rowMax());
                        System.out.println("new (C) " + categoryIG.getNameCategory() + " r-> " + categoryIG.getRowid());
                        categoryIGS.add(categoryIG);
                        updateRowForAllLevel();
                        notifierObservateur();
                    } else {
                        new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Une catégorie de même nom existe déjà").getMessage());
                    }
                }
            });
            notifierObservateur();
        }
    }

    /**
     * La fonction sert à rajouter un Niveau
     */
    public void addLevel() throws ExceptionSmartIJ {
        VueBoxDialogSmartIJ vueBoxDialogSmartIJ = new VueBoxDialogSmartIJ("Nouveau Niveau", "Ajouter un niveau", "Ajouter");
        Optional<String> result = Optional.ofNullable(vueBoxDialogSmartIJ.getResult());
        result.ifPresent(name -> {
            if (!result.get().isEmpty()) {
                NiveauIG niveauIG = new NiveauIG(vueBoxDialogSmartIJ.getResult(), FabricRowsIdAndCellId.getInstance().getcellidLevel());
                if(!alreadyExist(niveauIG)) {
                    niveauIGS.add(niveauIG);
                    notifierObservateur();
                    //écrire le level dans le xlsx une fois ajouter
                    writeLevel();
                }else{
                    new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Un niveau du même nom existe déjà").getMessage());
                }
            }
        });
    }


    /**
     * La fonction rajoute/écrit un nouveau niveau dans e ficier .xlsx
     */
    private void writeLevel() {
        XSSFRow row;
        row = sheet.createRow(2);
        for (NiveauIG obj : niveauIGS) {
            Cell cell = row.createCell(obj.getCellid());
            cell.setCellValue(obj.getLevelname());
        }
        try {
            FileOutputStream out = new FileOutputStream(fileCalc);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * La fonction propose des valeurs à l'utilisateur.
     * Si une seul proposition est trouvée, l'écriture est immédiate.
     *
     * @param suggestion un tableau de valeurs (à suggérer)
     * @param userWord   le mot entré par l'utilisateur
     */
    private void suggestForUser(ArrayList<String> suggestion, String userWord) throws ExceptionSmartIJ {
        if(actualLevel != null &&!(actualLevel.isEmpty())) {
            if (suggestion.size() > 1) {
                new VueBoxChoiceSmartIJ(suggestion, this, userWord);
            } else {
                ArrayList<String> suggest = new ArrayList<>(List.of(suggestion.get(0).split(" ")));
                ElementIG elementIG = createKnownElement(suggest,userWord);
                ifalredyExit(elementIG);
                notifierObservateur();
            }
        }
    }


    private void ifalredyExit(ElementIG elementIG) throws ExceptionSmartIJ {
        if(alreadyExist(elementIG)){
            ElementIG father = getElement(elementIG.getNameElement());
            father.addObject(elementIG.getvalueElement(2), getActualLevelName());
            System.out.println("j'incrément pas le niveau (father) " + actualLevel + " rf-> " + father.getRowid() + " rn-> " + getLevel(actualLevel).getRowid());
        }else {
            getCategory(actualCategory).addElement(elementIG);
            getLevel(getActualLevelName()).incrementRowid();
            System.out.println("j'incrément le niveau " + actualLevel + " r-> " + getLevel(actualLevel).getRowid());
        }
    }



    /**
     * renvoie une suggestion de chiffre
     *
     * @param text     le fichier txt
     * @param userword le mot cké
     * @return un tableau contenant les suggestions
     */
    private ArrayList<String> suggestWord(ArrayList<String> text, String userword) {
        ArrayList<String> suggestion = new ArrayList<>();
        if(suggestWordImprove(text,userword) > 0){
            int index = searchFirstNumberFrom(text,suggestWordImprove(text,userword));
            suggestion.add(findUnit(text,index) + " " + text.get(index));
        }else{
            int index = searchWord(text, userword, 0);
            if (index > 0) {
                boolean stop = false;
                while (!stop) {
                    index = searchFirstNumberFrom(text, index + 1);
                    if (searchWord(text, userword, index) == -1) {
                        stop = !estUnEntier(text.get(index + 1));
                    }
                    suggestion.add(findUnit(text, index) + " " + text.get(index));
                }
            }
        }
        return suggestion;
    }

    /**
     * Une fonction amélioré de la première fonction de recherche suggestWord
     * @param text le texte
     * @param userword le mot de l'utilisateur
     * @return un arraylist de mot
     */
    private int suggestWordImprove(ArrayList<String> text, String userword) {
        String tmpuserword = userword.replaceAll(" ", "");
        StringBuilder s = new StringBuilder();
        int i = 0, index = 0;
        while (!equals(s.toString(), tmpuserword) && index < text.size()) {
            s.delete(0, s.length());
            index = searchWord(text, userword, i);
            if (index < 0) {
                //index retourne -1
                index = index - numberOfWord(userword);
                break;
            }
            for (int j = index; j < index + numberOfWord(userword); j++) {
                s.append(text.get(j));
            }
            i = index + numberOfWord(userword);
        }
        return index + numberOfWord(userword);
    }

    /**
     * Search word, return the index of the first letter
     */
    private int searchWord(ArrayList<String> text, String userword, int start) {
        for (int i = start; i < text.size(); i++) {
            if (equals(text.get(i), userword)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * recherche le premier nombre qui suit l'index en paramètre dans le texte
     *
     * @param text  le texte
     * @param index l'index
     * @return le premier nombre qui suit l'index, sinon -1 si erreur
     */
    private int searchFirstNumberFrom(ArrayList<String> text, int index) {
        for (int i = index; i < text.size(); i++) {
            if (estUnEntier(text.get(i)))
                return i;
        }
        return -1;
    }

    /**
     * ajoute une unité
     */
    public void addUnit() throws ExceptionSmartIJ {
        VueBoxDialogSmartIJ vueBoxDialogSmartIJ = new VueBoxDialogSmartIJ("Les unités actuelles : \n" + unitIGS, "ajouter", "Ajouter unité");
        if( vueBoxDialogSmartIJ.getAnswer() != null && !(vueBoxDialogSmartIJ.getAnswer().isEmpty()))
            unitIGS.add(vueBoxDialogSmartIJ.getAnswer());
        notifierObservateur();
    }


    /**
     * Trouve l'unité d'un chiffre donné
     *
     * @param text  le texte
     * @param index l'index du chiffre en question
     */
    private String findUnit(ArrayList<String> text, int index) {
        for (int i = index + 1; i < text.size(); i++) {
            for (String unit : unitIGS) {
                if (text.get(i).equals(unit)) {
                    return text.get(i);
                }
            }
        }
        return ".";
    }


    /**
     * Créer un élement à partir d'un tableau de suggestion et du mot de l'utilisateur
     *
     * @param suggest  un tableau de suggestion
     * @param userWord le mot entrée par l'utilisateur
     * @return element
     */
    public ElementIG createKnownElement(ArrayList<String> suggest, String userWord) throws ExceptionSmartIJ {
        Object[] obj = new Object[4];
        obj[0] = userWord;
        obj[1] = suggest.get(0);
        obj[2] = suggest.get(1);
        obj[3] = getActualLevelName();
        ElementIG elementIG;
        elementIG = new ElementIG(obj, Objects.requireNonNull(getLevel(getActualLevelName())),Objects.requireNonNull(getCategory(getActualCategoryName())));
        System.out.println("new (E) " + elementIG.getNameElement() + " r-> " + elementIG.getRowid());
        return elementIG;
    }




    @Override
    public Iterator<CategoryIG> iterator() {
        return categoryIGS.iterator();
    }
}


    /*


    public void write(CategoryIG categoryIG) throws ExceptionSmartIJ {
        //On redefinit la catégorie pour ne pas changer le mecanisme
        categoryIG.setRowid(rowforCategory());
        XSSFRow row;
        //System.out.println("(C) Le rowid du niveau actuel est : " + getLevel(actualLevel).getRowid());
        //System.out.println("J'écris une cat " + categoryIG.getNameCategory() + " son rowid est : " + categoryIG.getRowid()) ;
        row = sheet.createRow(categoryIG.getRowid());
        int cellid = 0;
        Cell cellName = row.createCell(cellid);
        cellName.setCellValue(categoryIG.getNameCategory());
        //mise à a jour des lastRowId de tous les niveau
        for (NiveauIG niveauIG : niveauIGS) {
            niveauIG.setRowid(categoryIG.getRowid() + 1);
        }
    }


    public void write(ElementIG elementIG) throws ExceptionSmartIJ {
        XSSFRow row;
        if (alreadyExistInCloneTab(elementIG)) {
            ElementIG fatherElement = getElementInCloneTab(elementIG.getNameElement());
            Objects.requireNonNull(fatherElement).addObject(elementIG.getvalueElement(2));
            System.out.println(fatherElement.obj());
            fatherElement.addObject(getActualLevelName());
            System.out.println("J'écris un FatherElement " + elementIG.getNameElement() + " : son rowid est " + fatherElement.getRowid());
            row = sheet.createRow(fatherElement.getRowid());
            writepre(row, fatherElement);
            for (int i = 0; i < fatherElement.numberOfObjects(); i += 2) {
                System.out.println("VALEUR DE I : " + i);
                Cell cell = row.createCell(Objects.requireNonNull(getLevel(fatherElement.getStringValueElement(i + 3))).getCellid());
                cell.setCellValue(fatherElement.getvalueElement(i + 2));
                System.out.println("fatherElement écrit > " + fatherElement.getvalueElement(i+2) + " < dans niveau = " + getLevel(fatherElement.getStringValueElement(i+3)).getLevelname());
            }
        } else {
            //Si aucun élément similaire en mémoire
            //il faut passer à la ligne suivante
            updateRowForAllLevel();
            elementIGSClone.add(elementIG);
            row = sheet.createRow(elementIG.getRowid());
            writepre(row, elementIG);
            Cell cellValue = row.createCell(Objects.requireNonNull(getLevel(elementIG.getNameLevel())).getCellid());

            cellValue.setCellValue(elementIG.getvalueElement(2));
            getLevel(actualLevel).setRowid(getLevel(actualLevel).getRowid() +1);

            System.out.println("J'écris un Element " + elementIG.getNameElement() + " : son rowid est " + elementIG.getRowid());
            System.out.println("le cellid du niveau de cette élément est " + getLevel(elementIG.getNameLevel()).getCellid());
        }
        System.out.println("(E) Le rowid du niveau actuel est : " + getLevel(actualLevel).getRowid());
        try {
            FileOutputStream out = new FileOutputStream(fileCalc);
            workbook.write(out);
            out.close();
            notifierObservateur();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notifierObservateur();
    }
    */

