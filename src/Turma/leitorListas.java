package Turma;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.InputMismatchException;
import java.util.LinkedList;

import view.util.Aluno;
import view.util.Disciplina;
import view.util.Professor;
import view.util.Turma;

public class leitorListas {

	@SuppressWarnings("unchecked")
	public static LinkedList<Disciplina> ListaDisciplina() {
		LinkedList<Disciplina> lista = new LinkedList<Disciplina>();
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

	@SuppressWarnings("unchecked")
	public static LinkedList<Professor> ListaProfessor() {
		LinkedList<Professor> lista = new LinkedList<Professor>();;
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

	@SuppressWarnings("unchecked")
	public static LinkedList<Aluno> ListaAlunos() {
		LinkedList<Aluno> lista = new LinkedList<Aluno>();
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

	@SuppressWarnings("unchecked")
	public static LinkedList<Turma> ListaTurmas() {
		LinkedList<Turma> lista = new LinkedList<Turma>();
		try {
			FileInputStream fin = new FileInputStream("c:\\temp\\ListaTurma.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			lista = ((LinkedList<Turma>) ois.readObject());
			ois.close();
			return lista;
		} catch (IOException e) {
			return lista;
		} catch (ClassNotFoundException e) {
			System.out.println("Error de leitura: " + e.getMessage());
		}
		return lista;
	}

	public static int buscar(String codigo, LinkedList<Disciplina> lista) {
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
}