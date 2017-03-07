package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }





    public static void main(String[] args) {
        launch(args);
        File file = new File("\\Users\\100585195\\Desktop\\Assignment1\\data\\test\\ham");
        File file2 = new File("\\Users\\100585195\\Desktop\\Assignment1\\data\\test\\spam");
        Train Spamham = new Train(file,file2);
    }
}
