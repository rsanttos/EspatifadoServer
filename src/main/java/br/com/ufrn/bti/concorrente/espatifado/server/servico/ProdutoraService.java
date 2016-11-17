package br.com.ufrn.bti.concorrente.espatifado.server.servico;

import br.com.ufrn.bti.concorrente.espatifado.server.dao.ProdutoraDAO;
import br.com.ufrn.bti.concorrente.espatifado.server.dominio.Produtora;

public class ProdutoraService {
	private ProdutoraDAO produtoraDao;
	
	public ProdutoraService(){
		produtoraDao = new ProdutoraDAO();
	}

	public ProdutoraDAO getProdutoraDao() {
		return produtoraDao;
	}

	public void setProdutoraDao(ProdutoraDAO produtoraDao) {
		this.produtoraDao = produtoraDao;
	}
	
	public Produtora buscarPeloNome(String nome){
		Produtora p = new Produtora();
		p = produtoraDao.buscarPeloNome(nome);
		return p;
	}
	
	public double verificaCaixa(Produtora p){
		double caixa = produtoraDao.verificaCaixa(p);
		return caixa;
	}
	
	public void salvarOuAtualizar(Produtora p){
		produtoraDao.salvarOuAtualizar(p);
	}
}
