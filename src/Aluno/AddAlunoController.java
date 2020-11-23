package Aluno;

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

import javax.swing.SpringLayout.Constraints;

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
import view.util.Disciplina;
import view.util.Endereco;

public class AddAlunoController implements Serializable, Initializable {

    @FXML
    AnchorPane addAluno;
    @FXML
    TextField nomeNovoAluno;
    @FXML
    TextField cpfNovoAluno;
    @FXML
    TextField telefoneNovoAluno;
    @FXML
    TextField emailNovoAluno;
    @FXML
    TextField ruaNovoAluno;
    @FXML
    TextField numeroNovoAluno;
    @FXML
    TextField cepNovoAluno;
    @FXML
    TextField cidadeNovoAluno;
    @FXML
    TextField bairroNovoAluno;
    @FXML
    private ChoiceBox<String> sexoNovoAluno;
    @FXML
    private Button b1;
    @FXML
    DatePicker dataNascimentoNovoAluno;

    @FXML
    public void adicionarAluno(ActionEvent event) {
        this.addAluno.setVisible(true);

    }

    public void salvarAluno(ActionEvent event) {

        String validacao = validarTela();

        if (validacao.equalsIgnoreCase("")) {
            salvarNovoAluno();
        } else {
            Alerts.showAlerts("Error", null, validacao, AlertType.ERROR);
        }
    }

    public static int buscar(String codigo, LinkedList<Aluno> lista) {
        System.out.println("codigo PROCURADO:" + codigo);
        System.out.println("--------------");
        for (Aluno aluno : lista) {
            System.out.println("Comparar:" + aluno.getCpf() + " e: " + codigo);
            if (aluno.getCpf().compareToIgnoreCase(codigo) == 0) {

                return lista.indexOf(aluno);
            }
        }
        return -1;
    }

    private String validarTela() {
        String resposta = "";
        String cpfCadastrado = "";
        String cpfAluno = "";
        String sexoAluno = "";
        String DataNascimentoAluno = "";
        String email = "";
        String telefone = "";

        if (isCPF(cpfNovoAluno.getText()) == false) {
            cpfAluno = "\n Verifique o CPF informado!";
        }
        if (buscar(cpfNovoAluno.getText(), leitorListas.ListaAlunos()) != -1) {
            cpfCadastrado = "\n CPF j� Cadastrado!!";
        }
        if (sexoNovoAluno.getValue() == null) {
            sexoAluno = "\n Verifique o Sexo Informado!";
        }
        if (dataNascimentoNovoAluno.getValue() == null) {
            DataNascimentoAluno = "\n Verifique a Data de Nascimento informada!";
        }
        if (validaEmail() == false) {
            email = "\n Verifique o Email informado!";
        }
        if (validarTelefone() == false) {
            telefone = "\n Verifique o Telefone informado!";
        }

        resposta = cpfCadastrado + cpfAluno + sexoAluno + DataNascimentoAluno + email + telefone;
        return resposta;

    }

    private boolean validarTelefone() {
        String telefone = telefoneNovoAluno.getText();
        if (telefone.length() < 10) {
            return false;
        }
        return true;
    }

    public boolean validaEmail() {
        String email = emailNovoAluno.getText();
        if (email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$") == false) {
            return false;
        }
        return true;
    }

    public void salvarNovoAluno() {
        System.out.println("come�ou a salvar");
        String nome = nomeNovoAluno.getText();
        nome = nome.trim().replaceAll("\\s+", " ");
        String cpf = cpfNovoAluno.getText();
        LocalDate ld = dataNascimentoNovoAluno.getValue();
        String DataNascimento = ld.toString();
        String sexo = sexoNovoAluno.getValue();
        String email = emailNovoAluno.getText().trim();
        String rua = ruaNovoAluno.getText().trim();
        String numero = numeroNovoAluno.getText();
        String CEP = cepNovoAluno.getText();
        String cidade = cidadeNovoAluno.getText().trim();
        String Bairro = bairroNovoAluno.getText().trim();
        String telefone = telefoneNovoAluno.getText();
        System.out.println("DEU LOAD");
        Endereco enderecoNovoAluno = new Endereco(rua, numero, cidade, CEP, Bairro);
        Aluno novoAluno = new Aluno(nome, cpf, DataNascimento, sexo, telefone, email, enderecoNovoAluno);
        adicionar(novoAluno);
    }

    public void limparTela() {
        nomeNovoAluno.setText("");
        cpfNovoAluno.setText("");
        sexoNovoAluno.setValue(null);
        dataNascimentoNovoAluno.setValue(null);
        emailNovoAluno.setText("");
        ruaNovoAluno.setText("");
        numeroNovoAluno.setText("");
        cepNovoAluno.setText("");
        cidadeNovoAluno.setText("");
        bairroNovoAluno.setText("");
        telefoneNovoAluno.setText("");
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
            limparTela();
            Alerts.showAlerts("Sucesso", null, "Aluno cadastrado!", AlertType.CONFIRMATION);

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
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
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
        sexoNovoAluno.getItems().add("Homem");
        sexoNovoAluno.getItems().add("Mulher");
        sexoNovoAluno.getItems().add("Outros");
        view.util.Constraints.setTextFieldInteger(telefoneNovoAluno);
        view.util.Constraints.setTextFieldInteger(cpfNovoAluno);
        view.util.Constraints.setTextFieldInteger(cepNovoAluno);
        view.util.Constraints.setTextFieldInteger(numeroNovoAluno);
        view.util.Constraints.setTextMaxLength(telefoneNovoAluno, 11);
        view.util.Constraints.setTextMaxLength(cpfNovoAluno, 11);
        view.util.Constraints.validateText(nomeNovoAluno);

    }

}
