package Turma;

import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import view.util.Alerts;
import view.util.Disciplina;
import view.util.Professor;
import view.util.Turma;

public class listarTurmaDeDisciplinaController implements Initializable {

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
	TableColumn<Turma, Integer> numeroAlunosColumn = new TableColumn<>("Numero Maximo de Alunos");
	@FXML
	TextField codDisciplina;
	@FXML
	Button searchButton;

	@FXML
	public void buscarDisciplina(ActionEvent event) {
		LinkedList<Disciplina> listaProfessors = leitorListas.ListaDisciplina();
		String codProcurar = codDisciplina.getText();
		int resultado = buscar(codProcurar, listaProfessors);
		System.out.println("INDEX DO CPF: " + resultado);
		
		 if (resultado != -1) {
			exibirLista();

		} else {
			Alerts.showAlerts("Error", null, "Disciplina não encontrada!", AlertType.ERROR);
		}

	}

	public int buscar(String codigo, LinkedList<Disciplina> lista) {
		System.out.println("codigo PROCURADO:" + codigo);
		System.out.println("--------------");
		for (Disciplina disc : lista) {
			System.out.println("Comparar:" + disc.getCodigo() + " e: " + codigo);
			if (disc.getCodigo().compareToIgnoreCase(codigo) == 0) {

				return lista.indexOf(disc);
			}
		}
		return -1;
	}
	


	public static boolean isCPF(String CPF) {

		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11)) {
			return false;
		}
		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else {
				dig10 = (char) (r + 48);
			}
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig11 = '0';
			} else {
				dig11 = (char) (r + 48);
			}
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
				return (true);
			} else {
				return false;
			}
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	private void exibirLista() {
		tableView.setItems(getTurmas());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		codigoDisciplinaColumn.setCellValueFactory(new PropertyValueFactory<>("codigoDisciplina"));
		horarioColumn.setCellValueFactory(new PropertyValueFactory<>("horarioAulas"));
		numeroAlunosColumn.setCellValueFactory(new PropertyValueFactory<>("numeroMaxAlunos"));
		cpfProfessorColumn.setCellValueFactory(new PropertyValueFactory<>("cpfProfessor"));

	}

	public ObservableList<Turma> getTurmas() {
		System.out.println("Lendo lista de Turmas");
		LinkedList<Turma> lista = leitorListas.ListaTurmas();
		System.out.println(lista);
		ObservableList<Turma> turmas = FXCollections.observableArrayList();
		System.out.println(lista.isEmpty());
		if (lista.isEmpty() != true) {
			for (Turma turma : lista) {
				try {
				if (turma.getCodigoDisciplina().equals(codDisciplina.getText()) == true) {
					System.out.println("aaaa");
					turmas.add(turma);
				}
				}catch(NullPointerException e){
					System.out.println(turma.getCodigo() + " " +turma.getCpfProfessor() );
				}
			}
		}
		return turmas;
	}

}
