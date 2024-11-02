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
import java.sql.Date;
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
    void saveButtonOnAction(ActionEvent event) {
        try {
            int estudanteId = Integer.parseInt(estudanteIdField.getText());
            String nome = nomeField.getText();
            String cpf = cpfField.getText();
            Date dataNasc = Date.valueOf(dataNascField.getText());

            Estudante estudante = new Estudante(estudanteId, dataNasc, cpf, nome);

            // Verifica se o estudante já existe no banco de dados
            Estudante existente = estudanteDBDAO.buscaPorId(estudanteId);

            if (existente != null) {
                // Mostra popup de confirmação para atualizar o registro
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmar Atualização");
                confirmAlert.setHeaderText("Estudante já cadastrado");
                confirmAlert.setContentText("Deseja atualizar os dados deste estudante?");

                // Captura a resposta do usuário
                if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                    // Atualiza o registro no banco de dados
                    estudanteDBDAO.atualiza(estudante);
                    showAlert("Atualização realizada", "Os dados do estudante foram atualizados com sucesso.");
                }
            } else {
                // Insere novo registro no banco de dados
                estudanteDBDAO.insere(estudante);
                showAlert("Cadastro realizado", "Novo estudante cadastrado com sucesso.");
            }

            // Atualizar a tabela após a inserção/atualização
            atualizarTabelaEstudantes();

        } catch (NumberFormatException e) {
            showAlert("Erro de entrada", "O ID do estudante deve ser um número.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro no banco de dados", "Não foi possível salvar os dados do estudante.");
        }
    }

    // Método auxiliar para mostrar alertas informativos
    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Método para atualizar a tabela de estudantes após inserir/atualizar
    private void atualizarTabelaEstudantes() {
        try {
            estudantes = estudanteDBDAO.listaTodos();
            observableEstudantes.setAll(estudantes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        estudanteDBDAO = new EstudanteDBDAO();

        // Configurar as colunas
        colId.setCellValueFactory(new PropertyValueFactory<>("estudanteId")); // Ensure the property name matches
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));       // Ensure the property name matches

        // Carregar dados do banco de dados
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
            if (newSelection != null) {
                // Preencher campos de texto com dados do estudante selecionado
                estudanteIdField.setText(String.valueOf(newSelection.getEstudanteId()));
                nomeField.setText(newSelection.getNome());
                cpfField.setText(newSelection.getCpf());
                dataNascField.setText(newSelection.getDataNascimento().toString());
            }
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
