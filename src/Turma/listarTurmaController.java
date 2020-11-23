package Turma;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.util.Turma;

public class listarTurmaController implements Initializable {

	@FXML
	TableView<Turma> tableView;

	@FXML
	TableColumn<Turma, String> codigoColumn = new TableColumn<>("Código");
	@FXML
	TableColumn<Turma, String> codigoDisciplinaColumn = new TableColumn<>("Código Disciplina");
	@FXML
	TableColumn<Turma, String> cpfProfessorColumn = new TableColumn<>("CPF Professor");
	@FXML
	TableColumn<Turma, String> horarioColumn = new TableColumn<>("Horário");
	@FXML
	TableColumn<Turma, Integer> numeroAlunosColumn = new TableColumn<>("Numero de Alunos");


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		codigoDisciplinaColumn.setCellValueFactory(new PropertyValueFactory<>("codigoDisciplina"));
		horarioColumn.setCellValueFactory(new PropertyValueFactory<>("horarioAulas"));
		numeroAlunosColumn.setCellValueFactory(new PropertyValueFactory<>("numeroMaxAlunos"));
		cpfProfessorColumn.setCellValueFactory(new PropertyValueFactory<>("cpfProfessor"));
		tableView.setItems(getTurmas());

	}


	public ObservableList<Turma> getTurmas() {
		System.out.println("Lendo lista de Turmas");
		LinkedList<Turma> lista = leitorListas.ListaTurmas();
		System.out.println(lista);
		ObservableList<Turma> disciplinas = FXCollections.observableArrayList();
System.out.println(lista.isEmpty());
		if (lista.isEmpty() != true) {
			for (Turma disciplina : lista) {
				System.out.println(disciplina);
				disciplinas.add(disciplina);
			}
		}
		return disciplinas;
	}

}
