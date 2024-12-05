package com.curso.appestudantes.controller;

import com.curso.appestudantes.dao.DepartamentoDBDAO;
import com.curso.appestudantes.dao.DisciplinaDBDAO;
import com.curso.appestudantes.exceptions.InvalidInputException;
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
import java.util.ArrayList;

public class DisciplinaController extends Controller {
    private DisciplinaDBDAO disciplinaDBDAO;
    private List<Disciplina> disciplinas;
    private ObservableList<Disciplina> observableDisciplinas;

    @FXML
    private TextField cargaHorariaField;

    @FXML
    private TableColumn<Disciplina, Integer> colId;

    @FXML
    private TableColumn<Disciplina, String> colNome;

    @FXML
    private TextField disciplinaIdField;

    @FXML
    private ChoiceBox<Departamento> departamentoSelector;

    @FXML
    private TextField nomeField;

    @FXML
    private TableView<Disciplina> tabelaDisciplinas;

    /*  2° Refatoração
     *   Autor: Matheus
     *   Remoção de referências desnecessárias para a interface.
     * */

    @FXML
    void excluirButtonOnAction(ActionEvent event) {
        try {
            int disciplinaId = Integer.parseInt(disciplinaIdField.getText());

            Disciplina existente = disciplinaDBDAO.buscaPorId(disciplinaId);

            if (existente != null) {
                /*  10° Refatoração
                 *   Autor: Guilherme
                 *   Simplificação de confirmação com herança
                 * */
                if (getConfirmacao("Confirmar Remoção", "Deseja remover esta disciplina?")) {
                    // Atualiza o registro no banco de dados
                    disciplinaDBDAO.removePorId(disciplinaId);
                    showAlert("Remoção realizada", "A disciplina foi excluido com sucesso.");
                }
            } else {
                showAlert("Erro", "Não existe uma disciplina cadastrada com esse ID.");
            }

            atualizarTabelaDisciplinas();

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

            if(departamentoSelector.getValue() == null) {
                throw new InvalidInputException("Selecione algum departamento");
            }
            int departamentoId = departamentoSelector.getValue().getDepartamentoId();

            int cargaHoraria = Integer.parseInt(cargaHorariaField.getText());
            if(cargaHoraria < 1) {
                throw new InvalidInputException("Carga horária inválida");
            }

            String nome = nomeField.getText();
            if(nome == null || nome.equals("")){
                throw new InvalidInputException("Por favor forneça um nome.");
            }

            // Valor a ser inserido e possível existente
            Disciplina disciplina = new Disciplina(disciplinaId, nome, departamentoId, cargaHoraria);
            Disciplina existente = disciplinaDBDAO.buscaPorId(disciplinaId);

            if (existente != null) {
                /*  10° Refatoração
                 *   Autor: Guilherme
                 *   Simplificação de confirmação com herança
                 * */
                if (getConfirmacao("Confirmar Atualização", "Disciplina já cadastrada, deseja atualizar os dados desta disciplina?")) {
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
            atualizarTabelaDisciplinas();

        } catch (NumberFormatException e) {
            showAlert("Erro de entrada", "Algum campo está errado.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro no banco de dados", "Não foi possível salvar os dados da disciplina.");
        } catch (InvalidInputException e) {
            showAlert("Entrada inválida", e.getMessage());
        }
    }

    private void atualizarTabelaDisciplinas() {
        try {
            disciplinas = disciplinaDBDAO.listaTodas();
            observableDisciplinas.setAll(disciplinas);
        } catch (SQLException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro no banco de dados.");
            alert.setContentText("Não foi possível recuperar os dados do banco.");
            alert.showAndWait();
        }
    }

    @FXML
    public void initialize() {
        disciplinaDBDAO = new DisciplinaDBDAO();

        /*  6° Refatoração
         *   Autor: Guilherme K.T.
         *   Extract Method para inicialização da tabela
         * */
        initTables();

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
        showAlert("Onde você quer chegar?", "Você já está aqui.");
    }

    private void initTables(){
        // Configurar as colunas
        colId.setCellValueFactory(new PropertyValueFactory<>("disciplinaId"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        observableDisciplinas = FXCollections.observableArrayList(new ArrayList<>());
        tabelaDisciplinas.setItems(observableDisciplinas);
        atualizarTabelaDisciplinas();
    }


}
