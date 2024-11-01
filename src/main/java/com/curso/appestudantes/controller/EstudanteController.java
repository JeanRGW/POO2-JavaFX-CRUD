package com.curso.appestudantes.controller;

import com.curso.appestudantes.dao.EstudanteDBDAO;
import com.curso.appestudantes.model.Estudante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EstudanteController {
    EstudanteDBDAO estudanteDBDAO;
    List<Estudante> estudantes;


    @FXML
    public void initialize() {
        estudanteDBDAO = new EstudanteDBDAO();
        try {
            estudantes = estudanteDBDAO.listaTodos();
        } catch (SQLException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro no banco de dados.");
            alert.setContentText("Não foi possível recuperar os dados do banco.");

            alert.showAndWait();
        }


        // Carregar dados na tabela
    }

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNome;

    @FXML
    private TextField cpfField;

    @FXML
    private TextField dataNascField;

    @FXML
    private TextField estudanteIdField;

    @FXML
    private MenuItem goToDepartamento;

    @FXML
    private MenuItem goToDisciplina;

    @FXML
    private MenuItem goToEstudante;

    @FXML
    private Button newButton;

    @FXML
    private TextField nomeField;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<Estudante> tabelaEstudantes;

    @FXML
    void handleGoToDepartamento(ActionEvent event) {
        swapStage("departamento.fxml");
    }

    @FXML
    void handleGoToDisciplina(ActionEvent event) {
        swapStage("disciplina.fxml");
    }

    @FXML
    void handleGoToEstudante(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Onde você quer chegar?");
        alert.setContentText("Você já está aqui");

        alert.showAndWait();
    }

    void swapStage(String viewFile) {
        try {
            // Attempt to load the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/curso/appestudantes/view/" + viewFile));
            Scene newScene = new Scene(loader.load(), 600, 400);

            // Obtain the current stage
            Stage stage = (Stage) saveButton.getScene().getWindow();

            // Set the new scene and show the stage
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro ao trocar cena");
            alert.setContentText("Não foi possível trocar a cena.");

            alert.showAndWait();
        }
    }

}
