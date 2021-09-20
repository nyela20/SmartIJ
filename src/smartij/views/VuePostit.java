package smartij.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import smartij.listenerDnd.ListenerStartDrag;
import smartij.model.CategoryIG;
import smartij.model.ElementIG;
import smartij.model.SmartIG;



import static jdk.nashorn.internal.objects.Global.Infinity;


public class VuePostit extends BorderPane implements PatternObserver {

    private double prefHeight;
    private double prefWidth;
    private final String namePostit;
    private final CategoryIG categoryIG;
    private final Label label = new Label();
    private final Button button = new Button("+");
    private final VBox vbox = new VBox();
    private final SmartIG smartIG;


    public VuePostit(String namePostit, CategoryIG categoryIG, SmartIG smartIG) {
        this.namePostit = namePostit;
        this.smartIG = smartIG;
        this.prefHeight = categoryIG.getHeight();
        this.prefWidth = categoryIG.getWidth();
        this.categoryIG = categoryIG;
        init();
        button.setOnMousePressed(v -> {
            smartIG.setActualCategory(this.namePostit);
            smartIG.search();
        });
        this.setOnDragDetected(new ListenerStartDrag(this,String.valueOf(categoryIG.getNameCategory())));
    }

    private void addElementOnVbox(){
        for(ElementIG elementIG : categoryIG) {
            vbox.getChildren().add(new Label(elementIG.obj().toString()));
        }
    }

    private void init(){
        this.setLayoutX(categoryIG.getPosx()); this.setLayoutY(categoryIG.getPosy()); initLabel();initButton();initVbox();
        setMaxHeight(-Infinity);setMaxWidth(-Infinity); setMinHeight(-Infinity);setMinWidth(-Infinity);
        setPrefHeight(prefHeight);setPrefWidth(prefWidth); setTop(label);setBottom(button);setCenter(vbox);
    }

    private void initVbox(){
        vbox.setId("vboxPostit");vbox.setAlignment(Pos.CENTER);
        //vbox.setPrefHeight(prefHeight);vbox.setPrefWidth(143);
        addElementOnVbox();
    }

    private void initButton(){
        button.setId("buttonPostit");button.setMnemonicParsing(false);button.setPrefHeight(26.0);button.setPrefWidth(prefWidth);
        button.setStyle("-fx-background-color : black;"); button.setStyle("-fx-text-fill : white;");button.setAlignment(Pos.CENTER);
    }


    private void initLabel(){
        label.setText(categoryIG.getNameCategory());label.setId("LabelPostit");
        label.setPrefHeight(16.0);label.setMaxWidth(prefWidth);
        label.setScaleShape(false);label.setStyle("fx-background-color: yellow;");
        label.setUnderline(true);label.setFont(Font.font("Serif Bold", FontWeight.BOLD, FontPosture.REGULAR,12));
        label.setAlignment(Pos.CENTER);
    }


    @Override
    public void reagir() { }
}
