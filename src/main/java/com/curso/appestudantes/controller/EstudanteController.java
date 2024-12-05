package com.curso.appestudantes.controller;

import com.curso.appestudantes.App;
import com.curso.appestudantes.dao.EstudanteDBDAO;
import com.curso.appestudantes.exceptions.InvalidInputException;
import com.curso.appestudantes.model.Estudante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.util.ArrayList;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EstudanteController extends Controller {
    private EstudanteDBDAO estudanteDBDAO;
    private ObservableList<Estudante> observableEstudantes;

    @FXML
    private TableColumn<Estudante, Integer> colId;

    @FXML
    private TableColumn<Estudante, String> colNome;

    @FXML
    private TextField cpfField;

    @FXML
    private DatePicker dataNascField;

    @FXML
    private TextField estudanteIdField;

    /*  3° Refatoração
    *   Autor: JeanRGW
    *   Remoção de referências desnecessárias para a interface.
    * */

    @FXML
    private TextField nomeField;

    @FXML
    private TableView<Estudante> tabelaEstudantes;

    @FXML
    void editarDisciplinasButtonOnAction(ActionEvent event) {
        Estudante estudante = tabelaEstudantes.getSelectionModel().getSelectedItem();

        if(estudante != null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/estudanteDisciplina.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                scene.getStylesheets().add(getClass().getResource("/com/curso/appestudantes/view/styles.css").toExternalForm());

                Stage janelaPopUp = new Stage();

                // Travar tela inicial enquanto a outra estiver aberta
                janelaPopUp.initOwner(App.stage);
                janelaPopUp.initModality(Modality.APPLICATION_MODAL);

                // Carregar estudante para a tela
                janelaPopUp.setTitle("Disciplinas de " + estudante.getNome());
                EstudanteDisciplinaController controller = fxmlLoader.getController();
                controller.setEstudante(estudante);

                janelaPopUp.setScene(scene);
                janelaPopUp.setResizable(false);
                janelaPopUp.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void excluirButtonOnAction(ActionEvent event) {
        try {
            int estudanteId = Integer.parseInt(estudanteIdField.getText());

            Estudante existente = estudanteDBDAO.buscaPorId(estudanteId);

            if (existente != null) {
                /*  12° Refatoração
                 *   Autor: JeanRGW
                 *   Simplificação de confirmação com herança
                 * */
                if (getConfirmacao("Confirmar Remoção", "Deseja remover este estudante?")) {
                    // Atualiza o registro no banco de dados
                    estudanteDBDAO.removePorId(estudanteId);
                    showAlert("Remoção realizada", "O estudante foi excluido com sucesso.");
                }
            } else {
                showAlert("Erro", "Não existe um estudante cadastrado com esse ID.");
            }

            atualizarTabelaEstudantes();

        } catch (NumberFormatException e) {
            showAlert("Erro de entrada", "Algum campo está errado.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro no banco de dados", "Não foi possível remover o estudante.");
        }

    }

    // Validação de CPF, em caso de erro lança excessão com motivo.
    void validarCpf(String cpf) throws InvalidInputException {

        // Valida qnt de dígitos
        if(cpf.length() != 11)
            throw new InvalidInputException("Número de caracteres inválidos.");

        // Calcula os dígitos verificadores
        int d1 = calcularDigitoVerificador(cpf.substring(0, 9), 10);
        int d2 = calcularDigitoVerificador(cpf.substring(0, 9) + d1, 11);

        // Verifica se os dígitos verificadores estão corretos
        if (!cpf.equals(cpf.substring(0, 9) + d1 + d2))
            throw new InvalidInputException("Dígitos verificadores invalidos.");
    }

    private int calcularDigitoVerificador(String parte, int peso) throws InvalidInputException {
        int soma = 0;

        for (char c : parte.toCharArray()) {
            // Verifica se todos os caracteres são dígitos
            if (!Character.isDigit(c)) {
                throw new InvalidInputException("Caractere inválido no CPF: " + c);
            }

            soma += Character.getNumericValue(c) * peso--;
        }

        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        try {
            int estudanteId = Integer.parseInt(estudanteIdField.getText());
            String nome = nomeField.getText();
            if(nome == null || nome.equals("")){
                throw new InvalidInputException("Por favor forneça um nome.");
            }
            Date dataNasc = Date.valueOf(dataNascField.getValue());

            String cpf = cpfField.getText();
            validarCpf(cpf);

            Estudante estudante = new Estudante(estudanteId, dataNasc, cpf, nome);

            // Verifica se o estudante já existe no banco de dados
            Estudante existente = estudanteDBDAO.buscaPorId(estudanteId);

            if (existente != null) {
                /*  12° Refatoração
                 *   Autor: JeanRGW
                 *   Simplificação de confirmação com herança
                 * */
                if (getConfirmacao("Confirmar Atualização", "Estudante já cadastrado, deseja atualizar os dados deste estudante?")) {
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
            showAlert("Erro de entrada", "Algum campo está errado.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro no banco de dados", "Não foi possível salvar os dados do estudante.");
        } catch (InvalidInputException e) {
            showAlert("Entrada inválida", e.getMessage());
        }
    }

    // Método para atualizar a tabela de estudantes após inserir/atualizar
    private void atualizarTabelaEstudantes() {
        try {
            /* 8° Refatoração
            *   Autor: JeanRGW
            *   Atributo de classe convertido para variável local
            */
            List<Estudante> estudantes = estudanteDBDAO.listaTodos();
            observableEstudantes.setAll(estudantes);
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
        estudanteDBDAO = new EstudanteDBDAO();

        /*  7° Refatoração
        *   Autor: JeanRGW
        *   Extract Method para inicialização da tabela
        * */
        initTables();

        // Adicionar interação para tabela
        tabelaEstudantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                estudanteIdField.setText(String.valueOf(newSelection.getEstudanteId()));
                nomeField.setText(newSelection.getNome());
                cpfField.setText(newSelection.getCpf());
                dataNascField.setValue(LocalDate.parse(newSelection.getDataNascimento().toString()));
            }
        });

    }

    @FXML @Override
    void handleGoToEstudante(ActionEvent event) {
        showAlert("Onde você quer chegar?", "Você já está aqui.");
    }

    private void initTables(){
        // Configurar as colunas
        colId.setCellValueFactory(new PropertyValueFactory<>("estudanteId"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        observableEstudantes = FXCollections.observableArrayList(new ArrayList<>());
        tabelaEstudantes.setItems(observableEstudantes);
        atualizarTabelaEstudantes();
    }

}
