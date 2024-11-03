package com.curso.appestudantes.controller;

import com.curso.appestudantes.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    void swapStage(String viewFile) {
        try {
            // Carrega cena do arquivo.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/curso/appestudantes/view/" + viewFile));
            Scene newScene = new Scene(loader.load(), 600, 437);

            // Atribui a cena apra a janela.
            App.stage.setScene(newScene);
            App.stage.show();
        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro ao trocar cena");
            alert.setContentText("Não foi possível trocar a cena.");

            alert.showAndWait();
        }
    }

    @FXML
    void handleGoToDisciplina(ActionEvent event) {
        swapStage("disciplina.fxml");
    }

    @FXML
    void handleGoToEstudante(ActionEvent event) {
        swapStage("estudante.fxml");
    }

    @FXML
    void handleGoToDepartamento(ActionEvent event) {
        swapStage("departamento.fxml");
    }

    // Utilitário para exibir mensagens
    void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
