package br.com.ufrn.bti.concorrente.espatifado.server.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServidor {

	public static void main(String[] args) {
		int SAIDA_COM_ERRO = 1;
		int PORT = 8888;
		
		ServerSocket listener = null;
		
		try {
			listener = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("ERRO: na inicialização do escutamento na porta " + PORT);
			e.printStackTrace();
			System.exit(SAIDA_COM_ERRO);
		}
		
		Socket socket = null;
		while(true){
			try {
				socket = listener.accept();
			} catch (IOException e) {
				System.out.println("ERRO: na recepção de comunicação com o cliente!");
				e.printStackTrace();
			}
			
			new Thread(new TratadorRequisicoes(socket)).start();
		}
	}

}
