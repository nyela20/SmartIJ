package smartij;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import smartij.views.VueMenuSmartIJ;
import smartij.views.VueSmartIJ;
import smartij.model.SmartIG;

import java.io.IOException;

public class Main extends Application {

    int LargeurPanneauPrincipale = 1000;
    int HauteurPanneauPrincipale = 1000;


    @Override
    public void start(Stage primaryStage) throws IOException {
        SmartIG smartIG = new SmartIG();
        BorderPane root = new BorderPane();

        FXMLLoader loaderTwix = new FXMLLoader();
        loaderTwix.setLocation(getClass().getResource("fxml/smart.fxml"));
        loaderTwix.setControllerFactory(iC-> new VueSmartIJ(smartIG, root));

        FXMLLoader loaderMenu = new FXMLLoader();
        loaderMenu.setLocation(getClass().getResource("fxml/menu.fxml"));
        loaderMenu.setControllerFactory(iC -> new VueMenuSmartIJ(smartIG));


        root.setCenter(loaderTwix.load());
        root.setTop(loaderMenu.load());
        primaryStage.setTitle("SmartIJ");
        Scene scene = new Scene(root, LargeurPanneauPrincipale, HauteurPanneauPrincipale);
        scene.getStylesheets().add("smartij/sources/css/stylesheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void  main(String[] args){
        launch(args);
    }
}
