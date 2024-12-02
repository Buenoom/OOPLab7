package com.goles.lab778;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    public int accessServer(Matrix matrix) {
        try {
            Socket client = new Socket("localhost", 7777);
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            try {
                out.writeObject(matrix);
                Integer result = null;
                while (result == null) {
                    result = (Integer)in.readObject();
                }
                return result;
            }
            catch (ClassNotFoundException ex) {
                System.err.println(ex);
            }
            finally {
                client.close();
                out.close();
                in.close();
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
        return 0;
    }
    
    public void outputMatrix(Matrix matrix, TextArea area) {
        area.setText("");
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getColCount(); j++) {
                String outputElement = String.valueOf(matrix.getElement(i, j));
                int spaces = 8 - outputElement.length();   // Отступ после вывода элемента (чтобы ничто никуда не уехало)
                area.appendText(outputElement + " ".repeat(spaces));
            }
            area.appendText("\n");
        }
    }
    
    @Override
    public void start(Stage stage) throws InterruptedException, ClassNotFoundException {
        stage.setTitle("Лабораторная работа №7");
        
        TextField rowsField = new TextField();
        rowsField.setPromptText("Число строк");
        rowsField.setFocusTraversable(false);
        rowsField.setLayoutX(10);
        rowsField.setLayoutY(10);
        
        TextField colsField = new TextField();
        colsField.setPromptText("Число столбцов");
        colsField.setFocusTraversable(false);
        colsField.setLayoutX(10);
        colsField.setLayoutY(40);
        
        TextArea matrixTextArea = new TextArea();
        matrixTextArea.setPrefHeight(300);
        matrixTextArea.setPrefWidth(300);
        matrixTextArea.setEditable(false);
        matrixTextArea.setLayoutX(180);
        matrixTextArea.setLayoutY(10);
        
        TextField outputField = new TextField();
        outputField.setPromptText("Результат");
        outputField.setFocusTraversable(false);
        outputField.setEditable(false);
        outputField.setLayoutX(10);
        outputField.setLayoutY(285);
        
        Button createMatrixBtn = new Button("Создать");
        createMatrixBtn.setLayoutX(10);
        createMatrixBtn.setLayoutY(70);
        
        createMatrixBtn.setOnAction((ActionEvent event) -> {
            try {
                int rowCount = Integer.parseInt(rowsField.getText());
                int colCount = Integer.parseInt(colsField.getText());
                if (rowCount < 0 | colCount < 0) throw new IllegalArgumentException();
                Matrix matrix = new Matrix(rowCount, colCount);
                outputMatrix(matrix, matrixTextArea);
                int result = accessServer(matrix);
                outputField.setText(String.valueOf(result));
            }
            catch (NumberFormatException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Некорректный ввод");
                errorAlert.setContentText("Число строк/столбцов введено некорректно. Убедитесь, что оно целое и не содержит посторонних символов.");
                errorAlert.showAndWait();
            }
            catch (IllegalArgumentException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Некорректный ввод");
                errorAlert.setContentText("Число строк/столбцов не может быть отрицательным.");
                errorAlert.showAndWait();
            }
        });
        
        Pane root = new Pane(createMatrixBtn, rowsField, colsField, matrixTextArea, outputField);
        
        Scene scene = new Scene(root, 500, 350);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        launch();
    }

}