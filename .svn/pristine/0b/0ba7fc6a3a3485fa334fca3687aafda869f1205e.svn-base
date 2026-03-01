package br.com.ajasoftware.view.clientes;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.repository.clientes.ClientesRepository;
import br.com.ajasoftware.service.msg.NegocioException;
import br.com.ajasoftware.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ClientesRepository clientes;
	
	private Cliente cliente;
	
	public void inicializar() {
		if(cliente==null) {
			cliente = new Cliente();
		}
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void guardar() {
		try {
			cliente = clientes.guardar(cliente);
			FacesUtil.addInfoMessage("Cliente inserido/atualizado");
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
}