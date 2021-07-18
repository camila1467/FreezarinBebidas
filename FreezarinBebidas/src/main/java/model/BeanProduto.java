package model;

import java.io.Serializable;

/*
 * Classe BeanProduto
 * Classe Que Provê o Modelo de Objeto
 */
public class BeanProduto  implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private int quantidade;
	private float valor;
	
	
public boolean isNovo() {
		
		if (this.id == null)/*pega o atributo da classe*/ {
			return true; /*Inserir novo*/
		}else if (this.id != null && this.id > 0) {
			return false; /*Atualizar*/
		}
			
		
		return id == null;
	}

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public float getValor() {
		return this.valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	
	
}
