package br.com.ajasoftware.repository.usuarios;

import br.com.ajasoftware.filtro.FiltroGeral;

public class FiltroUsuario extends FiltroGeral{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String login;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	

}