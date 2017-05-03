package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Configuration.deserializeConfiguration();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("scheduler.fxml"));
        primaryStage.setTitle("Scheduler");
        primaryStage.getIcons().add(new Image("file:icon.png"));
        Scene s = new Scene(root, 353, 682);
        s.setFill(Color.AZURE);

        primaryStage.setScene(s);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void stop(){
        System.out.println("Stage is closing");
        Configuration.serializeConfiguration();
        System.exit(0);
    }
}
