package br.com.ufrn.bti.dao;

import org.hibernate.Query;

import br.com.ufrn.bti.dominio.Produtora;
import br.com.ufrn.bti.util.HibernateUtil;

public class ProdutoraDAO extends GenericDAO {
	public ProdutoraDAO(){
		
	}
	

	@SuppressWarnings("unchecked")
	public Produtora buscarPeloNome(String nome){
		Produtora p = new Produtora();
		session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("SELECT p FROM Produtora p WHERE p.nome = :nome");
        q.setString("nome", nome);
        p = (Produtora) q.uniqueResult();
        session.close();
        return p;
	}
	

	@SuppressWarnings("unchecked")
	public double verificaCaixa(Produtora p){
		double caixa;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("SELECT p.caixa FROM Produtora p WHERE p.nome = :nome");
        q.setString("nome", p.getNome());
        caixa = (double) q.uniqueResult();
        session.close();
        return caixa;
	}
}
