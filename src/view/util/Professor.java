package view.util;

import java.io.Serializable;

public class Professor implements Serializable{
	private String nome;
	private String cpf;
	private String dataNascimento;
	private String sexo;
	private String telefone;
	private String email;
	private Endereco endereco;
	
	public Professor(String nome, String cpf, String dataNascimento, String sexo, String telefone, String email,
			Endereco endereco) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}

	public Professor() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Professor: " + nome + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento + ", sexo=" + sexo
				+ ", telefone=" + telefone + ", email=" + email + ", endereco=" + endereco + "]";
	}
	
	


}
