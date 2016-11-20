package br.com.ufrn.bti.servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.ufrn.bti.servico.MusicaService;

public class MainServidorListagemMusicas {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		int porta = 6790;

		ServerSocket escuta = new ServerSocket(porta);

		System.out.println("*** Servidor ***");
		System.out.println("*** Porta de escuta (listen): " + porta);

		while (true) {
			Socket cliente = escuta.accept();
			System.out.println("*** conexao aceita de (remoto): " + cliente.getRemoteSocketAddress());
			
			String musicasJSON = new MusicaService().getMusicasJson();
			
			System.out.println("Resposta do servidor");
			System.out.println(musicasJSON);

			ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
			oos.writeObject(musicasJSON);
		}
	}

}
