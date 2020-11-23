package view.util;

import java.io.Serializable;
import java.util.Arrays;

public class Turma implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String codigoDisciplina;
	private String cpfProfessor;
	private int numeroMaxAlunos;
	private String[] alunos;
	private String horarioAulas;
	private String periodoLetivo;
	
	
	public Turma(String codigo,String codigoDisciplina, int numeroMaxAlunos, String horarioAulas, String periodoLetivo) {
		this.codigo = codigo;
		this.codigoDisciplina = codigoDisciplina;
		this.numeroMaxAlunos = numeroMaxAlunos;
		this.horarioAulas = horarioAulas;
		this.alunos = new String [this.numeroMaxAlunos]; 
		this.periodoLetivo=periodoLetivo;
	}


	public String getPeriodoLetivo() {
		return periodoLetivo;
	}


	public void setPeriodoLetivo(String periodoLetivo) {
		this.periodoLetivo = periodoLetivo;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getCodigoDisciplina() {
		return codigoDisciplina;
	}


	public void setCodigoDisciplina(String codigoDisciplina) {
		this.codigoDisciplina = codigoDisciplina;
	}


	public String getCpfProfessor() {
		return cpfProfessor;
	}


	public void setCpfProfessor(String cpfProfessor) {
		this.cpfProfessor = cpfProfessor;
	}


	public int getNumeroMaxAlunos() {
		return numeroMaxAlunos;
	}


	public void setNumeroMaxAlunos(int numeroMaxAlunos) {
		this.numeroMaxAlunos = numeroMaxAlunos;
	}


	public String[] getAlunos() {
		return alunos;
	}


	public void setAlunos(String[] alunos) {
		this.alunos = alunos;
	}


	public String getHorarioAulas() {
		return horarioAulas;
	}


	public void setHorarioAulas(String horarioAulas) {
		this.horarioAulas = horarioAulas;
	}


	@Override
	public String toString() {
		return "Turma [codigo=" + codigo + ", codigoDisciplina=" + codigoDisciplina + ", cpfProfessor=" + cpfProfessor
				+ ", numeroMaxAlunos=" + numeroMaxAlunos + ", alunos=" + Arrays.toString(alunos) + ", horarioAulas="
				+ horarioAulas + ", "+"Periodo= "+periodoLetivo+"]";
	}
	
	
	
}
