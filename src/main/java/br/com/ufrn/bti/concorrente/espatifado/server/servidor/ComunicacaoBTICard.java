package br.com.ufrn.bti.concorrente.espatifado.server.servidor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.com.ufrn.bti.concorrente.espatifado.server.comunicacao.MensagemRequisicao;
import br.com.ufrn.bti.concorrente.espatifado.server.comunicacao.MensagemResposta;
import br.com.ufrn.bti.concorrente.espatifado.server.comunicacao.TipoMensagem;
import br.com.ufrn.bti.concorrente.espatifado.server.comunicacao.conteudo.ConteudoRequisicaoPagamento;
import br.com.ufrn.bti.concorrente.espatifado.server.dominio.Pessoa;

public class ComunicacaoBTICard {

	public ComunicacaoBTICard() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Joaozin");
		pessoa.setAtivo(true);
		pessoa.setCpf("123.456.789-10");
		pessoa.setCidadeEstado("Natal/RN");
		
		ConteudoRequisicaoPagamento conteudoPagamento = new ConteudoRequisicaoPagamento();
		conteudoPagamento.setPessoa(pessoa);
		conteudoPagamento.setValorPagamento(100);
		
		MensagemResposta mensagemResposta;
		Socket socketBTICard;
		
		try {
			socketBTICard = new Socket("localhost", 8889);
			socketBTICard.setTcpNoDelay(true);
		} catch (IOException e) {
			System.out.println("Falha na criação do Socket!");
			e.printStackTrace();
			socketBTICard = null;
		}

		if (socketBTICard != null) {
			try {
				ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(socketBTICard.getInputStream()));
				ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(socketBTICard.getOutputStream()));

				outputStream.writeObject(new MensagemRequisicao<ConteudoRequisicaoPagamento>(
						TipoMensagem.SOLICITA_PAGAMENTO, conteudoPagamento));
				
				outputStream.flush();

				mensagemResposta = (MensagemResposta) inputStream.readObject();

				if (mensagemResposta.getTipoMensagem() == TipoMensagem.SOLICITA_PAGAMENTO_FALHA) {
					mensagemResposta.setTipoMensagem(TipoMensagem.SOLICITA_COMPRA_FALHA);
				} else {
					mensagemResposta.setTipoMensagem(TipoMensagem.SOLICITA_COMPRA_SUCESSO);
				}
			} catch (IOException | ClassNotFoundException ex) {
				ex.printStackTrace();
				mensagemResposta = new MensagemResposta<String>(TipoMensagem.SOLICITA_COMPRA_FALHA, "", false);
			}
		} else {
			mensagemResposta = new MensagemResposta<String>(TipoMensagem.SOLICITA_COMPRA_FALHA, "", false);
		}
		
		System.out.println(mensagemResposta.getTipoMensagem());
		System.out.println(mensagemResposta.getConteudo());
	}

}
