package Turma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.util.Aluno;
import view.util.Professor;
import view.util.Turma;

public class editarTurmaController implements Initializable {

	@FXML
	AnchorPane Turma;
	@FXML
	TextField codTurma;
	@FXML
	TextField cpfProfessor;

	@FXML
	Button cancelarButton;

	@FXML
	Button saveButton;
	@FXML
	Button searchTurma;
	@FXML
	Button editarProfessor;

	@FXML
	Button addAlumnTableView;
	@FXML
	Button removeButton;

	@FXML
	TableView<Aluno> tableViewTurma;

	@FXML
	TableColumn<Aluno, String> nameColumnTurma = new TableColumn<>("Nome");
	@FXML
	TableColumn<Aluno, String> cpfColumnTurma = new TableColumn<>("CPF");

	@FXML
	TableView<Aluno> tableView;

	@FXML
	TableColumn<Aluno, String> nameColumn = new TableColumn<>("Nome");
	@FXML
	TableColumn<Aluno, String> cpfColumn = new TableColumn<>("CPF");

	@FXML
	public void buscarTurma(ActionEvent event) {
		LinkedList<Turma> listaTurmas = leitorListas.ListaTurmas();
		String codBuscar = codTurma.getText();
		int resultado = buscar(codBuscar, listaTurmas);
		System.out.println("INDEX DO CPF: " + resultado);
		if (resultado != -1) {
			Turma.setVisible(true);
			carregarDados(resultado, listaTurmas);
			tableViewTurma.setItems((getAlunosTurma(listaTurmas.get(resultado))));
			codTurma.setDisable(true);
			searchTurma.setDisable(true);
		}

	}
	
	
	@FXML
	public void removeAlumn(ActionEvent event) {
		tableViewTurma.getItems().remove(tableViewTurma.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void addNewAlumn(ActionEvent event) {
		Aluno al = tableView.getSelectionModel().getSelectedItem();
		if (contains(tableViewTurma, al)) {
			System.out.println("Elemento já existente");
		} else {
			tableViewTurma.getItems().add(al);
		}

	}

	@FXML
	public void cancelarButtonPress(ActionEvent event) {
		zerarTela();
	}

	public static boolean contains(TableView<Aluno> table, Aluno obj) {
		for (Aluno item : table.getItems())
			if (item.getCpf().equals(obj.getCpf())) {
				return true;
			}
		return false;
	}

	private void zerarTela() {
		codTurma.setText("");
		codTurma.setDisable(false);
		cpfProfessor.setText("");
		tableViewTurma.getItems().clear();
		Turma.setVisible(false);
		searchTurma.setDisable(false);
	}

	@FXML
	public void salvarTurma(ActionEvent event) {

		LinkedList<Turma> listaTurmas = leitorListas.ListaTurmas();
		String codigo = codTurma.getText();
		int resultado = buscar(codigo, listaTurmas);
		System.out.println("INDEX DO Codigo: " + resultado);
		if (resultado != -1) {
			String[] novaListaAlunos = new String[listaTurmas.get(resultado).getNumeroMaxAlunos()];
			int aux = 0;
			for (Aluno item : tableViewTurma.getItems()) {
				if (item != null) {
					novaListaAlunos[aux] = item.getCpf();
					aux++;
				}
			}
			if (cpfProfessor.getText().isEmpty()|| leitorListas.isCPF(cpfProfessor.getText())) {

				listaTurmas.get(resultado).setCpfProfessor(cpfProfessor.getText());
				listaTurmas.get(resultado).setAlunos(novaListaAlunos);

				try {
					FileOutputStream fout = new FileOutputStream("c:\\temp\\ListaTurma.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					oos.writeObject(listaTurmas);
					oos.close();

					disabletelas();
					zerarTela();
				} catch (IOException e) {
					System.out.println("Error de escrita: " + e.getMessage());
				}
			} else {
				System.out.println("Valor de cpf invalido");
			}
		}
	}

	@FXML
	private void disabletelas() {
		cpfProfessor.setDisable(true);

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
		cpfProfessor.setText(turma.getCpfProfessor());

		desabilitar();

	}

	private void desabilitar() {
		cpfProfessor.setDisable(true);

	}

	@FXML
	public void habilitarSelecionado(ActionEvent event) {
		cpfProfessor.setEditable(true);
		cpfProfessor.setDisable(false);
	}

	public void adicionar(Turma turma) {
		File tempFile = new File("c:\\temp\\ListaProfessor.ser");
		boolean exists = tempFile.exists();
		if (!exists) {
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				System.out.println("An error occurred.");
			}
		}
		try {

			LinkedList<Turma> lista = leitorListas.ListaTurmas();
			if (lista == null) {
				lista = new LinkedList<Turma>();
			}
			lista.add(turma);
			System.out.println(turma);
			FileOutputStream fout = new FileOutputStream("c:\\temp\\ListaProfessor.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(lista);
			oos.close();

		} catch (IOException e) {
			System.out.println("Error de escrita: " + e.getMessage());
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Turma.setVisible(false);
		view.util.Constraints.setTextFieldInteger(cpfProfessor);
		view.util.Constraints.setTextMaxLength(cpfProfessor, 11);

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		nameColumnTurma.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cpfColumnTurma.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableView.setItems(getAlunos());
	}

	public ObservableList<Aluno> getAlunos() {
		LinkedList<Aluno> lista = Lista();
		ObservableList<Aluno> alunos = FXCollections.observableArrayList();
		for (Aluno aluno : lista) {
			alunos.add(aluno);
		}
		return alunos;
	}

	@SuppressWarnings("unchecked")
	public LinkedList<Aluno> Lista() {
		LinkedList<Aluno> lista = null;
		try {
			FileInputStream fin = new FileInputStream("c:\\temp\\ListaAlunos.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			lista = ((LinkedList<Aluno>) ois.readObject());
			ois.close();
			return lista;
		} catch (IOException e) {
			return lista;
		} catch (ClassNotFoundException e) {
			System.out.println("Error de leitura: " + e.getMessage());
		}
		return lista;
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
