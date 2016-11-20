package br.com.ufrn.bti.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.ufrn.bti.comunicacao.MensagemRequisicao;
import br.com.ufrn.bti.comunicacao.MensagemResposta;
import br.com.ufrn.bti.comunicacao.TipoMensagem;
import br.com.ufrn.bti.dominio.Usuario;
import br.com.ufrn.bti.servico.UsuarioService;

public class MainServidorLogin {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		int porta = 6789;

		ServerSocket escuta = new ServerSocket(porta);

		System.out.println("*** Servidor ***");
		System.out.println("*** Porta de escuta (listen): " + porta);

		while (true) {
			Socket cliente = escuta.accept();
			System.out.println("*** conexao aceita de (remoto): " + cliente.getRemoteSocketAddress());

			ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

			MensagemRequisicao<Usuario> m = (MensagemRequisicao<Usuario>) ois.readObject();

			Usuario usuarioLogin = new UsuarioService().getByLogin(m.getConteudo().getLogin());

			MensagemResposta<Boolean> mr = new MensagemResposta<>();

			if (usuarioLogin.getSenha().equals(m.getConteudo().getSenha())) {
				mr.setTipoMensagem(TipoMensagem.LOGIN_SUCESSO);
				mr.setSucesso(true);
				mr.setConteudo(true);
			} else {
				mr.setTipoMensagem(TipoMensagem.LOGIN_FALHA);
				mr.setSucesso(false);
				mr.setConteudo(false);
			}
			
			System.out.println("Resposta do servidor");
			System.out.println(mr.getTipoMensagem());
			System.out.println(mr.getConteudo());
			System.out.println(mr.isSucesso());

			ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
			oos.writeObject(mr);
		}
	}

}
