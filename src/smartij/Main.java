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
        primaryStage.setScene(new Scene(root,700,700));
        primaryStage.show();
    }

    public static void  main(String[] args){
        launch(args);
    }
}
