package br.com.ufrn.bti.comunicacao;

import java.io.Serializable;

public class MensagemRequisicao<T> extends AbstractMensagem<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5294811775601330890L;

	public MensagemRequisicao(TipoMensagem tipoMensagem, T conteudo) {
		super(tipoMensagem, conteudo);
	}

	public MensagemRequisicao(){}

}
