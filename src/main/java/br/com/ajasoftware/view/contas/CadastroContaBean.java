package br.com.ajasoftware.view.contas;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ajasoftware.model.conta.Conta;
import br.com.ajasoftware.repository.contas.ContasRepository;
import br.com.ajasoftware.service.msg.NegocioException;
import br.com.ajasoftware.util.jsf.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class CadastroContaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ContasRepository contas;
	
	@Getter @Setter
	private Conta conta;
	
	public void inicializar() {
		if(conta==null) {
			limpar();
		}
	}
	
	public void limpar() {
		conta=  new Conta();
	}
	
	public void guardar() {
		try {
			conta = contas.guardar(conta);
			FacesUtil.addInfoMessage("Conta inserida/atualizada");
		} catch (NegocioException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public boolean getPossuiId() {
		return conta!=null && conta.getId()!=null && !conta.getId().isEmpty();
	}
}