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
    private ObservableList<Estudante> observableEstudantes;

    @FXML
    private TableColumn<Estudante, Integer> colId;

    @FXML
    private TableColumn<Estudante, String> colNome;

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
    public void initialize() {
        estudanteDBDAO = new EstudanteDBDAO();

        // Setting up columns
        colId.setCellValueFactory(new PropertyValueFactory<>("estudanteId")); // Ensure the property name matches
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));       // Ensure the property name matches

        // Load data from the database
        try {
            estudantes = estudanteDBDAO.listaTodos();

            System.out.println(estudantes);

            // Use ObservableList directly from Estudante
            observableEstudantes = FXCollections.observableArrayList(estudantes);
            tabelaEstudantes.setItems(observableEstudantes);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro no banco de dados.");
            alert.setContentText("Não foi possível recuperar os dados do banco.");
            alert.showAndWait();
        }

        tabelaEstudantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // Logica item selecionado
        });

    }

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/curso/appestudantes/view/" + viewFile));
            Scene newScene = new Scene(loader.load(), 600, 437);
            Stage stage = (Stage) saveButton.getScene().getWindow();
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
