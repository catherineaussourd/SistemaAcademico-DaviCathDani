package Turma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;

import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import view.util.Alerts;
import view.util.Disciplina;
import view.util.Turma;

public class AddTurmaController implements Serializable, Initializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML
	AnchorPane addTurma;
	@FXML
	TextField codigoNovaTurma;
	@FXML
	TextField codigoDisciplina;
	@FXML
	TextField quantidadeAlunos;
	@FXML
	TextField periodoBox;
	@FXML
	private ChoiceBox<String> diasNovoTurma;
	@FXML
	private ChoiceBox<String> horarioNovoTurma;
	@FXML
	private Button b1;
	@FXML
	private Button continuar;

	@FXML
	public void continuarButton(ActionEvent event) {

		String cod = codigoDisciplina.getText();
		LinkedList<Disciplina> listDisc = leitorListas.ListaDisciplina();
		int resultBusca = leitorListas.buscar(cod, listDisc);
		System.out.println("resultBusca = " + resultBusca);
		if (resultBusca != -1) {
			this.addTurma.setVisible(true);
			this.b1.setDisable(false);
			if (listDisc.get(resultBusca).getCargaHoraria().compareTo("60h") == 0) {
				diasNovoTurma.getItems().add("2-5");
				diasNovoTurma.getItems().add("3-6");
				diasNovoTurma.getItems().add("4-7");
			} else {
				diasNovoTurma.getItems().add("2");
				diasNovoTurma.getItems().add("3");
				diasNovoTurma.getItems().add("4");
				diasNovoTurma.getItems().add("5");
				diasNovoTurma.getItems().add("6");
				diasNovoTurma.getItems().add("7");
			}

		} else {
			Alerts.showAlerts("Error", null, "Disciplina n�o cadastrada!", AlertType.ERROR);
		}

	}

	public void salvardisciplina(ActionEvent event) {

		// if((codigoNovaTurma.getText()!=null)&&
		// diasNovoTurma.getValue()!=null&&horarioNovoTurma.getValue()!=null&&quantidadeAlunos.getText()!=null&&codigoDisciplina.getText()!=null)
		// {

		salvarNovoTurma();
		// }else {
		// System.out.println("error disciplina");
		// }
	}

	public void salvarNovoTurma() {
		System.out.println("come�ou a salvar");
		String telaValida = validarTela();
		if(telaValida.equalsIgnoreCase("")) {
		String codigo = codigoNovaTurma.getText();
		codigo = codigo.trim().replaceAll("\\s+", " ");

		String codigodisciplina = codigoDisciplina.getText();
		codigo = codigo.trim().replaceAll("\\s+", " ");

		String horario = horarioNovoTurma.getValue();
		String dias = diasNovoTurma.getValue();
		System.out.println("INTEIRO");

		int savedValue = Integer.parseInt(quantidadeAlunos.getText());
		String periodo = periodoBox.getText();
		System.out.println("DEU LOAD");

		// VALIDAR DADOS AQUI
		
			Turma novoTurma = new Turma(codigo, codigodisciplina, savedValue, horario, periodo);
			System.out.println(novoTurma);
			adicionar(novoTurma);
		}
		else {
			Alerts.showAlerts("Error", null, telaValida, AlertType.ERROR);
		}
	}

	private String validarTela() {
		String resposta = "";
		String codTurma = "";
		String codDisciplina = "";
		String dias = "";
		String horario = "";
		String qtdAlunos = "";
		String periodo = "";
		if (validarCodTurma() == false) {
			codTurma = "\n Verifique o codigo de turma!";
		}
		if (validarCodDisciplina() == false) {
			codDisciplina = "\n Verifique o codigo de Disciplina!";
		}
		if (diasNovoTurma.getValue() == null) {
			dias = "\n Verifique os dias de aula da Turma!";
		}
		if (horarioNovoTurma.getValue() == null) {
			horario = "\n Verifique o horario das aulas da Turma!";
		}
		if (quantidadeAlunos.getText().isEmpty() == true) {
			qtdAlunos = "\n Verifique o campo de quantidade de alunos na turma!";
		}
		if(periodoBox.getText().isEmpty()==true) {
			periodo="\n Verifique o Per�odo Letivo da turma!";
		}
		resposta= codTurma + codDisciplina +  dias+ horario+ qtdAlunos+ periodo;
		return resposta;

	}

	private boolean validarCodTurma() {
		String cod = codigoNovaTurma.getText();
		if (cod.matches("^[nNtTmM][Ss][0-9]{2}$") == false) {
			return false;
		}
		return true;
	}

	private boolean validarCodDisciplina() {
		String cod = codigoDisciplina.getText();
		if (cod.matches("^[a-zA-Z]{3}\\d{4}$") == false) {
			return false;
		}
		return true;
	}

	public void limparTela() {
		codigoNovaTurma.setText("");
		codigoDisciplina.setText("");
		quantidadeAlunos.setText("");
		diasNovoTurma.setValue(null);
		horarioNovoTurma.setValue(null);
		diasNovoTurma.getItems().clear();
	}

	public void adicionar(Turma NovoTurma) {
		File tempFile = new File("c:\\temp\\ListaTurma.ser");
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
			lista.add(NovoTurma);
			System.out.println(NovoTurma);
			FileOutputStream fout = new FileOutputStream("c:\\temp\\ListaTurma.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(lista);
			oos.close();
			limparTela();
			Alerts.showAlerts("Sucesso", null, "Turma cadastrada!", AlertType.CONFIRMATION);

		} catch (IOException e) {
			System.out.println("Error de escrita: " + e.getMessage());
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.addTurma.setVisible(false);
		view.util.Constraints.setTextMaxLength(codigoNovaTurma, 4);
		view.util.Constraints.setTextMaxLength(codigoDisciplina, 7);
		this.b1.setDisable(true);
		horarioNovoTurma.getItems().add("AB");
		horarioNovoTurma.getItems().add("CD");
		horarioNovoTurma.getItems().add("EF");
		horarioNovoTurma.getItems().add("GH");
		horarioNovoTurma.getItems().add("IJ");
		horarioNovoTurma.getItems().add("LM");
		horarioNovoTurma.getItems().add("NO");
		horarioNovoTurma.getItems().add("PQ");

	}

}
