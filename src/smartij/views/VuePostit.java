package smartij.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import smartij.exceptions.ExceptionSmartIJ;
import smartij.listenerDnd.ListenerStartDrag;
import smartij.model.CategoryIG;
import smartij.model.ElementIG;
import smartij.model.SmartIG;


import static jdk.nashorn.internal.objects.Global.Infinity;


public class VuePostit extends BorderPane implements PatternObserver {

    private final double prefHeight;
    private final double prefWidth;
    private final String namePostit;
    private final CategoryIG categoryIG;
    private final Label label = new Label();
    private final Button button = new Button("+");
    private final VBox vbox = new VBox();


    public VuePostit(String namePostit, CategoryIG categoryIG, SmartIG smartIG) {
        if(!categoryIG.isSelected())
            this.setId("postit_Not_Selected");
        else
            this.setId("postit_Selected");


        this.namePostit = namePostit;
        this.prefHeight = categoryIG.getHeight();
        this.prefWidth = categoryIG.getWidth();
        this.categoryIG = categoryIG;
        init();
        button.setOnMousePressed(v -> {
            try {
                smartIG.setActualCategory(this.namePostit);
                smartIG.search();
            } catch (ExceptionSmartIJ e) {
                System.err.println(e.getMessage());
            }
        });
        this.setOnDragDetected(new ListenerStartDrag(this, String.valueOf(categoryIG.getNameCategory())));
        this.setOnMouseClicked((v) -> {smartIG.changeCatState(categoryIG);
        });
    }

    private void addElementOnVbox(){
        vbox.getChildren().clear();
        for(ElementIG elementIG : categoryIG) {
            vbox.getChildren().add(new Label(elementIG.obj().toString()));
        }
    }

    private void init(){
        this.setLayoutX(categoryIG.getPosx()); this.setLayoutY(categoryIG.getPosy()); initVbox();initLabel();initButton();
        setMaxHeight(-Infinity);setMaxWidth(-Infinity); setMinHeight(prefHeight); setMinWidth(prefWidth);
        setTop(label); setBottom(button); setCenter(vbox);
    }

    private void initVbox(){
        vbox.setId("vboxPostit");vbox.setAlignment(Pos.CENTER);
        vbox.autosize();
        addElementOnVbox();
    }

    private void initButton(){
        button.setId("buttonPostit");button.setMnemonicParsing(false);button.setPrefHeight(20);button.setPrefWidth(prefWidth);
        button.setAlignment(Pos.CENTER);
    }


    private void initLabel(){
        label.setText(categoryIG.getNameCategory());label.setId("LabelPostit");
        label.setPrefHeight(20);label.setPrefWidth(prefWidth);
        label.setScaleShape(false);
        label.setUnderline(true);label.setFont(Font.font("Serif Bold", FontWeight.BOLD, FontPosture.REGULAR,12));
        label.setAlignment(Pos.CENTER);
    }


    @Override
    public void reagir() { }
}
