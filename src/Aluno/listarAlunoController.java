package Aluno;

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
import view.util.Aluno;

public class listarAlunoController implements Initializable{

	@FXML
	TableView<Aluno> tableView;

	@FXML
	TableColumn<Aluno, String> nameColumn = new TableColumn<>("Nome");
	@FXML
	TableColumn<Aluno, String> cpfColumn = new TableColumn<>("CPF");
	@FXML
	TableColumn<Aluno, String> emailColumn = new TableColumn<>("Email");
	@FXML
	TableColumn<Aluno, String> nascimentoColumn = new TableColumn<>("Email");
	@FXML
	TableColumn<Aluno, String> sexoColumn = new TableColumn<>("Sexo");
	@FXML
	TableColumn<Aluno, String> telefoneColumn = new TableColumn<>("Telefone");
	@FXML
	TableColumn<Aluno, String> ruaColumn = new TableColumn<>("Rua");
	@FXML
	TableColumn<Aluno, String> numeroColumn = new TableColumn<>("Numero");
	@FXML
	TableColumn<Aluno, String> cepColumn = new TableColumn<>("CEP");
	@FXML
	TableColumn<Aluno, String> cidadeColumn = new TableColumn<>("Cidade");
	@FXML
	TableColumn<Aluno, String> bairroColumn = new TableColumn<>("Bairro");
	
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
	
	public int buscar(String cpf,LinkedList<Aluno>lista) {
		System.out.println("CPF PROCURADO:"+cpf);
		System.out.println("--------------");
		for(Aluno aluno : lista) {
			System.out.println("Comparar:"+aluno.getCpf()+ " e: "+cpf);
			if (aluno.getCpf().compareToIgnoreCase(cpf) == 0) {
				
				return lista.indexOf(aluno);
			}
		}
		return -1;
	}
	

	public void adicionar(Aluno NovoAluno) {
		File tempFile = new File("c:\\temp\\ListaAlunos.ser");
		boolean exists = tempFile.exists();
		if (!exists) {
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				System.out.println("An error occurred.");
			}

		}
		try {
			
			LinkedList<Aluno> lista = Lista();
			if (lista == null) {
				lista = new LinkedList<Aluno>();
			}
			lista.add(NovoAluno);
			System.out.println(NovoAluno);
			FileOutputStream fout = new FileOutputStream("c:\\temp\\ListaAlunos.ser");
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
		
		ruaColumn.setCellValueFactory(new Callback<CellDataFeatures<Aluno, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(CellDataFeatures<Aluno, String> c) {
	            return new SimpleStringProperty(c.getValue().getEndereco().getRua());                
	        }
	}); 
		
		numeroColumn.setCellValueFactory(new Callback<CellDataFeatures<Aluno, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(CellDataFeatures<Aluno, String> c) {
	            return new SimpleStringProperty(c.getValue().getEndereco().getNumero());                
	        }
	}); 
		cepColumn.setCellValueFactory(new Callback<CellDataFeatures<Aluno, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(CellDataFeatures<Aluno, String> c) {
	            return new SimpleStringProperty(c.getValue().getEndereco().getCEP());                
	        }
	}); 
		cidadeColumn.setCellValueFactory(new Callback<CellDataFeatures<Aluno, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(CellDataFeatures<Aluno, String> c) {
	            return new SimpleStringProperty(c.getValue().getEndereco().getCidade());                
	        }
	}); 
		bairroColumn.setCellValueFactory(new Callback<CellDataFeatures<Aluno, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<Aluno, String> c) {
            return new SimpleStringProperty(c.getValue().getEndereco().getBairro());                
        }
}); 


		tableView.setItems(getAlunos());

		
		
	}
	
	public ObservableList<Aluno> getAlunos(){
		LinkedList<Aluno> lista = Lista();
		ObservableList<Aluno> alunos = FXCollections.observableArrayList();
		for(Aluno aluno : lista) {
			alunos.add(aluno);
		}
		return alunos;
	}
	
	
	
	
}
