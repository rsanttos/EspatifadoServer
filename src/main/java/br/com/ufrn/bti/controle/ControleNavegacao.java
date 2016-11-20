package br.com.ufrn.bti.controle;

import javax.faces.bean.ManagedBean;

import br.com.ufrn.bti.dominio.Usuario;

@ManagedBean
public class ControleNavegacao {
	

	private Usuario usuario;

	/**
	 * Método responsável por verificar se o usuário é administrador do sistema
	 * 
	 * @author ramon
	 *
	 */

	public ControleNavegacao() {
		usuario = new Usuario();
	}
	
	public String visualizarDashboard(){
		return "/pages/dashboard.jsf";
	}
	
	public String visualizarMusicas(){
		return "/pages/musica/list.jsf";
	}
	
	public String visualizarUsuarios(){
		return "/pages/usuario/list.jsf";
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
