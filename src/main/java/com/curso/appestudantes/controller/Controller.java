package com.curso.appestudantes.controller;

import com.curso.appestudantes.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class Controller {
    private void swapStage(String viewFile) {
        try {
            // Carrega cena do arquivo.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/curso/appestudantes/view/" + viewFile));
            Scene newScene = new Scene(loader.load(), 600, 430);
            newScene.getStylesheets().add(getClass().getResource("/com/curso/appestudantes/view/styles.css").toExternalForm());

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

    boolean getConfirmacao(String titulo, String mensagem) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(titulo);
        confirmAlert.setContentText(mensagem);
        return confirmAlert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
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
