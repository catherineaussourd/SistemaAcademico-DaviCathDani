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

public class editarProfessorController implements Initializable {

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
	Button saveButton;
	@FXML
	Button searchProfessor;
	@FXML
	Button editarNome;
	@FXML
	Button editarEmail;
	@FXML
	Button editarTelefone;
	@FXML
	Button editarSexo;
	@FXML
	Button editarData;
	@FXML
	Button editarRua;
	@FXML
	Button editarNumero;
	@FXML
	Button editarCEP;
	@FXML
	Button editarCidade;
	@FXML
	Button editarBairro;

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
			Alerts.showAlerts("Error", null, "CPF Não encontrado ou invalido", AlertType.ERROR);
		}

	}

	@FXML
	public void editarProfessor(ActionEvent event) {

		LinkedList<Professor> listaProfessors = Lista();
		String cpfprocurar = cpfProfessor.getText();
		int resultado = buscar(cpfprocurar, listaProfessors);
		System.out.println("INDEX DO CPF: " + resultado);
		if (resultado != -1) {
			listaProfessors.get(resultado).setEmail(emailProfessor.getText());
			listaProfessors.get(resultado).setNome(nomeProfessor.getText());
			listaProfessors.get(resultado).setTelefone(telefoneProfessor.getText());
			LocalDate ld = dataNascimentoProfessor.getValue();
			String DataNascimento = ld.toString();
			listaProfessors.get(resultado).setDataNascimento(DataNascimento);
			listaProfessors.get(resultado).setSexo(sexoProfessor.getValue().toString());
			listaProfessors.get(resultado).getEndereco().setCidade(cidadeProfessor.getText());
			listaProfessors.get(resultado).getEndereco().setRua(ruaProfessor.getText());
			listaProfessors.get(resultado).getEndereco().setNumero(numeroProfessor.getText());
			listaProfessors.get(resultado).getEndereco().setCEP(cepProfessor.getText());
			listaProfessors.get(resultado).getEndereco().setBairro(bairroProfessor.getText());

			try {
				FileOutputStream fout = new FileOutputStream("c:\\temp\\ListaProfessor.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(listaProfessors);
				oos.close();
				zerarTela();
				Alerts.showAlerts("Sucesso", null, "Professor atualizado!", AlertType.CONFIRMATION);
			} catch (IOException e) {
				System.out.println("Error de escrita: " + e.getMessage());
			}
		}else {
			Alerts.showAlerts("Error", null, "Professor já cadastrado!", AlertType.ERROR);
		}
	}

	
	private void zerarTela() {
		cpfProfessor.setDisable(false);
		Professor.setVisible(false);
		nomeProfessor.setText("");
		telefoneProfessor.setText("");
		sexoProfessor.setValue(null);
		emailProfessor.setText("");
		ruaProfessor.setText("");
		numeroProfessor.setText("");
		cepProfessor.setText("");
		cidadeProfessor.setText("");
		bairroProfessor.setText("");
		dataNascimentoProfessor.setValue(null);
	}
	@FXML
	private void disabletelas() {
		nomeProfessor.setDisable(true);
		cpfProfessor.setDisable(true);
		telefoneProfessor.setDisable(true);
		emailProfessor.setDisable(true);
		ruaProfessor.setDisable(true);
		numeroProfessor.setDisable(true);
		cepProfessor.setDisable(true);
		cidadeProfessor.setDisable(true);
		bairroProfessor.setDisable(true);
		dataNascimentoProfessor.setDisable(true);
		sexoProfessor.setDisable(true);
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
		for (Professor professor : lista) {
			System.out.println("Comparar:" + professor.getCpf() + " e: " + cpf);
			if (professor.getCpf().compareToIgnoreCase(cpf) == 0) {

				return lista.indexOf(professor);
			}
		}
		return -1;
	}

	public void carregarDados(int index, LinkedList<Professor> lista) {

		Professor professor = lista.get(index);
		String sexo = professor.getSexo();
		nomeProfessor.setText(professor.getNome());
		telefoneProfessor.setText(professor.getTelefone());
		sexoProfessor.setValue(sexo);
		emailProfessor.setText(professor.getEmail());
		ruaProfessor.setText(professor.getEndereco().getRua());
		numeroProfessor.setText(professor.getEndereco().getNumero());
		cepProfessor.setText(professor.getEndereco().getCEP());
		cidadeProfessor.setText(professor.getEndereco().getCidade());
		bairroProfessor.setText(professor.getEndereco().getBairro());
		System.out.println(professor.getDataNascimento());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		System.out.println(formatter);
		LocalDate localDate = LocalDate.parse(professor.getDataNascimento(), formatter);
		System.out.println(localDate);
		dataNascimentoProfessor.setValue(localDate);
		desabilitar();

	}

	private void desabilitar() {
		sexoProfessor.setDisable(true);
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
	}

	@FXML
	public void habilitarSelecionado(ActionEvent event) {
		Button b = (Button) event.getSource();
		String button = b.getId();
		System.out.println(button);
		switch (button) {
		case "editarNome":
			System.out.println("Acerto");
			nomeProfessor.setEditable(true);
			nomeProfessor.setDisable(false);
			break;
		case "editarEmail":
			emailProfessor.setEditable(true);
			emailProfessor.setDisable(false);
			break;
		case "editarTelefone":
			telefoneProfessor.setEditable(true);
			telefoneProfessor.setDisable(false);
			break;
		case "editarSexo":
			sexoProfessor.setDisable(false);
			break;
		case "editarData":
			if (dataNascimentoProfessor.isDisabled() == true) {
				dataNascimentoProfessor.setDisable(false);
			} else {
				dataNascimentoProfessor.setDisable(true);
			}

			break;
		case "editarRua":
			if (ruaProfessor.isDisabled() == true) {
				ruaProfessor.setDisable(false);
			} else {
				ruaProfessor.setDisable(true);
			}
			break;
		case "editarNumero":
			numeroProfessor.setDisable(false);
			break;
		case "editarCEP":
			if (cepProfessor.isDisabled() == false) {
				cepProfessor.setDisable(true);
			} else {
				cepProfessor.setDisable(false);
			}
			break;
		case "editarCidade":
			if (cidadeProfessor.isDisabled() == true) {
				cidadeProfessor.setDisable(false);
			} else {
				cidadeProfessor.setDisable(true);
			}
		default:
			break;
		}
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
		sexoProfessor.getItems().add("Homem");
		sexoProfessor.getItems().add("Mulher");
		sexoProfessor.getItems().add("Outros");
		Professor.setVisible(false);
		nomeProfessor.setEditable(false);
		view.util.Constraints.setTextFieldInteger(telefoneProfessor);
		view.util.Constraints.setTextFieldInteger(cpfProfessor);
		view.util.Constraints.setTextFieldInteger(cepProfessor);
		view.util.Constraints.setTextFieldInteger(numeroProfessor);
		view.util.Constraints.setTextMaxLength(telefoneProfessor, 11);
		view.util.Constraints.setTextMaxLength(cpfProfessor, 11);
		view.util.Constraints.validateText(nomeProfessor);
	}

}
