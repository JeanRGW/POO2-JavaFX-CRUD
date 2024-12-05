package com.curso.appestudantes.controller;

import com.curso.appestudantes.dao.DepartamentoDBDAO;
import com.curso.appestudantes.exceptions.InvalidInputException;
import com.curso.appestudantes.model.Departamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DepartamentoController extends Controller {
    private DepartamentoDBDAO departamentoDBDAO;
    private List<Departamento> departamentos;
    private ObservableList<Departamento> observableDepartamentos;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNome;

    @FXML
    private TextField departamentoIdField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField qntProfessoresField;

    @FXML
    private TableView<Departamento> tabelaDepartamentos;

    /*  1° Refatoração
     *   Autor: Matheus
     *   Remoção de referências desnecessárias para a interface.
     * */

    @FXML
    void excluirButtonOnAction(ActionEvent event) {
        try {
            int departamentoId = Integer.parseInt(departamentoIdField.getText());
            Departamento existente = departamentoDBDAO.buscaPorId(departamentoId);

            if (existente != null) {
                /*  9° Refatoração
                 *   Autor: Matheus
                 *   Simplificação de confirmação com herança
                 * */
                if (getConfirmacao("Confirmar Remoção", "Deseja remover este departamento?")) {
                    // Atualiza o registro no banco de dados
                    departamentoDBDAO.removePorId(departamentoId);
                    showAlert("Remoção realizada", "O departamento foi excluido com sucesso.");
                }
            } else {
                showAlert("Erro", "Não existe um departamento cadastrado com esse ID.");
            }

            atualizarTabelaDepartamentos();

        } catch (NumberFormatException e) {
            showAlert("Erro de entrada", "Algum campo está errado.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro no banco de dados", "Não foi possível remover o departamento.");
        }

    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        try {
            int departamentoId = Integer.parseInt(departamentoIdField.getText());
            int quantProfessores = Integer.parseInt(qntProfessoresField.getText());

            String nome = nomeField.getText();
            if(nome == null || nome.equals("")){
                throw new InvalidInputException("Por favor forneça um nome.");
            }

            // Valor a ser inserido e possível existente
            Departamento departamento = new Departamento(departamentoId, nome, quantProfessores);
            Departamento existente = departamentoDBDAO.buscaPorId(departamentoId);

            if (existente != null) {
                /*  9° Refatoração
                 *   Autor: Matheus
                 *   Simplificação de confirmação com herança
                 * */
                if (getConfirmacao("Confirmar Atualização", "Departamento já cadastrado, deseja atualizar os dados deste departamento?")) {
                    // Atualiza o registro no banco de dados
                    departamentoDBDAO.atualiza(departamento);
                    showAlert("Atualização realizada", "Os dados do departamento foram atualizados com sucesso.");
                }
            } else {
                // Insere novo registro no banco de dados
                departamentoDBDAO.insere(departamento);
                showAlert("Cadastro realizado", "Novo departamento cadastrado com sucesso.");
            }

            // Atualizar a tabela após a inserção/atualização
            atualizarTabelaDepartamentos();

        } catch (NumberFormatException e) {
            showAlert("Erro de entrada", "Algum campo está errado.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro no banco de dados", "Não foi possível salvar os dados do departamento.");
        } catch (InvalidInputException e) {
            showAlert("Entrada inválida", e.getMessage());
        }
    }

    private void atualizarTabelaDepartamentos() {
        try {
            departamentos = departamentoDBDAO.listaTodos();
            observableDepartamentos.setAll(departamentos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        departamentoDBDAO = new DepartamentoDBDAO();

        // Configurar as colunas
        colId.setCellValueFactory(new PropertyValueFactory<>("departamentoId")); // Ensure the property name matches
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome")); // Ensure the property name matches

        // Carregar dados do banco de dados
        try {
            departamentos = departamentoDBDAO.listaTodos();

            observableDepartamentos = FXCollections.observableArrayList(departamentos);
            tabelaDepartamentos.setItems(observableDepartamentos);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro no banco de dados.");
            alert.setContentText("Não foi possível recuperar os dados do banco.");
            alert.showAndWait();
        }

        tabelaDepartamentos.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        // Preencher campos de texto com dados do departamento selecionado
                        departamentoIdField.setText(String.valueOf(newSelection.getDepartamentoId()));
                        nomeField.setText(newSelection.getNome());
                        qntProfessoresField.setText(String.valueOf(newSelection.getQntProfessores()));
                    }
                });

    }

    @FXML @Override
    void handleGoToDepartamento(ActionEvent event) {
        showAlert("Onde você quer chegar?", "Você já está aqui.");
    }

}
