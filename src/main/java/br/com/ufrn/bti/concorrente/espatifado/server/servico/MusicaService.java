package br.com.ufrn.bti.concorrente.espatifado.server.servico;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import br.com.ufrn.bti.concorrente.espatifado.server.dao.MusicaDAO;
import br.com.ufrn.bti.concorrente.espatifado.server.dominio.Musica;
import br.com.ufrn.bti.concorrente.espatifado.server.util.JSONProcessor;

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
	
	public void upload(Musica musica, Part part) throws IOException{
		String nomeArquivo = getFileName(part);
		musica.setCaminhoArquivo("/Users/ramonsantos/bti/workspaces/concorrente_distribuida/EspatifadoFiles/" + nomeArquivo);
		musica.setTamanho(part.getSize());
		realizaUpload(part);
		musicaDao.salvarOuAtualizar(musica);
	}
	
    public void realizaUpload(Part arquivo) throws IOException {
        String fileName = getFileName(arquivo);
        String filePath = "/Users/ramonsantos/bti/workspaces/concorrente_distribuida/EspatifadoFiles/";

        File fileSaveDir = new File(filePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
       arquivo.write(filePath + File.separator + fileName);        
    }
    
    
	private String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
	
	public String getMusicasJson(){
		String json = new String();
		
		json = JSONProcessor.toJSON(this.listar());
		
		return json;
	}
}
