package br.com.ufrn.bti.controle;

import javax.faces.bean.ManagedBean;

import br.com.ufrn.bti.dominio.Produtora;
import br.com.ufrn.bti.servico.ProdutoraService;

@ManagedBean
public class ProdutoraMBean {
	private Produtora produtora;
	private ProdutoraService produtoraService;
	
	public ProdutoraMBean(){
		produtoraService = new ProdutoraService();
		produtora = new Produtora();
		produtora = produtoraService.buscarPeloNome("Espatifado");
	}

	public Produtora getProdutora() {
		return produtora;
	}

	public void setProdutora(Produtora produtora) {
		this.produtora = produtora;
	}

	public ProdutoraService getProdutoraService() {
		return produtoraService;
	}

	public void setProdutoraService(ProdutoraService produtoraService) {
		this.produtoraService = produtoraService;
	}
	
	public double getCaixaProdutora(){
		return produtora.getCaixa();
	}
	
	public void realizaDeposito(double valor){
		double novoValor;
		novoValor = produtora.getCaixa() + valor;
		produtora.setCaixa(novoValor);
		produtoraService.salvarOuAtualizar(produtora);
	}
	
}
