package br.com.ajasoftware.repository.clientes;

import br.com.ajasoftware.filtro.FiltroGeral;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiltroCliente extends FiltroGeral{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean soAtivos;

}