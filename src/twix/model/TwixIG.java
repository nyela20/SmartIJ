package twix.model;

import javafx.scene.control.TextInputDialog;
import twix.exceptions.ExceptionTwix;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class TwixIG extends SujetObserve{



    private File file;
    private int state;
    private ArrayList<String> tabUnite = new ArrayList<>();

    public TwixIG(){
        super();
        this.file = null;
        this.state = 0;
        this.tabUnite.add("u");
        this.tabUnite.add("m2");
        this.tabUnite.add("ml");
    }

    /**
     * Let the user choose a file
     */
    public void open(){
        JFileChooser jFileChooser = new JFileChooser("src/twix/sources");
        jFileChooser.setMultiSelectionEnabled(false);
        int res = jFileChooser.showSaveDialog(jFileChooser.getParent());
        if(res == JFileChooser.APPROVE_OPTION){
            this.file = jFileChooser.getSelectedFile();
            this.state = 1;
        }
        notifierObservateur();
    }

    /**
     * Find the word in the file
     */
    public void search(){
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText("Choisir un élément");
        textInputDialog.showAndWait();
        String userWord = textInputDialog.getEditor().getText();
        String resultSplit = read().toString().replaceAll(" ","\n");
        ArrayList<String> text = new ArrayList<>();
        Collections.addAll(text,resultSplit.split("\n"));
        autoCorrect(text);
        System.out.println(text + "\n\n");
        suggest(text,userWord);
    }

    /**
     * renvoie une suggestion de chiffre
     * @param text le fichier txt
     * @param userword le mot cké
     * @return un tableau contenant les suggestions
     */
    public ArrayList<String> suggest(ArrayList<String> text, String userword) {
        ArrayList<String> suggestion = new ArrayList<>();
        int index = searchWord(text, userword, 0);
        boolean stop = false;
        while(!stop) {
            index = searchFirstNumberFrom(text, index + 1);
            if(searchWord(text,userword,index) == -1) {
                stop = true;
                if(estUnEntier(text.get(index+1).replaceAll(" ", ""))){
                    stop = false;
                }
            }
            suggestion.add(text.get(index).replaceAll(" ",""));
        }
        System.out.println(suggestion);
        return suggestion;
    }

    public int searchFirstNumberFrom(ArrayList<String> text,int index){
        for(int i=index; i< text.size(); i++){
            if(estUnEntier(text.get(i).replaceAll(" ", "")))
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
        text.clear();
        Collections.addAll(text,tmp.split("\n"));
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
            filereader = new FileReader("src/twix/sources/"+file.getName());
            bufferedreader = new BufferedReader(filereader);
            String strCurrentLine;
            while ((strCurrentLine = bufferedreader.readLine()) != null) {
                stringBuilder.append(strCurrentLine);
                //System.out.println(strCurrentLine);
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
