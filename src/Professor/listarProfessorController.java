package Professor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import view.util.Professor;

public class listarProfessorController implements Initializable {

	@FXML
	TableView<Professor> tableView;

	@FXML
	TableColumn<Professor, String> nameColumn = new TableColumn<>("Nome");
	@FXML
	TableColumn<Professor, String> cpfColumn = new TableColumn<>("CPF");
	@FXML
	TableColumn<Professor, String> emailColumn = new TableColumn<>("Email");
	@FXML
	TableColumn<Professor, String> nascimentoColumn = new TableColumn<>("Email");
	@FXML
	TableColumn<Professor, String> sexoColumn = new TableColumn<>("Sexo");
	@FXML
	TableColumn<Professor, String> telefoneColumn = new TableColumn<>("Telefone");
	@FXML
	TableColumn<Professor, String> ruaColumn = new TableColumn<>("Rua");
	@FXML
	TableColumn<Professor, String> numeroColumn = new TableColumn<>("Numero");
	@FXML
	TableColumn<Professor, String> cepColumn = new TableColumn<>("CEP");
	@FXML
	TableColumn<Professor, String> cidadeColumn = new TableColumn<>("Cidade");
	@FXML
	TableColumn<Professor, String> bairroColumn = new TableColumn<>("Bairro");

	@SuppressWarnings("unchecked")
	public LinkedList<Professor> Lista() {
		LinkedList<Professor> lista = null;
		try {
			FileInputStream fin = new FileInputStream("c:\\temp\\ListaProfessor.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			lista = ((LinkedList<Professor>) ois.readObject());
			ois.close();
			return lista;
		} catch (IOException e) {
			return lista;
		} catch (ClassNotFoundException e) {
			System.out.println("Error de leitura: " + e.getMessage());
		}
		return lista;
	}

	public int buscar(String cpf, LinkedList<Professor> lista) {
		System.out.println("CPF PROCURADO:" + cpf);
		System.out.println("--------------");
		for (Professor professor : lista) {
			System.out.println("Comparar:" + professor.getCpf() + " e: " + cpf);
			if (professor.getCpf().compareToIgnoreCase(cpf) == 0) {

				return lista.indexOf(professor);
			}
		}
		return -1;
	}

	public void adicionar(Professor NovoProfessor) {
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

			LinkedList<Professor> lista = Lista();
			if (lista == null) {
				lista = new LinkedList<Professor>();
			}
			lista.add(NovoProfessor);
			System.out.println(NovoProfessor);
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

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		nascimentoColumn.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
		sexoColumn.setCellValueFactory(new PropertyValueFactory<>("sexo"));
		telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		ruaColumn.setCellValueFactory(new PropertyValueFactory<>("endereco.Rua"));
		numeroColumn.setCellValueFactory(new PropertyValueFactory<>("endereco.Numero"));
		cepColumn.setCellValueFactory(new PropertyValueFactory<>("endereco.CEP"));
		cidadeColumn.setCellValueFactory(new PropertyValueFactory<>("endereco.Cidade"));
		bairroColumn.setCellValueFactory(new PropertyValueFactory<>("endereco.Bairro"));

		ruaColumn.setCellValueFactory(new Callback<CellDataFeatures<Professor, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Professor, String> c) {
				return new SimpleStringProperty(c.getValue().getEndereco().getRua());
			}
		});

		numeroColumn.setCellValueFactory(new Callback<CellDataFeatures<Professor, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Professor, String> c) {
				return new SimpleStringProperty(c.getValue().getEndereco().getNumero());
			}
		});
		cepColumn.setCellValueFactory(new Callback<CellDataFeatures<Professor, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Professor, String> c) {
				return new SimpleStringProperty(c.getValue().getEndereco().getCEP());
			}
		});
		cidadeColumn.setCellValueFactory(new Callback<CellDataFeatures<Professor, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Professor, String> c) {
				return new SimpleStringProperty(c.getValue().getEndereco().getCidade());
			}
		});
		bairroColumn.setCellValueFactory(new Callback<CellDataFeatures<Professor, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Professor, String> c) {
				return new SimpleStringProperty(c.getValue().getEndereco().getBairro());
			}
		});

		tableView.setItems(getProfessors());

	}

	public ObservableList<Professor> getProfessors() {
		System.out.println("Lendo lista de professores");
		LinkedList<Professor> lista = Lista();
		ObservableList<Professor> professors = FXCollections.observableArrayList();

		if (lista.isEmpty() != true) {
			for (Professor professor : lista) {
				professors.add(professor);
			}
		}
		return professors;
	}

}
