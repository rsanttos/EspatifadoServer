package br.com.ufrn.bti.comunicacao.conteudo;

import java.io.Serializable;

import br.com.ufrn.bti.dominio.Usuario;

public class ConteudoRequisicaoPagamento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8067702631588115629L;
	private Usuario pessoa;
	private double valorPagamento;

	public ConteudoRequisicaoPagamento(Usuario pessoa, double valorPagamento) {
		this.pessoa = pessoa;
		this.valorPagamento = valorPagamento;
	}

	public ConteudoRequisicaoPagamento() {
		// TODO Auto-generated constructor stub
	}

	public Usuario getUsuario() {
		return pessoa;
	}

	public void setUsuario(Usuario pessoa) {
		this.pessoa = pessoa;
	}

	public double getValorPagamento() {
		return valorPagamento;
	}

	public void setValorPagamento(double valorPagamento) {
		this.valorPagamento = valorPagamento;
	}

}
