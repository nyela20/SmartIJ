package twix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twix.views.VueMenu;
import twix.views.VueTwix;
import twix.model.TwixIG;

import javax.swing.*;
import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        TwixIG twixIG = new TwixIG();
        BorderPane root = new BorderPane();

        FXMLLoader loaderTwix = new FXMLLoader();
        loaderTwix.setLocation(getClass().getResource("fxml/twix.fxml"));
        loaderTwix.setControllerFactory(iC-> new VueTwix(twixIG, root));

        FXMLLoader loaderMenu = new FXMLLoader();
        loaderMenu.setLocation(getClass().getResource("fxml/menu.fxml"));
        loaderMenu.setControllerFactory(iC -> new VueMenu(twixIG));


        root.setCenter(loaderTwix.load());
        root.setTop(loaderMenu.load());
        primaryStage.setTitle("TwixIG");
        primaryStage.setScene(new Scene(root,500,500));
        primaryStage.show();

    }

    public static void  main(String[] args){
        launch(args);
    }
}
