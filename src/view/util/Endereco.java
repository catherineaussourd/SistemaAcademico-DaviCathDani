package view.util;

import java.io.Serializable;

public class Endereco implements Serializable {
	private String Rua;
	private String Numero;
	private String Cidade;
	private String CEP;
	private String Bairro;
	
	
	
	public Endereco() {
		
	}
	
	public Endereco(String rua, String numero, String cidade) {
		super();
		Rua = rua;
		Numero = numero;
		Cidade = cidade;
	}

	public Endereco(String rua, String numero, String cidade, String cEP, String bairro) {
		super();
		Rua = rua;
		Numero = numero;
		Cidade = cidade;
		CEP = cEP;
		Bairro = bairro;
	}
	public String getRua() {
		return Rua;
	}
	public void setRua(String rua) {
		Rua = rua;
	}
	public String getNumero() {
		return Numero;
	}
	public void setNumero(String numero) {
		Numero = numero;
	}
	public String getCidade() {
		return Cidade;
	}
	public void setCidade(String cidade) {
		Cidade = cidade;
	}
	public String getCEP() {
		return CEP;
	}
	public void setCEP(String cEP) {
		CEP = cEP;
	}
	public String getBairro() {
		return Bairro;
	}
	public void setBairro(String bairro) {
		Bairro = bairro;
	}

	@Override
	public String toString() {
		return "Endereco [Rua=" + Rua + ", Numero=" + Numero + ", Cidade=" + Cidade + ", CEP=" + CEP + ", Bairro="
				+ Bairro + "]";
	}
	
	

}
