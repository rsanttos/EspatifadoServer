package br.com.ufrn.bti.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.com.ufrn.bti.dominio.Musica;
import br.com.ufrn.bti.util.HibernateUtil;

public class MusicaDAO extends GenericDAO {

	/**
	 * Construtor que recebe o session factory do hibernate.
	 * 
	 * @param sf
	 */
	public MusicaDAO() {
	}

	/**
	 * Método que retorna a listagem de usuários
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Musica> listar() {	
		
		List<Musica> listaMusicas = new ArrayList<Musica>();
		session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
		listaMusicas = session.createQuery("SELECT m FROM Musica m").list();
		session.close();
		
		return listaMusicas;
	}


}