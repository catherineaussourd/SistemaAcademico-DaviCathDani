package Disciplina;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.util.Disciplina;

public class listarDisciplinaController implements Initializable {

	@FXML
	TableView<Disciplina> tableView;

	@FXML
	TableColumn<Disciplina, String> nameColumn = new TableColumn<>("Nome");
	@FXML
	TableColumn<Disciplina, String> codigoColumn = new TableColumn<>("Código");
	@FXML
	TableColumn<Disciplina, String> cargaHorariaColumn = new TableColumn<>("Carga Horária");
	@FXML
	TableColumn<Disciplina, String> creditosColumn = new TableColumn<>("Créditos");


	@SuppressWarnings("unchecked")
	public LinkedList<Disciplina> Lista() {
		LinkedList<Disciplina> lista = null;
		try {
			FileInputStream fin = new FileInputStream("c:\\temp\\ListaDisciplina.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			lista = ((LinkedList<Disciplina>) ois.readObject());
			ois.close();
			return lista;
		} catch (IOException e) {
			return lista;
		} catch (ClassNotFoundException e) {
			System.out.println("Error de leitura: " + e.getMessage());
		}
		return lista;
	}


	public void adicionar(Disciplina NovoDisciplina) {
		File tempFile = new File("c:\\temp\\ListaDisciplina.ser");
		boolean exists = tempFile.exists();
		if (!exists) {
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				System.out.println("An error occurred.");
			}

		}
		try {

			LinkedList<Disciplina> lista = Lista();
			if (lista == null) {
				lista = new LinkedList<Disciplina>();
			}
			lista.add(NovoDisciplina);
			System.out.println(NovoDisciplina);
			FileOutputStream fout = new FileOutputStream("c:\\temp\\ListaDisciplina.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(lista);
			oos.close();

		} catch (IOException e) {
			System.out.println("Error de escrita: " + e.getMessage());
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		cargaHorariaColumn.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
		creditosColumn.setCellValueFactory(new PropertyValueFactory<>("creditos"));
		tableView.setItems(getDisciplinas());

	}

	public ObservableList<Disciplina> getDisciplinas() {
		System.out.println("Lendo lista de Disciplinas");
		LinkedList<Disciplina> lista = Lista();
		System.out.println(lista);
		ObservableList<Disciplina> disciplinas = FXCollections.observableArrayList();
System.out.println(lista.isEmpty());
		if (lista.isEmpty() != true) {
			for (Disciplina disciplina : lista) {
				System.out.println(disciplina);
				disciplinas.add(disciplina);
			}
		}
		return disciplinas;
	}

}
