package br.com.ufrn.bti.comunicacao;

import java.io.Serializable;

public class MensagemResposta<T> extends AbstractMensagem<T> implements Serializable {

	public MensagemResposta(TipoMensagem tipoMensagem, T conteudo, boolean sucesso) {
		super(tipoMensagem, conteudo);
		this.sucesso = sucesso;
	}
	
	public MensagemResposta(){}

	private boolean sucesso;
	
	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}

}
