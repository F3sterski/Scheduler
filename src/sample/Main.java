package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        deserializeConfiguration();
        Parent root = FXMLLoader.load(getClass().getResource("scheduler.fxml"));
        primaryStage.setTitle("Scheduler");

        Scene s = new Scene(root, 353, 682);
        s.setFill(Color.AZURE);
        primaryStage.setScene(s);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Controller c = new Controller();
        launch(args);

    }
    @Override
    public void stop(){
        System.out.println("Stage is closing");
        serializeConfiguration();
    }

    private void serializeConfiguration(){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("configuration.azc");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Configuration.getInstance());
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in configuration.azc");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    private void deserializeConfiguration(){
        try {
            FileInputStream fileIn = new FileInputStream("configuration.azc");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Configuration.setInstanceOnLoad((Configuration) in.readObject());
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            System.out.println("Configuration class not found");
            c.printStackTrace();
            return;
        }
    }
}
