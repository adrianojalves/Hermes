package br.com.ajasoftware.view.usuarios;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ajasoftware.model.grupo.acesso.GrupoAcesso;
import br.com.ajasoftware.model.usuario.Usuario;
import br.com.ajasoftware.repository.usuarios.UsuariosRepository;
import br.com.ajasoftware.service.msg.NegocioException;
import br.com.ajasoftware.util.Funcoes;
import br.com.ajasoftware.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuariosRepository usuarios;
	
	@Inject
	private Funcoes funcoes;
	
	private Usuario usuario;
	
	private boolean mostrarSenha;
	
	public void inicializar() {
		if(usuario==null) {
			usuario = new Usuario();
			usuario.setCodGrupoAcesso(new GrupoAcesso(1));
			usuario.setAdministrador(true);
			configMostraSenha();
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void guardar() {
		try {
			if(mostrarSenha) {
				usuario.setSenha(funcoes.md5(usuario.getSenha()));
			}
			usuario = usuarios.guardar(usuario);
			FacesUtil.addInfoMessage("Usuário inserido/atualizado");
			mostrarSenha=false;
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void configMostraSenha() {
		mostrarSenha=true;
	}
	public boolean isMostrarSenha() {
		return mostrarSenha;
	}

	public void setMostrarSenha(boolean mostrarSenha) {
		this.mostrarSenha = mostrarSenha;
	}
}