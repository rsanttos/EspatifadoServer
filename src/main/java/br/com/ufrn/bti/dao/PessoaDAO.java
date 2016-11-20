package br.com.ufrn.bti.dao;


import java.util.List;

import org.hibernate.Query;

import br.com.ufrn.bti.dominio.Pessoa;
import br.com.ufrn.bti.util.HibernateUtil;
 
public class PessoaDAO extends GenericDAO {
	
	/**
	 * Construtor que recebe o session factory do hibernate.
	 * @param sf
	 */
	public PessoaDAO() {
	}
	
	/**
	 * Método que busca uma pessoa a partir do CPF recebido como parâmetro
	 * @param cpf
	 * @return
	 */
	public Integer getCpf(String cpf){
		String hql = "select u from Usuario u Pessoa p WHERE p.id = u.pessoa.id AND u.pessoa.cpf= :cpf";
		Query query = HibernateUtil.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setString("cpf", cpf);
		return query.getFetchSize();
	}
	
	/**
	 * Método que busca uma pessoa a partir do CPF recebido como parâmetro
	 * @param cpf
	 * @return
	 */
	public Pessoa buscarPessoaByCpf(String cpf){
		String hql = "select p from Pessoa p  WHERE p.cpf= :cpf";
		Query query = HibernateUtil.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setString("cpf", cpf.replaceAll("\\D", ""));
		return (Pessoa)query.uniqueResult();
	}
	
	/**
	 * Método que busca uma pessoa a partir do nome recebido como parâmetro
	 * @param pessoa
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Pessoa> buscarPorNome(Pessoa pessoa) {
		String hql = "select p from Pessoa p where UPPER(p.nome) like UPPER(:nome)";
		Query query = HibernateUtil.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setString("nome", pessoa.getNome() + "%");
		return query.list();
	}

	/**
	 * Seleciona uma pessoa de acordo com o CPF.
	 * 
	 * @param Pessoa com cpf preenchido.
	 * @return
	 */
	public Pessoa findByCpf(Pessoa pessoa) {
		String hql = "select p from Pessoa p where p.cpf = :cpf";
		Query query = HibernateUtil.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setString("cpf", pessoa.getCpf().replaceAll("\\D", ""));
		return (Pessoa)query.uniqueResult();
	}
	/**
	 * Seleciona uma pessoa de acordo com o email e retorna um unico resultado
	 * 
	 * @param Pessoa com email preenchido
	 * @return
	 */
	public Pessoa buscarPorEmail(Pessoa pessoa) {
		String hql = "select p from Pessoa p where UPPER(p.email) like UPPER(:email)";
		Query query = HibernateUtil.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setString("email", pessoa.getEmail() + "%");
		return (Pessoa) query.uniqueResult();
	}
}
