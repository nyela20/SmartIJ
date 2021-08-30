package twix.model;

import javafx.scene.control.TextInputDialog;
import twix.exceptions.ExceptionTwix;
import twix.views.VueBoite;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;


public class TwixIG extends SujetObserve{



    private File file;
    private File fileCalc;
    private int state;
    private ArrayList<String> tabUnite = new ArrayList<>();

    public TwixIG(){
        super();
        this.file = null;
        this.fileCalc = null;
        this.state = 0;
        this.tabUnite.add("u");
        this.tabUnite.add("m2");
        this.tabUnite.add("ml");
    }

    /**
     * Let the user choose a file
     */
    public void open() {
        try {
            JFileChooser jFileChooser = new JFileChooser("src/twix/tools/page/pagetxt");
            jFileChooser.setMultiSelectionEnabled(false);
            int res = jFileChooser.showSaveDialog(jFileChooser.getParent());
            if (res == JFileChooser.APPROVE_OPTION) {
                file = jFileChooser.getSelectedFile();
                createXlsx(file.getParentFile().getPath())
                ;state = 1;
            } else {
                jFileChooser.cancelSelection();
            }
            notifierObservateur();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void createXlsx(String pathname){
        try {
            if(!Files.exists(Paths.get(pathname + "/file.xlsx"))){
                Path file = Files.createFile(Paths.get(pathname + "/file.xlsx"));
                fileCalc = file.toFile();
                writeXlsx(".");
            }else{
                Files.delete(Paths.get(pathname + "/file.xlsx"));
                createXlsx(pathname);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writeXlsx(String write){
        FileWriter flot;
        BufferedWriter flotFiltre;
        try {
            flot = new FileWriter(fileCalc);
            flotFiltre = new BufferedWriter(flot);
            flotFiltre.write(write);
            flotFiltre.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Find the word in the file
     */
    public void search() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText("Choisir un élément");
        textInputDialog.showAndWait();
        String userWord = textInputDialog.getEditor().getText();
        String resultSplit = read().toString().replaceAll(" ", "\n");
        ArrayList<String> text = new ArrayList<>();
        Collections.addAll(text, resultSplit.split("\n"));
        autoCorrect(text);
        //System.out.println(text + "\n\n");
        if (!userWord.isEmpty() && !text.isEmpty()) {
            if (!suggestWord(text, userWord).isEmpty()) {
                suggestForUser(suggestWord(text, userWord), userWord);
            }else{
                new ExceptionTwix("Aucun mot correspondant");
            }
        }
    }

    private void suggestForUser(ArrayList<String> suggestion, String userWord){
        VueBoite vueBoite = new VueBoite(suggestion,this,userWord);
    }

    /**
     * renvoie une suggestion de chiffre
     * @param text le fichier txt
     * @param userword le mot cké
     * @return un tableau contenant les suggestions
     */
    public ArrayList<String> suggestWord(ArrayList<String> text, String userword) {
        ArrayList<String> suggestion = new ArrayList<>();
        int index = searchWord(text, userword, 0);
        if(index > 0) {
            boolean stop = false;
            while (!stop) {
                index = searchFirstNumberFrom(text, index + 1);
                if (searchWord(text, userword, index) == -1) {
                    stop = true;
                    if (estUnEntier(text.get(index + 1))) {
                        stop = false;
                    }
                }
                suggestion.add(text.get(index));
            }
        }
        return suggestion;
    }

    public int searchFirstNumberFrom(ArrayList<String> text,int index){
        for(int i=index; i< text.size(); i++){
            if(estUnEntier(text.get(i)))
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

    private void autoCorrect(ArrayList<String> text){
        //autocorrection séparation mot
        for(String word : text){
            for(String unit : this.tabUnite) {
                StringBuilder s = new StringBuilder(word);
                if (word.startsWith(unit)) {
                    s.replace(0, 0, unit+"\n");
                    for(int i=0; i< unit.length(); i++) s.deleteCharAt(unit.length() + 1);
                    text.set(text.indexOf(word),s.toString());
                }
            }
        }
        String tmp = text.toString().replaceAll(",", "\n");
        String tmp2 = tmp.replaceAll(" ","");
        text.clear();
        Collections.addAll(text,tmp2.split("\n"));
        //autocorrection syntaxique

    }


    /**
     * Search word, return the index of the first letter
     */

    private int searchWord(ArrayList<String> text,String userword, int start){
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
    private StringBuilder read(){
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedreader = null;
        FileReader filereader = null;
        try {
            filereader = new FileReader(file.getPath());
            if(file.length() != 0) {
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
    private static boolean equals(String a, String b){
        if(a.length() == 0) return false;
        if(b.length() < a.length()) return false;
        for(int i=0; i < a.length() ; i++){
            if(a.charAt(i) != b.charAt(i))
                return false;
        }
        return true;
    }

    public int getState() {
        return state;
    }


    public String getFileName(){
        return file.getName();
    }
}
