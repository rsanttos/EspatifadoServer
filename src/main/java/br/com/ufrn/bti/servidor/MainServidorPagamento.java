package br.com.ufrn.bti.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.ufrn.bti.comunicacao.conteudo.ConteudoRequisicaoPagamento;
import br.com.ufrn.bti.dominio.Produtora;
import br.com.ufrn.bti.servico.ProdutoraService;

public class MainServidorPagamento {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		int porta = 6791;

		ServerSocket escuta = new ServerSocket(porta);

		System.out.println("*** Servidor ***");
		System.out.println("*** Porta de escuta (listen): " + porta);

		while (true) {
			Socket cliente = escuta.accept();
			System.out.println("*** conexao aceita de (remoto): " + cliente.getRemoteSocketAddress());

			ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

			ConteudoRequisicaoPagamento conteudo = (ConteudoRequisicaoPagamento) ois.readObject();
			
			Socket socketBTICard = new Socket("localhost", 6792);
			
			ObjectOutputStream btiCardOS = new ObjectOutputStream(socketBTICard.getOutputStream());
			btiCardOS.writeObject(conteudo);

			ObjectInputStream btiCardIS = new ObjectInputStream(socketBTICard.getInputStream());
			boolean deuCerto = (Boolean) btiCardIS.readObject();
						
			System.out.println("Resposta do servidor");
			System.out.println(deuCerto);
			
			if(deuCerto){
				ProdutoraService produtoraService = new ProdutoraService();
				
				Produtora p = new Produtora();
				p = produtoraService.buscarPeloNome("Espatifado");  
				
				double novoValor = new ProdutoraService().verificaCaixa(p);
				
				novoValor += conteudo.getValorPagamento();
				
				p.setCaixa(novoValor);
				
				produtoraService.salvarOuAtualizar(p);
			}

			ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
			oos.writeObject(deuCerto);
		}
	}

}
