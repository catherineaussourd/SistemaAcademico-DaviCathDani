package Professor;

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

import Turma.leitorListas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import view.util.Alerts;
import view.util.Professor;
import view.util.Turma;

public class removerProfessorController implements Initializable {

	@FXML
	AnchorPane Professor;
	@FXML
	TextField nomeProfessor;
	@FXML
	TextField cpfProfessor;
	@FXML
	TextField telefoneProfessor;
	@FXML
	TextField emailProfessor;
	@FXML
	TextField ruaProfessor;
	@FXML
	TextField numeroProfessor;
	@FXML
	TextField cepProfessor;
	@FXML
	TextField cidadeProfessor;
	@FXML
	TextField bairroProfessor;
	@FXML
	private ChoiceBox<String> sexoProfessor;
	@FXML
	DatePicker dataNascimentoProfessor;
	@FXML
	Button removeButton;
	@FXML
	Button searchProfessor;

	@FXML
	public void buscarProfessor(ActionEvent event) {
		LinkedList<Professor> listaProfessors = Lista();
		String cpfprocurar = cpfProfessor.getText();
		int resultado = buscar(cpfprocurar, listaProfessors);
		System.out.println("INDEX DO CPF: " + resultado);
		if (resultado != -1) {
			Professor.setVisible(true);
			carregarDados(resultado, listaProfessors);
		}else {
			Alerts.showAlerts("Error!", null, "CPF não cadastrado!", AlertType.ERROR);
		}

	}

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
		for (Professor arofessor : lista) {
			System.out.println("Comparar:" + arofessor.getCpf() + " e: " + cpf);
			if (arofessor.getCpf().compareToIgnoreCase(cpf) == 0) {

				return lista.indexOf(arofessor);
			}
		}
		return -1;
	}

	public void carregarDados(int index, LinkedList<Professor> lista) {

		Professor professor = lista.get(index);
		String sexo = professor.getSexo();
		sexoProfessor.setValue(sexo);
		nomeProfessor.setText(professor.getNome());
		telefoneProfessor.setText(professor.getTelefone());
		emailProfessor.setText(professor.getEmail());
		ruaProfessor.setText(professor.getEndereco().getRua());
		numeroProfessor.setText(professor.getEndereco().getNumero());
		cepProfessor.setText(professor.getEndereco().getCEP());
		cidadeProfessor.setText(professor.getEndereco().getCidade());
		bairroProfessor.setText(professor.getEndereco().getBairro());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		System.out.println(formatter);
		LocalDate localDate = LocalDate.parse(professor.getDataNascimento(), formatter);
		System.out.println(localDate);
		dataNascimentoProfessor.setValue(localDate);
		desabilitar();
	}

	private void desabilitar() {
		nomeProfessor.setEditable(false);
		nomeProfessor.setDisable(true);
		telefoneProfessor.setEditable(false);
		telefoneProfessor.setDisable(true);
		emailProfessor.setEditable(false);
		emailProfessor.setDisable(true);
		ruaProfessor.setEditable(false);
		ruaProfessor.setDisable(true);
		numeroProfessor.setEditable(false);
		numeroProfessor.setDisable(true);
		cepProfessor.setEditable(false);
		cepProfessor.setDisable(true);
		cidadeProfessor.setEditable(false);
		cidadeProfessor.setDisable(true);
		bairroProfessor.setEditable(false);
		bairroProfessor.setDisable(true);
		dataNascimentoProfessor.setDisable(true);
		sexoProfessor.setDisable(true);
	}

	private boolean inTurma(String cpf) {
		LinkedList<Turma> turmas = leitorListas.ListaTurmas();
		for (Turma turma : turmas) {
			if (turma.getCpfProfessor().compareTo(cpf) == 0) {
				return true;
			}
		}
		return false;
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

	@FXML
	public void deletarProfessor(ActionEvent event) {
		LinkedList<Professor> listaProfessors = Lista();
		String cpfprocurar = cpfProfessor.getText();
		int resultado = buscar(cpfprocurar, listaProfessors);
		System.out.println("INDEX DO CPF: " + resultado);
		if (resultado != -1) {

			if (inTurma(cpfprocurar) == false) {

				listaProfessors.remove(resultado);
				try {
					FileOutputStream fout = new FileOutputStream("c:\\temp\\ListaProfessor.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					oos.writeObject(listaProfessors);
					oos.close();
					Alerts.showAlerts("Sucesso", null, "Professor removido!", AlertType.ERROR);
				} catch (IOException e) {
					System.out.println("Error de escrita: " + e.getMessage());
				}
			}else {
				Alerts.showAlerts("Error", null, "Professor cadastrado em uma turma!", AlertType.CONFIRMATION);
			}
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Professor.setVisible(false);
		nomeProfessor.setEditable(false);
		sexoProfessor.getItems().add("Homem");
		sexoProfessor.getItems().add("Mulher");
		sexoProfessor.getItems().add("Outros");
		view.util.Constraints.setTextFieldInteger(cpfProfessor);
	}

}
