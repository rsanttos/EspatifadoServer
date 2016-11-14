package br.com.ufrn.bti.concorrente.espatifado.server.servico;

import java.util.ArrayList;
import java.util.List;

import br.com.ufrn.bti.concorrente.espatifado.server.dao.MusicaDAO;
import br.com.ufrn.bti.concorrente.espatifado.server.dominio.Musica;

public class MusicaService {

	private MusicaDAO musicaDao;
	
	public MusicaService(){
		musicaDao = new MusicaDAO();
	}
	
	public void salvarOuAtualizar(Musica musica){
		musicaDao.salvarOuAtualizar(musica);
	}
	
	public void remover(Musica musica){
		musicaDao.deletar(musica);
	}
	
	public List<Musica> listar(){
		List<Musica> musicas = new ArrayList<Musica>();
		musicas = musicaDao.listar();
		return musicas;
	}
}
