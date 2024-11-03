package com.curso.appestudantes;

import com.curso.appestudantes.dao.InitDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/estudante.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 430);
        scene.getStylesheets().add(getClass().getResource("/com/curso/appestudantes/view/styles.css").toExternalForm());
        stage.setTitle("App Estudantes");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        try {
            InitDB.init();

            launch();
        } catch (SQLException e) {
            System.out.println("Erro grave no banco de dados, verificar login e existência do banco");
        }

    }
}