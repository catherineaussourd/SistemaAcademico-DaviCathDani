package view.util;

import java.io.Serializable;

public class Disciplina implements Serializable {
	private String nome;
	private String codigo;
	private String cargaHoraria;
	private String creditos;

	public Disciplina() {
	}

	public Disciplina(String nome, String codigo, String cargaHoraria, String creditos) {
		this.nome = nome;
		this.codigo = codigo;
		this.cargaHoraria = cargaHoraria;
		this.creditos = creditos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	@Override
	public String toString() {
		return "Disciplina [nome=" + nome + ", codigo=" + codigo + ", cargaHoraria=" + cargaHoraria + ", creditos="
				+ creditos + "]";
	}
	
	
	

}
