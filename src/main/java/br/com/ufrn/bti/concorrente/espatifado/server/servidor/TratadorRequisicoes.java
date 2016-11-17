package br.com.ufrn.bti.concorrente.espatifado.server.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.com.ufrn.bti.concorrente.espatifado.server.comunicacao.MensagemRequisicao;
import br.com.ufrn.bti.concorrente.espatifado.server.comunicacao.MensagemResposta;
import br.com.ufrn.bti.concorrente.espatifado.server.comunicacao.TipoMensagem;

public class TratadorRequisicoes implements Runnable {

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private MensagemRequisicao mensagem;
	
	public TratadorRequisicoes(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			this.inputStream = new ObjectInputStream(this.socket.getInputStream());
			this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("ERRO no estabelecimento da comunicação!");
			e.printStackTrace();
		}
		
		try {
			this.mensagem = (MensagemRequisicao) this.inputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("ERRO na leitura do objeto recebido!");
			e.printStackTrace();
		}
		
		MensagemResposta<String> mensagemResposta;
		
		if (this.mensagem.getTipoMensagem() != TipoMensagem.LISTA_MUSICAS) {
			return;
		} else if (this.mensagem.getTipoMensagem() != TipoMensagem.SOLICITA_COMPRA) {
			return;
		} else {
			mensagemResposta = new MensagemResposta<String>(TipoMensagem.REQUISICAO_NAO_TRATAVEL, "", false);
		}
	}

}
