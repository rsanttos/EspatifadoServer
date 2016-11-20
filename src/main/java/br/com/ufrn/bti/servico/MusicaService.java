package br.com.ufrn.bti.servico;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import br.com.ufrn.bti.dao.MusicaDAO;
import br.com.ufrn.bti.dominio.Musica;
import br.com.ufrn.bti.util.JSONProcessor;

public class MusicaService {

	private MusicaDAO musicaDao;

	public MusicaService() {
		musicaDao = new MusicaDAO();
	}

	public void salvarOuAtualizar(Musica musica) {
		musicaDao.salvarOuAtualizar(musica);
	}

	public void remover(Musica musica) {
		musicaDao.deletar(musica);
	}

	public List<Musica> listar() {
		List<Musica> musicas = new ArrayList<Musica>();
		musicas = musicaDao.listar();
		return musicas;
	}

	public void upload(Musica musica, Part part) throws IOException {
		String nomeArquivo = getFileName(part);
		musica.setCaminhoArquivoServidor("/home/inacio-medeiros/Music/servidor/" + nomeArquivo);
		musica.setCaminhoArquivoCliente("/home/inacio-medeiros/Music/cliente/" + nomeArquivo);
		musica.setTamanho(part.getSize());
		realizaUpload(part);
		musicaDao.salvarOuAtualizar(musica);
		
		byte[] array = Files.readAllBytes(new File(musica.getCaminhoArquivoServidor()).toPath());
		
		Socket s = null;

		try {
			s = new Socket("localhost", 5678);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			
			oos.writeObject(musica.getCaminhoArquivoCliente());
			oos.writeObject(array);
		} catch (UnknownHostException e) {
			System.out.println("!!! Servidor Desconhecido: " + e.getMessage());
		} catch (EOFException e) {
			System.out.println("!!! Não há mais dados de entrada: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("!!! E/S: " + e.getMessage());
		/*} catch (ClassNotFoundException e) {
			System.out.println("!!! Erro no recebimento de mensagem: " + e.getMessage());*/
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("!!! Encerramento do socket falhou: " + e.getMessage());
				}
			}
		}
	}

	public void realizaUpload(Part arquivo) throws IOException {
		String fileName = getFileName(arquivo);
		String filePath = "/home/inacio-medeiros/Music/servidor/";

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

	public String getMusicasJson() {
		String json = new String();

		json = JSONProcessor.toJSON(this.listar());

		return json;
	}
}
