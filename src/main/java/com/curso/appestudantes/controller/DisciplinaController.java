package com.curso.appestudantes.controller;

import com.curso.appestudantes.dao.DepartamentoDBDAO;
import com.curso.appestudantes.dao.DisciplinaDBDAO;
import com.curso.appestudantes.model.Departamento;
import com.curso.appestudantes.model.Disciplina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DisciplinaController extends Controller {
    private DisciplinaDBDAO disciplinaDBDAO;
    private List<Disciplina> disciplinas;
    private ObservableList<Disciplina> observableDisciplinas;

    @FXML
    private TextField cargaHorariaField;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNome;

    @FXML
    private TextField disciplinaIdField;

    @FXML
    private ChoiceBox<Departamento> departamentoSelector;

    @FXML
    private Button excluirButton;

    @FXML
    private MenuItem goToDepartamento;

    @FXML
    private MenuItem goToDisciplina;

    @FXML
    private MenuItem goToEstudante;

    @FXML
    private TextField nomeField;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<Disciplina> tabelaDisciplinas;

    @FXML
    void excluirButtonOnAction(ActionEvent event) {
        try {
            int disciplinaId = Integer.parseInt(disciplinaIdField.getText());

            Disciplina existente = disciplinaDBDAO.buscaPorId(disciplinaId);

            if (existente != null) {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmar Remoção");
                confirmAlert.setContentText("Deseja remover esta disciplina?");

                if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                    // Atualiza o registro no banco de dados
                    disciplinaDBDAO.removePorId(disciplinaId);
                    showAlert("Remoção realizada", "A disciplina foi excluido com sucesso.");
                }
            } else {
                showAlert("Erro", "Não existe uma disciplina cadastrada com esse ID.");
            }

            atualizarTabelaDepartamentos();

        } catch (NumberFormatException e) {
            showAlert("Erro de entrada", "Algum campo está errado.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro no banco de dados", "Não foi possível remover a disciplina.");
        }

    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        try {
            int disciplinaId = Integer.parseInt(disciplinaIdField.getText());
            String nome = nomeField.getText();
            int cargaHoraria = Integer.parseInt(cargaHorariaField.getText());
            int departamentoId = departamentoSelector.getSelectionModel().getSelectedItem().getDepartamentoId();

            Disciplina disciplina = new Disciplina(disciplinaId, nome, departamentoId, cargaHoraria);

            // Verifica se o estudante já existe no banco de dados
            Disciplina existente = disciplinaDBDAO.buscaPorId(disciplinaId);

            if (existente != null) {
                // Mostra popup de confirmação para atualizar o registro
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmar Atualização");
                confirmAlert.setHeaderText("Disciplina já cadastrado");
                confirmAlert.setContentText("Deseja atualizar os dados desta disciplina?");

                // Captura a resposta do usuário
                if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                    // Atualiza o registro no banco de dados
                    disciplinaDBDAO.atualiza(disciplina);
                    showAlert("Atualização realizada", "Os dados da disciplina foram atualizados com sucesso.");
                }
            } else {
                // Insere novo registro no banco de dados
                disciplinaDBDAO.insere(disciplina);
                showAlert("Cadastro realizado", "Nova disciplina cadastrada com sucesso.");
            }

            // Atualizar a tabela após a inserção/atualização
            atualizarTabelaDepartamentos();

        } catch (NumberFormatException e) {
            showAlert("Erro de entrada", "Algum campo está errado.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro no banco de dados", "Não foi possível salvar os dados da disciplina.");
        }
    }

    private void atualizarTabelaDepartamentos() {
        try {
            disciplinas = disciplinaDBDAO.listaTodas();
            observableDisciplinas.setAll(disciplinas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        disciplinaDBDAO = new DisciplinaDBDAO();

        // Configurar as colunas
        colId.setCellValueFactory(new PropertyValueFactory<>("disciplinaId"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Carregar disciplinas do banco de dados
        try {
            disciplinas = disciplinaDBDAO.listaTodas();

            observableDisciplinas = FXCollections.observableArrayList(disciplinas);
            tabelaDisciplinas.setItems(observableDisciplinas);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro no banco de dados.");
            alert.setContentText("Não foi possível recuperar os dados (disciplinas) do banco.");
            alert.showAndWait();
        }

        // Popular seleção de departamento.
        try {
            DepartamentoDBDAO departamentoDBDAO = new DepartamentoDBDAO();
            List<Departamento> departamentos = departamentoDBDAO.listaTodos();

            departamentoSelector.getItems().addAll(departamentos);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro no banco de dados.");
            alert.setContentText("Não foi possível recuperar os dados (departamentos) do banco.");
            alert.showAndWait();
        }

        // Listener para seleção de novo elemento na tabela
        tabelaDisciplinas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                disciplinaIdField.setText(String.valueOf(newSelection.getDisciplinaId()));
                nomeField.setText(newSelection.getNome());
                cargaHorariaField.setText(String.valueOf(newSelection.getCargaHoraria()));

                // Auto preencher campo departamento.
                for(Departamento d: departamentoSelector.getItems()){
                    if(d.getDepartamentoId() == newSelection.getDepartamentoId())
                        departamentoSelector.getSelectionModel().select(d);
                }
            }
        });

    }

    @FXML @Override
    void handleGoToDisciplina(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("Onde você quer chegar?");
        alert.setContentText("Você já está aqui");

        alert.showAndWait();
    }

}
