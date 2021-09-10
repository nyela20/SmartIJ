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


public class SmartIG extends PatternObervable {


    private File file;
    private File fileCalc;
    private int state;
    private String actualLevel;
    private final ArrayList<String> tabUnite = new ArrayList<>();
    private final ArrayList<NiveauIG> tabLevel = new ArrayList<>();
    private final XSSFWorkbook workbook = new XSSFWorkbook();
    private final XSSFSheet sheet = workbook.createSheet();

    public SmartIG() {
        super();
        this.file = null;
        this.fileCalc = null;
        this.state = 0;
        this.tabUnite.add("u");
        this.tabUnite.add("m2");
        this.tabUnite.add("ml");
        this.tabUnite.add("m²");
    }



    public int numberOfLevel() {
        return tabLevel.size();
    }

    public boolean isvalid(String m){
        boolean b = m != null;
        return b && !m.isEmpty();
    }

    public boolean fileExist(){
        return file != null;
    }

    public String getActualLevelName() {
        return actualLevel;
    }

    public NiveauIG getLevel(String name){
        for(NiveauIG niveauIG : tabLevel){
            if(equals(niveauIG.getLevelname(),name))
                return niveauIG;
        }
        return null;
    }

    public int lastRowIdLevel(String nameLevel){
        return getLevel(nameLevel).getRowid();
    }


    public void changeLevel() {
        if (fileExist()) {
            if (numberOfLevel() > 0) {
                ChoiceDialog<Object> choiceDialog = new ChoiceDialog<>();
                ArrayList<String> nametabLevel = new ArrayList<>();
                for (NiveauIG niveauIG : tabLevel) {
                    nametabLevel.add(niveauIG.getLevelname());
                }
                choiceDialog.getItems().addAll(nametabLevel);
                choiceDialog.showAndWait();
                if (choiceDialog.getResult() != null && !choiceDialog.getResult().toString().isEmpty()) {
                    this.actualLevel = choiceDialog.getResult().toString();
                }
                notifierObservateur();
            } else {
                new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Vous n'avez ajouter aucun level").getMessage());
            }
        } else {
            new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Vous n'avez pas ouvert de fichier").getMessage());
        }
    }


    /**
     * Let the user choose a file
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

    public void addLevel() {
        if (fileExist()) {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setContentText("Ajouter un level");
            textInputDialog.setHeaderText("Nouveau level");
            textInputDialog.setTitle("Ajouter");
            Optional<String> result = textInputDialog.showAndWait();
            result.ifPresent(name -> {
                NiveauIG niveauIG = new NiveauIG(textInputDialog.getResult(), FabricRowsIdAndCellId.getInstance().getcellidLevel());
                tabLevel.add(niveauIG);
                //écrire le level dans le xlsx une fois ajouter
                writeLevel();
            });
        } else {
            new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Vous n'avez pas ouvert de fichier").getMessage());
        }
    }


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

    public void writeLevel() {
        XSSFRow row;
        row = sheet.createRow(2);
            for (NiveauIG obj : tabLevel) {
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




    public void write(ElementIG elementIG) {
        XSSFRow row;
        row = sheet.createRow(lastRowIdLevel(getActualLevelName()));
        System.out.println("level actuel : " + lastRowIdLevel(getActualLevelName()));
        int cellid = 0;
        cellid++;
        Cell cellName = row.createCell(cellid);
        cellName.setCellValue(elementIG.getNameElement());
        cellid++;
        Cell cellUnit = row.createCell(cellid);
        cellUnit.setCellValue(elementIG.getUnitElement());
        Cell cellValue = row.createCell(getLevel(actualLevel).getCellid());
        cellValue.setCellValue(elementIG.getvalueElement());
        try {
            FileOutputStream out = new FileOutputStream(fileCalc);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Find the word in the file
     */
    public void search(){
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
    }

    private void suggestForUser(ArrayList<String> suggestion, String userWord) {
        new VueBoxChoiceSmartIJ(suggestion, this, userWord);
    }


    /**
     * ajoute une unité
     */
    public void addUnit() {
        if (fileExist()) {
            VueBoxDialogSmartIJ vueBoxDialogSmartIJ = new VueBoxDialogSmartIJ("Les unités actuelles : \n" + tabUnite, "ajouter", "Ajouter unité");
            if(isvalid(vueBoxDialogSmartIJ.getAnswer()))
                tabUnite.add(vueBoxDialogSmartIJ.getAnswer());
        }
        else{
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
            for (String unit : tabUnite) {
                if (text.get(i).equals(unit)) {
                    return text.get(i);
                }
            }
        }
        return ".";
    }

    /**
     * renvoie une suggestion de chiffre
     *
     * @param text     le fichier txt
     * @param userword le mot cké
     * @return un tableau contenant les suggestions
     */
    public ArrayList<String> suggestWord(ArrayList<String> text, String userword) {
        ArrayList<String> suggestion = new ArrayList<>();
        int index = searchWord(text, userword, 0);
        if (index > 0) {
            boolean stop = false;
            while (!stop) {
                index = searchFirstNumberFrom(text, index + 1);
                if (searchWord(text, userword, index) == -1) {
                    stop = !estUnEntier(text.get(index + 1));
                }
                suggestion.add(findUnit(text,index) + " " +  text.get(index));
            }
        }
        return suggestion;
    }

    public int searchFirstNumberFrom(ArrayList<String> text, int index) {
        for (int i = index; i < text.size(); i++) {
            if (estUnEntier(text.get(i)))
                return i;
        }
        return -1;
    }


    /**
     * La fonction retourne vrai si le String est composé uniquement d'entier
     * de [0 à 9]*, sinon faux
     *
     * @param chaine la chaine à verifier
     * @return vrai si c'est un entier, sinon faux
     */
    public boolean estUnEntier(String chaine) {
        try {
            Integer.parseInt(chaine);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void autoCorrect(ArrayList<String> text) {
        //autocorrection séparation mot
        for (String word : text) {
            for (String unit : this.tabUnite) {
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
        //autocorrection syntaxique

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

    public int getState() {
        return state;
    }


    public String getFileName() {
        if(fileExist()) {
            return file.getName();
        }else{
            new BoxDialogExceptionSmartIJ(new ExceptionSmartIJ("Vous n'avez pas ouvert de fichier").getMessage());
            return "";
        }
    }
}
