package br.com.ufrn.bti.concorrente.espatifado.server.controle;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.Part;

import br.com.ufrn.bti.concorrente.espatifado.server.dominio.Musica;
import br.com.ufrn.bti.concorrente.espatifado.server.servico.MusicaService;

@ManagedBean
public class MusicaMBean {

	private MusicaService musicaService;
	
	private Musica musica;

	private Part part;

	private DataModel<Musica> listagem;
	
	public MusicaMBean(){
		musica = new Musica();
		musicaService = new MusicaService();
	}

	public String getDir() {
		return "/pages/musica";
	}
	
	/**
	 * Método que inicializa o objeto do MBean
	 */
	public String novo() {
		musica = new Musica();
		return getDir() + "/form.jsf";
	}
	
	/**
	 * Método que representa a ação remover.
	 */
	public void remover(){
		musica = (Musica)listagem.getRowData();
		musicaService.remover(musica);
		FacesContext ctx = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Música removida!",
				"Música deletada com sucesso.");
		ctx.addMessage(null, msg);
		listagem = null;
	}
	
	public String listar() {
		listagem = new ListDataModel<Musica>(musicaService.listar());
		return getDir() + "/list.jsf";
	}
	
	
	public String salvar(){
		
		musicaService.salvarOuAtualizar(musica);
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário adicionado!",
				"Usuário adicionado com sucesso.");
		ctx.addMessage(null, msg);
		
		return getDir() + "/list.jsf";
	}
	
	public String upload() throws IOException{
		musicaService.upload(musica, part);
		musica = new Musica();
		return getDir() + "/list.jsf";
	}
	
	public MusicaService getMusicaService() {
		return musicaService;
	}

	public void setMusicaService(MusicaService musicaService) {
		this.musicaService = musicaService;
	}

	public Musica getMusica() {
		return musica;
	}

	public void setMusica(Musica musica) {
		this.musica = musica;
	}

	public DataModel<Musica> getListagem() {
		listagem = new ListDataModel<Musica>(musicaService.listar());
		return listagem;
	}

	public void setListagem(DataModel<Musica> listagem) {
		this.listagem = listagem;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}
	
}
