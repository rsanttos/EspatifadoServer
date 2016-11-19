package br.com.ufrn.bti.concorrente.espatifado.server.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.com.ufrn.bti.concorrente.espatifado.server.comunicacao.MensagemRequisicao;
import br.com.ufrn.bti.concorrente.espatifado.server.comunicacao.MensagemResposta;
import br.com.ufrn.bti.concorrente.espatifado.server.comunicacao.TipoMensagem;
import br.com.ufrn.bti.concorrente.espatifado.server.comunicacao.conteudo.ConteudoRequisicaoPagamento;
import br.com.ufrn.bti.concorrente.espatifado.server.dominio.Produtora;
import br.com.ufrn.bti.concorrente.espatifado.server.dominio.Usuario;
import br.com.ufrn.bti.concorrente.espatifado.server.servico.MusicaService;
import br.com.ufrn.bti.concorrente.espatifado.server.servico.ProdutoraService;
import br.com.ufrn.bti.concorrente.espatifado.server.servico.UsuarioService;

public class TratadorRequisicoes implements Runnable {

	private Socket socket, socketBTICard;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private MensagemRequisicao mensagem;
	private MusicaService servicoMusicas;
	private UsuarioService servicoUsuario;
	private ProdutoraService servicoProdutora;
	private Produtora produtora;

	public TratadorRequisicoes(Socket socket) {
		this.socket = socket;
		this.servicoMusicas = new MusicaService();
		this.servicoUsuario = new UsuarioService();
		this.servicoProdutora = new ProdutoraService();
		this.produtora = servicoProdutora.buscarPeloNome("Espatifado");
		try {
			this.socketBTICard = new Socket("locahost", 8889);
		} catch (IOException e) {
			this.socketBTICard = null;
		}
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

		MensagemResposta mensagemResposta;

		if (this.mensagem.getTipoMensagem() == TipoMensagem.LOGIN) {
			mensagemResposta = trataLogin();
		} else if (this.mensagem.getTipoMensagem() == TipoMensagem.LISTA_MUSICAS) {
			mensagemResposta = new MensagemResposta<String>(TipoMensagem.LISTA_MUSICAS_SUCESSO,
					servicoMusicas.getMusicasJson(), true);
		} else if (this.mensagem.getTipoMensagem() == TipoMensagem.SOLICITA_COMPRA) {
			mensagemResposta = trataSolicitacaoCompra();
		} else {
			mensagemResposta = new MensagemResposta<String>(TipoMensagem.REQUISICAO_NAO_TRATAVEL, "", false);
		}

		try {
			this.outputStream.writeObject(mensagemResposta);
		} catch (IOException e) {
			System.out.println("ERRO no envio da resposta!");
			e.printStackTrace();
		}

		try {
			if (this.outputStream != null) {
				this.outputStream.close();
			}

			if (this.inputStream != null) {
				this.inputStream.close();
			}

			if (this.socket != null) {
				this.socket.close();
			}
		} catch (IOException e) {
			System.out.println("ERRO ao fechar o socket!");
			e.printStackTrace();
		}

	}

	private MensagemResposta trataLogin() {
		MensagemResposta<Boolean> mensagemResposta = new MensagemResposta<Boolean>();
		
		Usuario usuario = (Usuario) this.mensagem.getConteudo();
		Usuario usuarioLogin = this.servicoUsuario.getByLogin(usuario.getLogin());
		
		if( (usuarioLogin == null) ||  !usuarioLogin.getSenha().equals(usuario.getSenha()) ){
			mensagemResposta.setTipoMensagem(TipoMensagem.LOGIN_FALHA);
			mensagemResposta.setConteudo(false);
			mensagemResposta.setSucesso(false);
		} else {
			mensagemResposta.setTipoMensagem(TipoMensagem.LOGIN_SUCESSO);
			mensagemResposta.setConteudo(true);
			mensagemResposta.setSucesso(true);
		}
		
		return mensagemResposta;
	}
	
	private MensagemResposta trataSolicitacaoCompra() {
		ConteudoRequisicaoPagamento conteudoPagamento = (ConteudoRequisicaoPagamento) this.mensagem.getConteudo();
		MensagemResposta mensagemResposta;

		if (this.socketBTICard != null) {
			try {
				this.inputStream = new ObjectInputStream(this.socketBTICard.getInputStream());
				this.outputStream = new ObjectOutputStream(this.socketBTICard.getOutputStream());

				this.outputStream.writeObject(new MensagemRequisicao<ConteudoRequisicaoPagamento>(
						TipoMensagem.SOLICITA_PAGAMENTO, conteudoPagamento));

				mensagemResposta = (MensagemResposta) this.inputStream.readObject();

				if (mensagemResposta.getTipoMensagem() == TipoMensagem.SOLICITA_PAGAMENTO_FALHA) {
					mensagemResposta.setTipoMensagem(TipoMensagem.SOLICITA_COMPRA_FALHA);
				} else {
					produtora.setCaixa(produtora.getCaixa() + conteudoPagamento.getValorPagamento());
					servicoProdutora.salvarOuAtualizar(produtora);
					mensagemResposta.setTipoMensagem(TipoMensagem.SOLICITA_COMPRA_SUCESSO);
				}
			} catch (IOException | ClassNotFoundException ex) {
				mensagemResposta = new MensagemResposta<String>(TipoMensagem.SOLICITA_COMPRA_FALHA, "", false);
			}
		} else {
			mensagemResposta = new MensagemResposta<String>(TipoMensagem.SOLICITA_COMPRA_FALHA, "", false);
		}

		return mensagemResposta;
	}
}
