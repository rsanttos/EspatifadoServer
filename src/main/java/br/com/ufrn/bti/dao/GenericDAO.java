package br.com.ufrn.bti.dao;

import org.hibernate.Session;

import br.com.ufrn.bti.util.HibernateUtil;

public class GenericDAO {

	public static Session session;

	public void salvarOuAtualizar(Object obj) {
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.getTransaction().commit();
		session.close();
	}
	
	public void deletar(Object obj){
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(obj);
		session.getTransaction().commit();
		session.close();
	}

}
