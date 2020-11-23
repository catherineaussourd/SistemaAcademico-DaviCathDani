package Aluno;

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
import view.util.Aluno;
import view.util.Turma;

public class removerAlunoController implements Initializable {

	@FXML
	AnchorPane Aluno;
	@FXML
	TextField nomeAluno;
	@FXML
	TextField cpfAluno;
	@FXML
	TextField telefoneAluno;
	@FXML
	TextField emailAluno;
	@FXML
	TextField ruaAluno;
	@FXML
	TextField numeroAluno;
	@FXML
	TextField cepAluno;
	@FXML
	TextField cidadeAluno;
	@FXML
	TextField bairroAluno;
	@FXML
	private ChoiceBox<String> sexoAluno;
	@FXML
	DatePicker dataNascimentoAluno;
	@FXML
	Button removeButton;
	@FXML
	Button searchAluno;

	@FXML
	public void buscarAluno(ActionEvent event) {
		LinkedList<Aluno> listaAlunos = Lista();
		String cpfprocurar = cpfAluno.getText();
		int resultado = buscar(cpfprocurar, listaAlunos);
		System.out.println("INDEX DO CPF: " + resultado);
		if (resultado != -1) {
			Aluno.setVisible(true);
			carregarDados(resultado, listaAlunos);
		}else if (resultado == -1) {
			Alerts.showAlerts("Error", null, "CPF Não cadastrado!", AlertType.ERROR);
		}

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

	public int buscar(String cpf, LinkedList<Aluno> lista) {
		System.out.println("CPF PROCURADO:" + cpf);
		System.out.println("--------------");
		for (Aluno aluno : lista) {
			System.out.println("Comparar:" + aluno.getCpf() + " e: " + cpf);
			if (aluno.getCpf().compareToIgnoreCase(cpf) == 0) {

				return lista.indexOf(aluno);
			}
		}
		return -1;
	}

	public void carregarDados(int index, LinkedList<Aluno> lista) {

		Aluno aluno = lista.get(index);
		nomeAluno.setText(aluno.getNome());
		telefoneAluno.setText(aluno.getTelefone());
		emailAluno.setText(aluno.getEmail());
		ruaAluno.setText(aluno.getEndereco().getRua());
		numeroAluno.setText(aluno.getEndereco().getNumero());
		cepAluno.setText(aluno.getEndereco().getCEP());
		cidadeAluno.setText(aluno.getEndereco().getCidade());
		bairroAluno.setText(aluno.getEndereco().getBairro());
		sexoAluno.setValue(aluno.getSexo());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		System.out.println(formatter);
		LocalDate localDate = LocalDate.parse(aluno.getDataNascimento(), formatter);
		System.out.println(localDate);
		dataNascimentoAluno.setValue(localDate);
		desabilitar();
	}

	private void desabilitar() {
		nomeAluno.setEditable(false);
		nomeAluno.setDisable(true);
		sexoAluno.setDisable(true);
		dataNascimentoAluno.setDisable(true);
		telefoneAluno.setEditable(false);
		telefoneAluno.setDisable(true);
		emailAluno.setEditable(false);
		emailAluno.setDisable(true);
		ruaAluno.setEditable(false);
		ruaAluno.setDisable(true);
		numeroAluno.setEditable(false);
		numeroAluno.setDisable(true);
		cepAluno.setEditable(false);
		cepAluno.setDisable(true);
		cidadeAluno.setEditable(false);
		cidadeAluno.setDisable(true);
		bairroAluno.setEditable(false);
		bairroAluno.setDisable(true);
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

	@FXML
	public void deletarAluno(ActionEvent event) {
		LinkedList<Aluno> listaAlunos = Lista();
		String cpfprocurar = cpfAluno.getText();
		int resultado = buscar(cpfprocurar, listaAlunos);
		System.out.println("INDEX DO CPF: " + resultado);
		System.out.println(inTurma(cpfprocurar));
		if ((resultado != -1) && (inTurma(cpfprocurar) == false)) {

			listaAlunos.remove(resultado);
			try {
				FileOutputStream fout = new FileOutputStream("c:\\temp\\ListaAlunos.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(listaAlunos);
				oos.close();
				Alerts.showAlerts("Aluno deletado!", null, "Aluno deletado!", AlertType.WARNING);
			} catch (IOException e) {
				System.out.println("Error de escrita: " + e.getMessage());
			}
		}  else if (inTurma(cpfprocurar) == true) {
			Alerts.showAlerts("Error", null, "Aluno cadastrado em turma!!!", AlertType.ERROR);
		}

	}

	private boolean inTurma(String cpf) {
		LinkedList<Turma> turmas = leitorListas.ListaTurmas();
		for (Turma turma : turmas) {
			System.out.println(turma);
			String[] aux = turma.getAlunos();
			try {
				for (String cpfs : aux) {
					System.out.println("comparando: "+cpf+" com :"+cpfs);
					if(cpfs.compareTo(cpf)==0) {
						return true;
					}
				}

			} catch (NullPointerException e) {
				System.out.println("Lista vazia");
			}
			
		}return false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Aluno.setVisible(false);
		nomeAluno.setEditable(false);
		sexoAluno.getItems().add("Homem");
		sexoAluno.getItems().add("Mulher");
		sexoAluno.getItems().add("Outros");
		view.util.Constraints.setTextFieldInteger(cpfAluno);
		view.util.Constraints.setTextMaxLength(cpfAluno, 11);
	}

}
