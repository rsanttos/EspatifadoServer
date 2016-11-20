package br.com.ufrn.bti.controle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.ufrn.bti.dominio.Pessoa;
import br.com.ufrn.bti.dominio.Usuario;
import br.com.ufrn.bti.servico.PessoaService;
import br.com.ufrn.bti.servico.UsuarioService;

@ManagedBean
public class UsuarioMBean {
	
	/**
	 * Service de Usuário que persistirá os dados da entidade Usuário 
	 */
	private UsuarioService usuarioService;
	
	/**
	 * Service de Pessoa que persistirá os dados da entidade Pessoa
	 */
	private PessoaService pessoaService;
	
	/**
	 * Objeto da classe pessoa que irá compor o objeto Usuário
	 */
	private Pessoa pessoa;
	
	private Usuario usuario;

	private DataModel<Usuario> listagem;
	
	
	/**
	 * Construtor da entidade
	 */
	public UsuarioMBean() {
		usuario = new Usuario();
		pessoa = new Pessoa();
		pessoaService = new PessoaService();
		usuarioService = new UsuarioService();
	}
	
	/**
	 * @return the usuarioService
	 */
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @return the pessoaService
	 */
	public PessoaService getPessoaService() {
		return pessoaService;
	}

	/**
	 * @param pessoaService the pessoaService to set
	 */
	public void setPessoaService(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
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
	 * Retorna o diretório padrão
	 */
	protected String getDir() {
		return "/pages/usuario";
	}
	
	/**
	 * Método que inicializa o objeto do MBean
	 */
	public String novo() {
		usuario = new Usuario();
		return getDir() + "/form.jsf";
	}
	
	/**
	 * Método que representa a ação de redirecionamento do usuário para alteração
	 */
	public String alterar() {
		usuario = (Usuario)listagem.getRowData();
		return getDir() + "/form.jsf";
	}
	
	/**
	 * Método que representa a ação remover.
	 */
	public void remover(){
		usuario = (Usuario)listagem.getRowData();
		usuarioService.remover(usuario);
		FacesContext ctx = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário removido!",
				"Usuário deletado com sucesso.");
		ctx.addMessage(null, msg);
		listagem = null;
	}
	
	
	
	
	
	public String salvarUsuario(){
		
		pessoaService.salvarOuAtualizar(usuario.getPessoa());
		
		usuarioService.salvarOuAtualizar(usuario);
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário adicionado!",
				"Usuário adicionado com sucesso.");
		ctx.addMessage(null, msg);
		
		return getDir() + "/list.jsf";
	}
	
	public String listar() {
		listagem = new ListDataModel<Usuario>(usuarioService.listar());
		return getDir() + "/list.jsf";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setListagem(DataModel<Usuario> listagem) {
		this.listagem = listagem;
	}

	public DataModel<Usuario> getListagem() {
		listagem = new ListDataModel<Usuario>(usuarioService.listar());
		return listagem;
	}	

	public String realizaLogin(){
		
		Usuario usuarioAux = new Usuario();
		
		usuarioAux = usuarioService.getByLogin(usuario.getLogin());
		
		if(usuarioAux != null){
			if(usuarioAux.getSenha() == usuario.getSenha()){

				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("USUARIO_LOGADO", usuario);
				
				return "/pages/musica/list.jsf";
			}
		}
		
		return "/pages/musica/list.jsf";
	}
	
	public Usuario getUsuarioLogado(){
		Usuario usuarioLogado = new Usuario();
		
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = (HttpSession) request.getSession();

		usuarioLogado = (Usuario) session.getAttribute("USUARIO_LOGADO");
		
		return usuarioLogado;
	}
}

