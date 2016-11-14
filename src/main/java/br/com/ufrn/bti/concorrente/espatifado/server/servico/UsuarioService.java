package br.com.ufrn.bti.concorrente.espatifado.server.servico;

import java.util.List;

import br.com.ufrn.bti.concorrente.espatifado.server.dao.UsuarioDAO;
import br.com.ufrn.bti.concorrente.espatifado.server.dominio.Pessoa;
import br.com.ufrn.bti.concorrente.espatifado.server.dominio.Usuario;

public class UsuarioService {
	
	/**
	 * Objeto DAO com consultas específicas da entidade Usuario
	 */
	private UsuarioDAO usuarioDao;
	
	/**
	 * Service da entidade Pessoa que irá realizar a persistência dos dados da entidade Pessoa
	 */
	private PessoaService pessoaService;
	
	public UsuarioService(){
		usuarioDao = new UsuarioDAO();
		pessoaService = new PessoaService();
	}
	
	
	/**
	 * Método que consulta e verifica se um usuário já existe a partir do CPF recebido como parâmetro
	 * 
	 * @param cpf
	 * @return
	 */
//	public Boolean existeUsuario(String cpf){
//		cpf = cpf.replaceAll("\\D", "");
//		if (usuarioDao.buscarPeloCPF(cpf) == null ) {
//			return false;
//		} else {
//			
//			return true;
//		}
//	}
	public Boolean existeLogin(String login){
		System.out.println(login);
 		if (usuarioDao.buscarPeloLogin(login) == null ) {
			return false;
		} else {
			
			return true;
		}
	}

	/**
	 * Método que consulta e retorna a listagem de objetos da entidade Usuario
	 * @return
	 */
	public List<Usuario> listar() {	
		return usuarioDao.listar();
	}

	/**
	 * Método que consulta e retorna o objeto da entidade Usuario a partir do login recebido como parâmetro
	 * 
	 * @param login
	 * @return
	 */
	public Usuario getByLogin(String login){
		return usuarioDao.buscarPeloLogin(login);
	}
	
	/**
	 * Método que consulta e retorna o objeto da entidade Usuario a partir do CPF recebido como parâmetro
	 * 
	 * @param cpf
	 * @return
	 */
//	public Usuario getUsuarioByCpf(String cpf){
//		return usuarioDao.buscarPeloCPF(cpf);
//	}
	
	/**
	 * Método que efetua a persistência dos dados pessoais do usuário recebido como parâmetro
	 * 
	 * @param login
	 * @return
	 */
	public void atualizarMeusDados(Usuario usuario){	
		pessoaService.salvarOuAtualizar(usuario.getPessoa());
		usuarioDao.salvarOuAtualizar(usuario);
	}
	
	public void salvarOuAtualizar(Usuario obj) {
		pessoaService.salvarOuAtualizar(obj.getPessoa());
		usuarioDao.salvarOuAtualizar(obj);
	}
	
	public void atualizar(Usuario obj) {
		pessoaService.atualizar(obj.getPessoa());
		usuarioDao.salvarOuAtualizar(obj);
	}
	
	
	/**
	 * Método que consulta e retorna o objeto da entidade Usuario a partir do id recebido como parâmetro
	 * 
	 * @param id
	 * @return
	 */
	public Usuario buscarPorId(int id){
		return usuarioDao.buscarPeloId(id);
	}
	
	public void remover(Usuario obj){
			obj.setAtivo(false);
			usuarioDao.salvarOuAtualizar(obj);
	}
}
