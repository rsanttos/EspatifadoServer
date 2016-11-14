package br.com.ufrn.bti.concorrente.espatifado.server.dominio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.dom4j.tree.AbstractEntity;

import com.google.gson.annotations.SerializedName;

@Entity
public class Usuario extends AbstractEntity{
	
	private static final long serialVersionUID = -1370492530379526895L;

	/**
	 * ID da entidade
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_USUARIO")
	@SequenceGenerator(name="SEQ_USUARIO", sequenceName="id_seq_usuario", allocationSize=1)
	@Column(name="id_usuario")
	@SerializedName(value = "idUsuario")
	private int id;
	
	/**
	 * Login do usuário no sistema
	 */
	private String login;
	
	/**
	 * Senha de acesso do usuário no sistema
	 */
	private String senha;
	
	
	/**
	 * Pessoa que é o usuário no sistema
	 * Relacionamento ORM
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;
 	
 	/**
 	 * 
 	 * Atributo que representa se um usuário está ativo ou não no sistema
 	 * 
 	 */
	@Column(columnDefinition = "boolean default true", insertable = false, updatable = true)
	@SerializedName(value = "ativoUsuarioExterno")
	private boolean ativo;

	/**
	 * 
	 * Musicas que um usuário pode ter no sistema
	 * 
	 */
 	@ManyToMany(fetch=FetchType.EAGER)
   	@JoinTable(name="usuario_musica",joinColumns = @JoinColumn(name="id_usuario"), inverseJoinColumns = @JoinColumn(name="id_musica"))
	private List<Musica> musicas;
	
	
 	/**
 	 * Construtor da classe
 	 */
 	public Usuario() {
		pessoa = new Pessoa();
	}
 	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}


	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}


	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}


	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}


	/**
	 * @return the pessoa
	 */
	public Pessoa getPessoa() {
		return pessoa;
	}


	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	/**
	 * @return the ativo
	 */
	public boolean isAtivo() {
		return ativo;
	}


	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	public List<Musica> getMusicas() {
		return musicas;
	}


	public void setMusicas(List<Musica> musicas) {
		this.musicas = musicas;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * Método que verifica se um objeto da entidade é igual ao objeto recebido como parâmetro
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}