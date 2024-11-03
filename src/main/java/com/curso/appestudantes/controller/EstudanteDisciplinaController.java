package com.curso.appestudantes.controller;

import com.curso.appestudantes.dao.DisciplinaDBDAO;
import com.curso.appestudantes.dao.EstudanteDisciplinaDBDAO;
import com.curso.appestudantes.exceptions.InvalidInputException;
import com.curso.appestudantes.model.Disciplina;
import com.curso.appestudantes.model.Estudante;
import com.curso.appestudantes.model.EstudanteDisciplina;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class EstudanteDisciplinaController extends Controller {
    private ObservableList<EstudanteDisciplina> observableEstudanteDisciplina;
    private EstudanteDisciplinaDBDAO estudanteDisciplinaDBDAO;
    private Estudante estudante;

    @FXML
    private TableColumn<EstudanteDisciplina, String> colDisciplinaNome;

    @FXML
    private TableColumn<EstudanteDisciplina, String> colEstado;

    @FXML
    private ChoiceBox<Disciplina> disciplinaSelector;

    @FXML
    private Label nomeEstudanteLabel;

    @FXML
    private ChoiceBox<String> estadoSelector;

    @FXML
    private Button excluirButton;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<EstudanteDisciplina> tabelaDisciplinas;

    @FXML
    void excluirButtonOnAction(ActionEvent event) {
        try {
            Disciplina disciplina = disciplinaSelector.getValue();

            if(disciplina == null)
                throw new InvalidInputException("Você deve selecionar alguma disciplina");

            EstudanteDisciplina existente = estudanteDisciplinaDBDAO.buscaPorIds(estudante.getEstudanteId(), disciplina.getDisciplinaId());
            if(existente != null){
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmar Remoção");
                confirmAlert.setContentText("Deseja remover esta disciplina?");

                if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                    // Atualiza o registro no banco de dados
                    estudanteDisciplinaDBDAO.remove(existente);
                    showAlert("Remoção realizada", "A disciplina foi excluido com sucesso.");
                }
            } else {
                showAlert("Erro", "Não existe uma relação entre este estudante e disciplina.");
            }

            atualizarTabelaPrincipal();

        } catch (InvalidInputException e) {
            showAlert("Entrada inválida", e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro no banco de dados", "Não foi remover os dados.");
        }
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        try {
            Disciplina disciplina = disciplinaSelector.getValue();
            String estado = estadoSelector.getValue();

            // Verificar entrada vazia
            if(disciplina == null){
                throw new InvalidInputException("Você deve selecioanr uma disciplina");
            }
            if(estado == null || estado.equals("")){
                throw new InvalidInputException("Algum estado deve ser selecionado");
            }


            EstudanteDisciplina estudanteDisciplina = new EstudanteDisciplina(estudante, disciplina, estado);
            EstudanteDisciplina existente = estudanteDisciplinaDBDAO.buscaPorIds(estudante.getEstudanteId(), disciplina.getDisciplinaId());

            if (existente != null) {
                // Mostra popup de confirmação para atualizar o registro
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmar Atualização");
                confirmAlert.setHeaderText("Disciplina já cadastrada");
                confirmAlert.setContentText("Deseja atualizar os dados desta disciplina?");

                // Captura a resposta do usuário
                if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                    // Atualiza o registro no banco de dados
                    estudanteDisciplinaDBDAO.atualiza(estudanteDisciplina);
                    showAlert("Atualização realizada", "Os dados foram atualizados com sucesso.");
                }
            } else {
                // Insere novo registro no banco de dados
                estudanteDisciplinaDBDAO.insere(estudanteDisciplina);
                showAlert("Cadastro realizado", "Novo disciplina cadastrada com sucesso.");
            }

            atualizarTabelaPrincipal();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro no banco de dados", "Não foi possível salvar os dados.");
        } catch (InvalidInputException e) {
            showAlert("Entrada inválida", e.getMessage());
        }
    }

    // Não chamar antes de setEstudante.
    public void atualizarTabelaPrincipal() {
        try {
            List<EstudanteDisciplina> disciplinasDoEstudante = estudanteDisciplinaDBDAO.listaPorEstudanteId(estudante.getEstudanteId());
            observableEstudanteDisciplina.setAll(disciplinasDoEstudante);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setEstudante(Estudante estudante){
        this.estudante = estudante;
        nomeEstudanteLabel.setText(estudante.getNome());

        atualizarTabelaPrincipal();
    }

    public void initialize(){
        estudanteDisciplinaDBDAO = new EstudanteDisciplinaDBDAO();
        observableEstudanteDisciplina = tabelaDisciplinas.getItems();

        // Define preenchimento da tabela
        colDisciplinaNome.setCellValueFactory(dadoLinha -> {
            String nomeDisciplina = dadoLinha.getValue().getDisciplina().getNome();
            return new SimpleStringProperty(nomeDisciplina);
        });
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Carregamento da tabela principal deve ocorrer fora do initialize, para passar dados entre janelas.


        // Carregar disciplinas para o menu de seleção.
        try {
            DisciplinaDBDAO disciplinaDBDAO = new DisciplinaDBDAO();
            List<Disciplina> disciplinas = disciplinaDBDAO.listaTodas();

            disciplinaSelector.getItems().addAll(disciplinas);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro no banco de dados.");
            alert.setContentText("Não foi possível recuperar os dados (disciplinas) do banco.");
            alert.showAndWait();
        }


        // Carregar selecionado para a tela
        tabelaDisciplinas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Preencher campos de texto com dados do estudante selecionado
                disciplinaSelector.getSelectionModel().select(newSelection.getDisciplina());
                estadoSelector.getSelectionModel().select(newSelection.getEstado());
            }
        });
    }

}
