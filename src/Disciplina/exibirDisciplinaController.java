package Disciplina;

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
import view.util.Disciplina;

public class exibirDisciplinaController implements Initializable{

	
	@FXML
	AnchorPane Disciplina;
	@FXML
	TextField nomeDisciplina;
	@FXML
	TextField codigoDisciplina;
	
	@FXML
	private ChoiceBox<String> cargaHorariaNovoDisciplina;
	@FXML
	private ChoiceBox<String> creditosNovoDisciplina;
	
	@FXML
	Button searchDisciplina;
	
	@FXML
	public void buscarDisciplina(ActionEvent event) {
		LinkedList<Disciplina> listaDisciplinas = Lista();
		String codigoProcurar = codigoDisciplina.getText();
		int resultado = buscar(codigoProcurar, listaDisciplinas);
		System.out.println("INDEX DO Codigo: " + resultado);
		if (resultado != -1) {
			Disciplina.setVisible(true);
			carregarDados(resultado, listaDisciplinas);
		}else {
			Alerts.showAlerts("Error", null, "Disciplina não encontrada", AlertType.ERROR);
		}

	}
	
	
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
	
	public int buscar(String codigo, LinkedList<Disciplina> lista) {
		System.out.println("codigo PROCURADO:" + codigo);
		System.out.println("--------------");
		for (Disciplina disciplina : lista) {
			System.out.println("Comparar:" + disciplina.getCodigo() + " e: " + codigo);
			if (disciplina.getCodigo().compareToIgnoreCase(codigo) == 0) {

				return lista.indexOf(disciplina);
			}
		}
		return -1;
	}
	
	
	public void carregarDados(int index, LinkedList<Disciplina> lista) {

		Disciplina disciplina = lista.get(index);
		String cargaHoraria = disciplina.getCargaHoraria();
		String creditosHoraria = disciplina.getCreditos();
		nomeDisciplina.setText(disciplina.getNome());
		cargaHorariaNovoDisciplina.setValue(cargaHoraria);
		creditosNovoDisciplina.setValue(creditosHoraria);
		desabilitar();

	}

	private void desabilitar() {
		nomeDisciplina.setDisable(true);
		cargaHorariaNovoDisciplina.setDisable(true);
		creditosNovoDisciplina.setDisable(true);
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
		cargaHorariaNovoDisciplina.getItems().add("30h");
		cargaHorariaNovoDisciplina.getItems().add("60h");
		creditosNovoDisciplina.getItems().add("2");
		creditosNovoDisciplina.getItems().add("4");
		Disciplina.setVisible(false);
		view.util.Constraints.setTextMaxLength(codigoDisciplina, 7);
	}
	
	
	
	
	
}
