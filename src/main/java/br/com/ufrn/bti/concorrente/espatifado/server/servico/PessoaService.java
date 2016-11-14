package br.com.ufrn.bti.concorrente.espatifado.server.servico;

import java.util.List;

import br.com.ufrn.bti.concorrente.espatifado.server.dao.PessoaDAO;
import br.com.ufrn.bti.concorrente.espatifado.server.dominio.Pessoa;

public class PessoaService {

	/**
	 * Objeto DAO com consultas específicas da entidade Pessoa
	 */
	private PessoaDAO pessoaDao;
	
	public PessoaService(){
		pessoaDao = new PessoaDAO();
	}
	
	/**
	 * Método que efetua a INATIVAÇÃO do objeto da entidade Pessoa
	 * @throws NegocioException 
	 */
	public void remover(Pessoa obj){
		//A pessoa nunca será removida, apenas desativada
		obj.setAtivo(false);
		pessoaDao.salvarOuAtualizar(obj);
	}
	
	/**
	 * Método que busca o objeto de Pessoa a partir do CPF recebido como parâmetro
	 * 
	 * @param cpf
	 * @return
	 */
	public Integer buscarCpf(String cpf){
		return pessoaDao.getCpf(cpf);
	}
	
	/**
	 * Método que busca o objeto de Pessoa a partir do CPF recebido como parâmetro
	 * 
	 * @param cpf
	 * @return
	 */
	public Pessoa buscarPessoaByCpf(String cpf){
		return pessoaDao.buscarPessoaByCpf(cpf);
	}
	
	/**
	 * Seleciona uma pessoa de acordo com o CPF.
	 * 
	 * @param Pessoa com cpf preenchido.
	 * @return
	 */
	public Pessoa findByCpf(Pessoa pessoa){
		return pessoaDao.findByCpf(pessoa);
	}
	
	/**
	 * Seleciona uma pessoa de acordo com o nome e retorna um unico resultado
	 * 
	 * @param Pessoa com nome preenchido
	 * @return
	 */
	public Pessoa buscarPorEmail(Pessoa pessoa){
		return pessoaDao.buscarPorEmail(pessoa);
	}

	/**
	 * Método que busca o objeto de Pessoa a partir do nome recebido como parâmetro
	 * 
	 * @param nome
	 * @return
	 */
	public List<Pessoa> buscarPorNome(Pessoa pessoa) {
		return pessoaDao.buscarPorNome(pessoa);
	}
	
	public void salvarOuAtualizar(Pessoa obj){
		pessoaDao.salvarOuAtualizar(obj);
	}

	public void atualizar(Pessoa obj){
		pessoaDao.salvarOuAtualizar(obj);
	}
	
	/***
	 * Método que verifica se já existe alguém com o mesmo cpf cadastrado no banco de dados
	 * @param obj
	 * @return Retorna true se  o cpf já estiver cadastrado no banco
	 */
	private boolean existePessoa(Pessoa pessoa) {
		return findByCpf(pessoa) != null;
	}
	
}
