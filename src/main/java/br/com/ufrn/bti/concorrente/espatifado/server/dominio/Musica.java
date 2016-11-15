package br.com.ufrn.bti.concorrente.espatifado.server.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.dom4j.tree.AbstractEntity;

@Entity
public class Musica  extends AbstractEntity{
	
	private static final long serialVersionUID = -1370492530379526895L;

	/**
	 * ID da entidade
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MUSICA")
	@SequenceGenerator(name="SEQ_MUSICA", sequenceName="id_seq_musica", allocationSize=1)
	@Column(name="id_musica")
	private int id;
	
	private String nome;
	
	private String banda;
	
	private String caminhoArquivo;
	
	private double preco;
	
	private double tamanho;
	
	public Musica(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getBanda() {
		return banda;
	}

	public void setBanda(String banda) {
		this.banda = banda;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getTamanho() {
		return tamanho;
	}

	public void setTamanho(double tamanho) {
		this.tamanho = tamanho;
	}
	
	
	
}
