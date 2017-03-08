package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.image.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.*;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Main extends Application {

        private Stage window;
        private BorderPane layout;
        private TableView<TestFile> table;
        private TextField THField, TSField, TrHField,TrSField;

        @Override
        public void start(Stage primaryStage) throws Exception {
            primaryStage.setTitle("JavaFX Demo");

     

        /* create the table (for the center of the user interface) */
            table = new TableView<>();
            table.setEditable(true);

        /* create the table's columns */
            TableColumn<TestFile,String> sidColumn = null;
            sidColumn = new TableColumn<>("File Name");
            sidColumn.setMinWidth(300);
            sidColumn.setCellValueFactory(new PropertyValueFactory<>("StudentNumber"));

            TableColumn<TestFile,Float> AssignmentsColumn = null;
            AssignmentsColumn = new TableColumn<>("Actual Class");
            AssignmentsColumn.setMinWidth(200);
            AssignmentsColumn.setCellValueFactory(new PropertyValueFactory<>("Assignments"));



            TableColumn<TestFile,Float> MidtermColumn = null;
            MidtermColumn = new TableColumn<>(" Spam Probability");
            MidtermColumn.setMinWidth(200);
            MidtermColumn.setCellValueFactory(new PropertyValueFactory<>("Midterm"));


            table.getColumns().add(sidColumn);

            table.getColumns().add(AssignmentsColumn);

            table.getColumns().add(MidtermColumn);

        /* create an edit form (for the bottom of the user interface) */
            GridPane editArea = new GridPane();
            editArea.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            editArea.setVgap(10);
            editArea.setHgap(10);

            Label sidLabel = new Label("Path to Testing Ham:");
            editArea.add(sidLabel, 0, 0);
            TextField THField = new TextField();
            THField.setPromptText("Testing Ham");
            editArea.add(THField, 1, 0);

            Label ALabel = new Label("Path to Testing Spam:");
            editArea.add(ALabel, 0, 1);
            TextField TSField = new TextField();
            TSField.setPromptText("Testing Spam");
            editArea.add(TSField, 1, 1);

            Label mLabel = new Label("Path to Training Ham");
            editArea.add(mLabel, 0, 2);
            TextField TrHField = new TextField();
            TrHField.setPromptText("Training Ham");
            editArea.add(TrHField, 1, 2);

            Label FinExamLabel = new Label("Path to Training Spam");
            editArea.add(FinExamLabel, 0, 3);
            TextField TrSField = new TextField();
            TrSField.setPromptText("Training Spam");
            editArea.add(TrSField, 1, 3);

            Button addButton = new Button("Select");






        /* arrange all components in the main user interface */
            layout = new BorderPane();;
            layout.setCenter(table);
            layout.setBottom(editArea);

            Scene scene = new Scene(layout, 700, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
    }
    public static double SF(TreeMap<String,Double> probMap, String file){
            double sf=0.0;
            double counter = 0.0;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext())
            {
                String word = scanner.next();
                for (Map.Entry<String, Double>entry: probMap.entrySet()){
                    String key = entry.getKey();
                    double value;
                    if (word == key)
                    {
                        value = entry.getValue();
                        double value1 = 1 - value;
                        double val = Math.log(value);
                        double ValueMinusOne = Math.log(value1);
                        double finalValue = ValueMinusOne - val;
                        counter = counter + finalValue;

                    }
                }
            }
            sf = 1/(1 + (Math.pow(Math.E,counter)));



            return sf;
    }





    public static void main(String[] args) throws IOException {
        launch(args);
        File file = new File("/home/harshan/Desktop/Assignments/Assignment1/data/train/ham/");
        File file2 = new File("/home/harshan/Desktop/Assignments/Assignment1/data/train/spam/");
        Train Spamham;
        Spamham = new Train(file,file2);

        String file3 = "/home/harshan/Desktop/Assignments/Assignment1/data/test/ham/";

        double sfValue= SF(Spamham.TotalProb(), "/home/harshan/Desktop/Assignments/Assignment1/data/test/ham/00002.5a587ae61666c5aa097c8e866aedcc59");

        System.out.println("\n"+sfValue);
        TestFile SpamTest = new TestFile(file3,sfValue,"HAM");




    }
}
