package Professor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.InputMismatchException;
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
import view.util.Professor;
import view.util.Alerts;
import view.util.Aluno;
import view.util.Endereco;

public class AddProfessorController implements Serializable, Initializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML
	AnchorPane addProfessor;
	@FXML
	TextField nomeNovoProfessor;
	@FXML
	TextField cpfNovoProfessor;
	@FXML
	TextField telefoneNovoProfessor;
	@FXML
	TextField emailNovoProfessor;
	@FXML
	TextField ruaNovoProfessor;
	@FXML
	TextField numeroNovoProfessor;
	@FXML
	TextField cepNovoProfessor;
	@FXML
	TextField cidadeNovoProfessor;
	@FXML
	TextField bairroNovoProfessor;
	@FXML
	private ChoiceBox<String> sexoNovoProfessor;
	@FXML
	private Button b1;
	@FXML
	DatePicker dataNascimentoNovoprofessor;

	@FXML
	public void adicionarprofessor(ActionEvent event) {
		this.addProfessor.setVisible(true);

	}
	public void salvarprofessor(ActionEvent event) {
		
		String validacao = validarTela();

		if (validacao.equalsIgnoreCase("")) {
			salvarNovoProfessor();
		}else {
			Alerts.showAlerts("Error", null, validacao, AlertType.ERROR);
		}
	}
	
	
	
	private String validarTela() {
		String resposta = "";
		String cpfCadastrado="";
		String cpfProfessor = "";
		String sexoProfessor = "";
		String DataNascimentoProfessor = "";
		String email = "";
		String telefone = "";

		if (isCPF(cpfNovoProfessor.getText()) == false) {
			cpfProfessor = "\n Verifique o CPF informado!";
		}
		if(buscar(cpfNovoProfessor.getText(),leitorListas.ListaProfessor())!=-1) {
			cpfCadastrado="\n CPF já Cadastrado!!";
		}
		if (sexoNovoProfessor.getValue() == null) {
			sexoProfessor = "\n Verifique o Sexo informado!";
		}
		if (dataNascimentoNovoprofessor.getValue() == null) {
			DataNascimentoProfessor = "\n Verifique a Data de Nascimento informada!";
		}
		if (validaEmail() == false) {
			email = "\n Verifique o Email informado!";
		}
		if (validarTelefone() == false) {
			telefone = "\n Verifique o Telefone informado!";
		}

		resposta = cpfCadastrado+cpfProfessor + sexoProfessor + DataNascimentoProfessor + email + telefone;
		return resposta;

	}
	
	public static int buscar(String codigo, LinkedList<Professor> lista) {
		System.out.println("codigo PROCURADO:" + codigo);
		System.out.println("--------------");
		for (Professor professor : lista) {
			System.out.println("Comparar:" + professor.getCpf() + " e: " + codigo);
			if (professor.getCpf().compareToIgnoreCase(codigo) == 0) {

				return lista.indexOf(professor);
			}
		}
		return -1;
	}

	
	private boolean validarTelefone() {
		String telefone = telefoneNovoProfessor.getText();
		if(telefone.length()<10) {
			return false;
		}
		return true;
	}
	
	public boolean validaEmail() {
        String email = emailNovoProfessor.getText();
        if (email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$") == false) {
            return false;
        }
        return true;
    }

	public void salvarNovoProfessor() {
		System.out.println("come�ou a salvar");
		String nome = nomeNovoProfessor.getText();
		nome = nome.trim().replaceAll("\\s+", " ");
		String cpf = cpfNovoProfessor.getText();
		LocalDate ld = dataNascimentoNovoprofessor.getValue();
		String DataNascimento = ld.toString();
		String sexo = sexoNovoProfessor.getValue();
		String email = emailNovoProfessor.getText().trim();
		String rua = ruaNovoProfessor.getText().trim();
		String numero = numeroNovoProfessor.getText();
		String CEP = cepNovoProfessor.getText();
		String cidade = cidadeNovoProfessor.getText().trim();
		String Bairro = bairroNovoProfessor.getText().trim();
		String telefone = telefoneNovoProfessor.getText();
		System.out.println("DEU LOAD");
		Endereco enderecoNovoProfessor = new Endereco(rua, numero, cidade, CEP, Bairro);
		Professor novoProfessor = new Professor(nome, cpf, DataNascimento, sexo, telefone, email, enderecoNovoProfessor);
		adicionar(novoProfessor);
	}
	public void limparTela() {
		nomeNovoProfessor.setText("");
		cpfNovoProfessor.setText("");
		sexoNovoProfessor.setValue(null);
		dataNascimentoNovoprofessor.setValue(null);
		emailNovoProfessor.setText("");
		ruaNovoProfessor.setText("");
		numeroNovoProfessor.setText("");
		cepNovoProfessor.setText("");
		cidadeNovoProfessor.setText("");
		bairroNovoProfessor.setText("");
		telefoneNovoProfessor.setText("");
		
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
			limparTela();
                        Alerts.showAlerts("Sucesso", null, "Professor cadastrado!", AlertType.CONFIRMATION);

		} catch (IOException e) {
			System.out.println("Error de escrita: " + e.getMessage());
		}

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sexoNovoProfessor.getItems().add("Homem");
		sexoNovoProfessor.getItems().add("Mulher");
		sexoNovoProfessor.getItems().add("Outros");
		view.util.Constraints.setTextFieldInteger(telefoneNovoProfessor);
		view.util.Constraints.setTextFieldInteger(cpfNovoProfessor);
		view.util.Constraints.setTextFieldInteger(cepNovoProfessor);
		view.util.Constraints.setTextFieldInteger(numeroNovoProfessor);
		view.util.Constraints.setTextMaxLength(telefoneNovoProfessor, 11);
		view.util.Constraints.setTextMaxLength(cpfNovoProfessor, 11);
		view.util.Constraints.validateText(nomeNovoProfessor);

	}

}
