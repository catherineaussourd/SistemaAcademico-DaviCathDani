package Turma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.util.Aluno;
import view.util.Disciplina;
import view.util.Turma;

public class exibirTurmaController implements Initializable {

	@FXML
	AnchorPane Turma;
	@FXML
	TextField codigoTurma;
	@FXML
	TextField codigoDisciplina;
	@FXML
	TextField cpfProfessor;
	@FXML
	TextField horariosTurma;
	@FXML
	TextField tamanhoAlunos;
	@FXML
	TextField periodo;
	@FXML
	TableView<Aluno> tableViewTurma;

	@FXML
	TableColumn<Aluno, String> nameColumnTurma = new TableColumn<>("Nome");
	@FXML
	TableColumn<Aluno, String> cpfColumnTurma = new TableColumn<>("CPF");

	@FXML
	Button searchTurma;

	@FXML
	public void buscarTurma(ActionEvent event) {
		LinkedList<Turma> listaTurmas = leitorListas.ListaTurmas();
		String codBuscar = codigoTurma.getText();
		int resultado = buscar(codBuscar, listaTurmas);
		System.out.println("INDEX DO CPF: " + resultado);
		if (resultado != -1) {
			Turma.setVisible(true);
			carregarDados(resultado, listaTurmas);
			tableViewTurma.setItems((getAlunosTurma(listaTurmas.get(resultado))));

		}
	}

	public static boolean contains(TableView<Aluno> table, Aluno obj) {
		for (Aluno item : table.getItems())
			if (item.getCpf().equals(obj.getCpf())) {
				return true;
			}
		return false;
	}

	private void zerarTela() {
		codigoTurma.setText("");
		codigoTurma.setDisable(false);
		cpfProfessor.setText("");
		periodo.setText("");
		tableViewTurma.getItems().clear();
		Turma.setVisible(false);
		searchTurma.setDisable(false);
	}

	public int buscar(String codigo, LinkedList<Turma> lista) {
		System.out.println("CPF PROCURADO:" + codigo);
		System.out.println("--------------");
		for (Turma turma : lista) {
			System.out.println("Comparar:" + turma.getCodigo() + " e: " + codigo);
			if (turma.getCodigo().compareToIgnoreCase(codigo) == 0) {

				return lista.indexOf(turma);
			}
		}
		return -1;
	}

	public void carregarDados(int index, LinkedList<Turma> lista) {

		Turma turma = lista.get(index);
		codigoDisciplina.setText(turma.getCodigoDisciplina());
		cpfProfessor.setText(turma.getCpfProfessor());
		horariosTurma.setText(turma.getHorarioAulas());
		tamanhoAlunos.setText(String.valueOf(turma.getNumeroMaxAlunos()));
		periodo.setText(turma.getPeriodoLetivo());
		desabilitar();
	}

	private void desabilitar() {

		codigoDisciplina.setDisable(true);
		cpfProfessor.setDisable(true);
		tamanhoAlunos.setDisable(true);
		tableViewTurma.setDisable(true);
		horariosTurma.setDisable(true);
		periodo.setDisable(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Turma.setVisible(false);
		nameColumnTurma.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cpfColumnTurma.setCellValueFactory(new PropertyValueFactory<>("cpf"));
	}

	public ObservableList<Aluno> getAlunosTurma(Turma turma) {
		LinkedList<Aluno> lista = leitorListas.ListaAlunos();

		ObservableList<Aluno> alunos = FXCollections.observableArrayList();
		String[] alunosTurma = turma.getAlunos();
		try {
			for (String cpf : alunosTurma) {
				for (Aluno aluno : lista) {
					if (aluno.getCpf().compareTo(cpf) == 0) {
						alunos.add(aluno);
					} else {
						System.out.println("Não é da turma");
					}
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Lista vazia");
		}

		return alunos;
	}

}
