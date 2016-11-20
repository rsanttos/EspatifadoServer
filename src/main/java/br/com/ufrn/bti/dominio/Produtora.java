package br.com.ufrn.bti.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.dom4j.tree.AbstractEntity;

@Entity
public class Produtora extends AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5731950556298724095L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PRODUTORA")
	@SequenceGenerator(name="SEQ_PRODUTORA", sequenceName="id_seq_produtora", allocationSize=1)
	@Column(name="id_produtora")
	private int id;
	private String nome;
	private double caixa;
	
	public Produtora(){
		
	}

	public Produtora(String nome) {
		super();
		this.nome = nome;
		this.caixa = 0.0;
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

	public double getCaixa() {
		return caixa;
	}

	public void setCaixa(double caixa) {
		this.caixa = caixa;
	}	
	
}
