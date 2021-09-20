package smartij.model;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import smartij.exceptions.BoxDialogExceptionSmartIJ;
import smartij.exceptions.ExceptionSmartIJ;
import smartij.fabrics.FabricIdPostit;
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


    private File file;
    private File fileCalc;
    private int state;
    private String actualLevel;
    private String actualCategory;
    private final ArrayList<String> unitIGS = new ArrayList<>();
    private final ArrayList<NiveauIG> niveauIGS = new ArrayList<>();
    private final ArrayList<ElementIG> elementIGS = new ArrayList<>();
    private final ArrayList<CategoryIG> categoryIGS = new ArrayList<>();
    private final XSSFWorkbook workbook = new XSSFWorkbook();
    private final XSSFSheet sheet = workbook.createSheet();


    public SmartIG() {
        super();
        this.file = null;
        this.fileCalc = null;
        this.state = 0;
        this.unitIGS.add("u");
        this.unitIGS.add("m2");
        this.unitIGS.add("ml");
        this.unitIGS.add("m²");
    }

    public String getAllUnit(){
        return unitIGS.toString();
    }

    /**
     * change le nom de la catégorie actuelle
     * @param newcat la nouvelle catégorie
     */
    public void setActualCategory(String newcat){
        this.actualCategory = newcat;
    }

    /*
    retourne une catégorie dont le nom est donnée en paramètre
     */
    public CategoryIG getCategory(String namecat){
        for(CategoryIG c : categoryIGS){
            if(c.getNameCategory().equals(namecat)){
                return c;
            }
        }
        return null;
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
     * retourne le nom du niveau actuel
     *
     * @return le nom du niveau actuel
     */
    public String getActualLevelName() {
        return actualLevel;
    }



    /**
     * retourne une catégorie
     * @param name la nom de la catégorie
     * @return une catégorie
     */
    public CategoryIG getCatergorie(String name){
        for(CategoryIG categoryIG : categoryIGS){
            if(categoryIG.getNameCategory().equals(name))
                return categoryIG;
        }
        return null;
    }



    /**
     * retourne un niveau
     *
     * @param name le nom du niveau
     * @return un niveau
     */
    private NiveauIG getLevel(String name) {
        for (NiveauIG niveauIG : niveauIGS) {
            if (equals(niveauIG.getLevelname(), name))
                return niveauIG;
        }
        return null;
    }

    /**
     * recherche et retourne un élément en mémoire
     * @param name son npom
     * @return un Element
     */
    private ElementIG getElement(String name){
        for(ElementIG elementIG : elementIGS){
            if (Objects.equals(name, elementIG.getNameElement())) {
                return elementIG;
            }
        }
        return null;
    }




    /**
     * la fonction sert à verifier si un élement similaire existe déjà
     * en mémoire
     * @param elementIG l'élèment à vérifier
     * @return vrai sinon faux
     */
    private boolean alreadyExist(ElementIG elementIG) {
        for (ElementIG elementIG1 : elementIGS) {
            if (Objects.equals(elementIG1.getNameElement(), elementIG.getNameElement())) {
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
     * @return le nom d'un fichier
     */
    public String getFileName() {
        if (fileExist()) {
            return file.getName();
        } else {
            new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Vous n'avez pas ouvert de fichier").getMessage());
            return "";
        }
    }

    /**
     * retourne booléen selon la
     * validité d'une chaîne de caractères
     *
     * @param m la chaîne de caractères
     * @return true or false
     */
    private boolean isvalid(String m) {
        boolean b = m != null;
        return b && !m.isEmpty();
    }


    /**
     * retourne un booléen , si un fichier existe
     *
     * @return oui si existe, sinon non
     */
    private boolean fileExist() {
        return file != null;
    }


    /**
     * retourne le numéro de la dernière ligne libre d'un niveau
     *
     * @param nameLevel le nom du niveau
     * @return le nunméro de la dernièreligne
     */
    private int lastRowIdLevel(String nameLevel) {
        return Objects.requireNonNull(getLevel(nameLevel)).getRowid();
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
     * @param s le mot
     * @return le nombre de mot
     */
    private int numberOfWord(String s){
        return s.split(" ").length;
    }

    /**
     * la fonction déplace une interface de CategorieIG
     * @param categoryIG la catégorie
     * @param posx la position x
     * @param posy la position y
     */
    public void moveCategory(CategoryIG categoryIG, double posx, double posy){
        getCategory(categoryIG.getNameCategory()).move(posx,posy);
        notifierObservateur();
        System.out.println("nous avons" + categoryIGS.size());
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
            int res = jFileChooser.showSaveDialog(jFileChooser.getParent());
            if (res == JFileChooser.APPROVE_OPTION) {
                file = jFileChooser.getSelectedFile();
                createXlsx(file.getParentFile().getPath())
                ;
                state = 1;
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
     * La fonction write va écrire un élément dans le fichier .xlsx
     * @param elementIG l'éléments à écrire
     */
    public void write(ElementIG elementIG) {
        XSSFRow row;
        int cellid = 0;
        //Si un élément du même nom existe déjà :
        if (alreadyExist(elementIG)) {
            ElementIG fatherElement = getElement(elementIG.getNameElement());
            Objects.requireNonNull(fatherElement).addObject(elementIG.getvalueElement(2));
            fatherElement.addObject(getActualLevelName());
            System.out.println(fatherElement.obj());
            //on décrement la dernière ligne du niveau de FatherElement
            row = sheet.createRow(fatherElement.getRowid());

            Cell cellName = row.createCell(cellid);
            cellName.setCellValue(fatherElement.getNameElement());
            cellid++;
            Cell cellUnit = row.createCell(cellid);
            cellUnit.setCellValue(fatherElement.getUnitElement());


            for (int i = 0; i < fatherElement.numberOfObjects() ; i+=2) {
                Cell cell = row.createCell(Objects.requireNonNull(getLevel(fatherElement.getStringValueElement(i + 3))).getCellid());
                cell.setCellValue(fatherElement.getvalueElement(i+2));
            }

        } else {
            //Si aucun élément similaire en mémoire
            elementIGS.add(elementIG);
            row = sheet.createRow(elementIG.getRowid());
            System.out.println(elementIG.obj());
            Cell cellName = row.createCell(cellid);
            cellName.setCellValue(elementIG.getNameElement());
            cellid++;
            Cell cellUnit = row.createCell(cellid);
            cellUnit.setCellValue(elementIG.getUnitElement());
            Cell cellValue = row.createCell(Objects.requireNonNull(getLevel(actualLevel)).getCellid());
            cellValue.setCellValue(elementIG.getvalueElement(2));
            Objects.requireNonNull(getLevel(elementIG.getNameLevel())).setRowid(Objects.requireNonNull(getLevel(elementIG.getNameLevel())).getRowid() +1);
        }
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


    /**
     * Find the word in the file
     */
    public void search() {
        if(getnumberOfLevel() > 0) {
            if(isvalid(actualLevel)) {
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
                        new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Aucun mot correspondant").getMessage());
                    }
                }
            }else{
                new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Aucun niveau n'a été assigné").getMessage());
            }
        }else{
            new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Aucun niveau n'a été ajouté").getMessage());
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
     * la fonction met à jour le numéro de ligne du fichier
     */
    private void updateRows(){
        int maxLevel = 0;
        for(NiveauIG n : niveauIGS){
            if(lastRowIdLevel(n.getLevelname()) > maxLevel){
                maxLevel = lastRowIdLevel(n.getLevelname());
            }
        }
        for(NiveauIG n : niveauIGS){
            n.setRowid(maxLevel);
        }
    }


    /**
     * Changer de niveau implique le changement de colonne dans le tableau
     */
    public void changeLevel() {
        if (fileExist()) {
            if (getnumberOfLevel() > 0) {
                ChoiceDialog<Object> choiceDialog = new ChoiceDialog<>();
                ArrayList<String> nametabLevel = new ArrayList<>();
                for (NiveauIG niveauIG : niveauIGS) {
                    nametabLevel.add(niveauIG.getLevelname());
                }
                choiceDialog.getItems().addAll(nametabLevel);
                choiceDialog.showAndWait();
                if (choiceDialog.getResult() != null && !choiceDialog.getResult().toString().isEmpty()) {
                    this.actualLevel = choiceDialog.getResult().toString();
                    notifierObservateur();
                }
            } else {
                new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Vous n'avez ajouter aucun niveau").getMessage());
            }
        } else {
            new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Vous n'avez pas ouvert de fichier").getMessage());
        }
    }

    /**
     * ajouter une catégorie
     */
    public void addCategory() {
        if (fileExist()) {
            VueBoxDialogSmartIJ vueBoxDialogSmartIJ = new VueBoxDialogSmartIJ("Nouvelle catégorie","Ajouter une catégorie","ajouter");
            Optional<String> result = Optional.ofNullable(vueBoxDialogSmartIJ.getResult());
            result.ifPresent(name -> {
                if(!result.get().isEmpty()) {
                    int identifiant = FabricIdPostit.getInstance().getIdPostit();
                    categoryIGS.add(new CategoryIG(result.get(),identifiant,100,100));
                    notifierObservateur();
                }
            });
            notifierObservateur();
        }else{
            new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Vous n'avez pas ouvert de fichier").getMessage());
        }
    }




    public void addLevel() {
        if (fileExist()) {
            VueBoxDialogSmartIJ vueBoxDialogSmartIJ = new VueBoxDialogSmartIJ("Nouveau Niveau","Ajouter un niveau","Ajouter");
            Optional<String> result = Optional.ofNullable(vueBoxDialogSmartIJ.getResult());
            result.ifPresent(name -> {
                if(!result.get().isEmpty()) {
                    NiveauIG niveauIG = new NiveauIG(vueBoxDialogSmartIJ.getResult(), FabricRowsIdAndCellId.getInstance().getcellidLevel());
                    niveauIGS.add(niveauIG);
                    notifierObservateur();
                    //écrire le level dans le xlsx une fois ajouter
                    writeLevel();
                }
            });
        } else {
            new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Vous n'avez pas ouvert de fichier").getMessage());
        }
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
    private void suggestForUser(ArrayList<String> suggestion, String userWord){
        updateRows();
        if(isvalid(actualLevel)) {
            if (suggestion.size() > 1) {
                new VueBoxChoiceSmartIJ(suggestion, this, userWord);
            } else {
                ArrayList<String> suggest = new ArrayList<>(List.of(suggestion.get(0).split(" ")));
                write(createKnownElement(suggest,userWord));
            }
        }else{
            new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Vous n'avez pas défini le niveau actuel").getMessage());
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
    public void addUnit() {
        if (fileExist()) {
            VueBoxDialogSmartIJ vueBoxDialogSmartIJ = new VueBoxDialogSmartIJ("Les unités actuelles : \n" + unitIGS, "ajouter", "Ajouter unité");
            if (isvalid(vueBoxDialogSmartIJ.getAnswer()))
                unitIGS.add(vueBoxDialogSmartIJ.getAnswer());
                notifierObservateur();
        } else {
            new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Vous n'avez pas ouvert de fichier").getMessage());
        }
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

    public void removeAllUnit(){
        unitIGS.clear();
        notifierObservateur();
    }

    /**
     * Créer un élement à partir d'un tableau de suggestion et du mot de l'utilisateur
     *
     * @param suggest  un tableau de suggestion
     * @param userWord le mot entrée par l'utilisateur
     * @return element
     */
    public ElementIG createKnownElement(ArrayList<String> suggest, String userWord) {
        Object[] obj = new Object[4];
        obj[0] = userWord;
        obj[1] = suggest.get(0);
        obj[2] = suggest.get(1);
        obj[3] = getActualLevelName();
        ElementIG elementIG;
        elementIG = new ElementIG(obj, Objects.requireNonNull(getLevel(getActualLevelName())));
        getCatergorie(actualCategory).addElement(elementIG);
        return elementIG;
    }


    @Override
    public Iterator<CategoryIG> iterator() {
        return categoryIGS.iterator();
    }
}

