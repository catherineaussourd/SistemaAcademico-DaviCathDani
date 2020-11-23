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

public class editarAlunoController implements Initializable {

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
	Button saveButton;
	@FXML
	Button searchAluno;
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
	public void buscarAluno(ActionEvent event) {
		LinkedList<Aluno> listaAlunos = Lista();
		String cpfprocurar = cpfAluno.getText();
		int resultado = buscar(cpfprocurar, listaAlunos);
		System.out.println("INDEX DO CPF: " + resultado);
		if (resultado != -1) {
			Aluno.setVisible(true);
			carregarDados(resultado, listaAlunos);
		}else if(cpfprocurar==null||cpfprocurar.length()<11) {
			Alerts.showAlerts("Error", null, "CPF invalido ou vazio!.", AlertType.ERROR);
			
		}else {
			Alerts.showAlerts("Error", null, "CPF não encontrado!.", AlertType.ERROR);
		}

	}

	@FXML
	public void editarAluno(ActionEvent event) {

		LinkedList<Aluno> listaAlunos = Lista();
		String cpfprocurar = cpfAluno.getText();
		int resultado = buscar(cpfprocurar, listaAlunos);
		System.out.println("INDEX DO CPF: " + resultado);
		if (resultado != -1) {
			if (AddAlunoController.isCPF(cpfAluno.getText()) == false) {
				Alerts.showAlerts("Error", null, "CPF invalido ou vazio!.", AlertType.ERROR);
				return;
			} else if (sexoAluno.getValue() == null) {
				Alerts.showAlerts("Error", null, "É necessario marcar um \"Sexo\"!", AlertType.ERROR);
				return;
			} else if (dataNascimentoAluno.getValue() == null) {
				Alerts.showAlerts("Error", null, "É necessario marcar uma data de nascimento!.", AlertType.ERROR);
				return;
			} else if (validaEmail() == false) {
				Alerts.showAlerts("Error", null, "Email invalido", AlertType.ERROR);
				return;
			} else if (validarTelefone() == false) {
				Alerts.showAlerts("Error", null, "Telefone invalido", AlertType.ERROR);
				return;
			}

			else {
				listaAlunos.get(resultado).setEmail(emailAluno.getText());
				listaAlunos.get(resultado).setNome(nomeAluno.getText());
				listaAlunos.get(resultado).setTelefone(telefoneAluno.getText());
				LocalDate ld = dataNascimentoAluno.getValue();
				String DataNascimento = ld.toString();
				listaAlunos.get(resultado).setDataNascimento(DataNascimento);
				listaAlunos.get(resultado).setSexo(sexoAluno.getValue().toString());
				listaAlunos.get(resultado).getEndereco().setCidade(cidadeAluno.getText());
				listaAlunos.get(resultado).getEndereco().setRua(ruaAluno.getText());
				listaAlunos.get(resultado).getEndereco().setNumero(numeroAluno.getText());
				listaAlunos.get(resultado).getEndereco().setCEP(cepAluno.getText());
				listaAlunos.get(resultado).getEndereco().setBairro(bairroAluno.getText());

				try {
					FileOutputStream fout = new FileOutputStream("c:\\temp\\ListaAlunos.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					oos.writeObject(listaAlunos);
					oos.close();
					zerarTela();
					Alerts.showAlerts("Sucesso", null, "Aluno atualizado!", AlertType.CONFIRMATION);
				} catch (IOException e) {
					System.out.println("Error de escrita: " + e.getMessage());
				}
			}
		}else {
			Alerts.showAlerts("Error", null, "Aluno já cadastrado!", AlertType.ERROR);
		}
	}

	private boolean validarTelefone() {
		String telefone = telefoneAluno.getText();
		if (telefone.length() < 10) {
			return false;
		}
		return true;
	}

	public boolean validaEmail() {
		String email = emailAluno.getText();
		if (email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$") == false) {
			return false;
		}
		return true;
	}
	private void zerarTela() {
		cpfAluno.setDisable(false);
		Aluno.setVisible(false);
		nomeAluno.setText("");
		telefoneAluno.setText("");
		sexoAluno.setValue(null);
		emailAluno.setText("");
		ruaAluno.setText("");
		numeroAluno.setText("");
		cepAluno.setText("");
		cidadeAluno.setText("");
		bairroAluno.setText("");
		dataNascimentoAluno.setValue(null);
	}

	@FXML
	private void disabletelas() {
		nomeAluno.setDisable(true);
		cpfAluno.setDisable(true);
		telefoneAluno.setDisable(true);
		emailAluno.setDisable(true);
		ruaAluno.setDisable(true);
		numeroAluno.setDisable(true);
		cepAluno.setDisable(true);
		cidadeAluno.setDisable(true);
		bairroAluno.setDisable(true);
		dataNascimentoAluno.setDisable(true);
		sexoAluno.setDisable(true);
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
		String sexo = aluno.getSexo();
		nomeAluno.setText(aluno.getNome());
		telefoneAluno.setText(aluno.getTelefone());
		sexoAluno.setValue(sexo);
		emailAluno.setText(aluno.getEmail());
		ruaAluno.setText(aluno.getEndereco().getRua());
		numeroAluno.setText(aluno.getEndereco().getNumero());
		cepAluno.setText(aluno.getEndereco().getCEP());
		cidadeAluno.setText(aluno.getEndereco().getCidade());
		bairroAluno.setText(aluno.getEndereco().getBairro());
		System.out.println(aluno.getDataNascimento());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		System.out.println(formatter);
		LocalDate localDate = LocalDate.parse(aluno.getDataNascimento(), formatter);
		System.out.println(localDate);
		dataNascimentoAluno.setValue(localDate);
		desabilitar();

	}

	private void desabilitar() {
		sexoAluno.setDisable(true);
		nomeAluno.setEditable(false);
		nomeAluno.setDisable(true);
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
		dataNascimentoAluno.setDisable(true);
	}

	@FXML
	public void habilitarSelecionado(ActionEvent event) {
		Button b = (Button) event.getSource();
		String button = b.getId();
		System.out.println(button);
		switch (button) {
		case "editarNome":
			System.out.println("Acerto");
			nomeAluno.setEditable(true);
			nomeAluno.setDisable(false);
			break;
		case "editarEmail":
			emailAluno.setEditable(true);
			emailAluno.setDisable(false);
			break;
		case "editarTelefone":
			telefoneAluno.setEditable(true);
			telefoneAluno.setDisable(false);
			break;
		case "editarSexo":
			sexoAluno.setDisable(false);
			break;
		case "editarData":
			if (dataNascimentoAluno.isDisabled() == true) {
				dataNascimentoAluno.setDisable(false);
			} else {
				dataNascimentoAluno.setDisable(true);
			}

			break;
		case "editarRua":
			if (ruaAluno.isDisabled() == true) {
				ruaAluno.setDisable(false);
			} else {
				ruaAluno.setDisable(true);
			}
			break;
		case "editarNumero":
			numeroAluno.setDisable(false);
			break;
		case "editarCEP":
			if (cepAluno.isDisabled() == false) {
				cepAluno.setDisable(true);
			} else {
				cepAluno.setDisable(false);
			}
			break;
		case "editarCidade":
			if (cidadeAluno.isDisabled() == true) {
				cidadeAluno.setDisable(false);
			} else {
				cidadeAluno.setDisable(true);
			}
		default:
			break;
		}
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
		sexoAluno.getItems().add("Homem");
		sexoAluno.getItems().add("Mulher");
		sexoAluno.getItems().add("Outros");
		Aluno.setVisible(false);
		nomeAluno.setEditable(false);
		view.util.Constraints.setTextFieldInteger(telefoneAluno);
		view.util.Constraints.setTextFieldInteger(cpfAluno);
		view.util.Constraints.setTextFieldInteger(cepAluno);
		view.util.Constraints.setTextFieldInteger(numeroAluno);
		view.util.Constraints.setTextMaxLength(telefoneAluno, 11);
		view.util.Constraints.setTextMaxLength(cpfAluno, 11);
		view.util.Constraints.validateText(nomeAluno);
	}

}
