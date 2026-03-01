package br.com.ajasoftware.view.despesas;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.model.conta.Conta;
import br.com.ajasoftware.model.despesas.Despesa;
import br.com.ajasoftware.repository.despesas.DespesasRepository;
import br.com.ajasoftware.service.msg.NegocioException;
import br.com.ajasoftware.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroDespesaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DespesasRepository despesas;
	
	private Despesa despesa;
	
	public void inicializar() {
		if(despesa==null) {
			despesa = new Despesa();
		}
	}
	
	public void limpar() {
		Cliente c = despesa.getCodCliente();
		despesa = new Despesa();
		despesa.setCodCliente(c);
	}
	
	public void guardar() {
		try {
			if(despesa.getCodConta()==null) {
				FacesUtil.addErrorMessage("Selecione a conta");
			}
			else {
				despesa = despesas.guardar(despesa);
				FacesUtil.addInfoMessage("Despesa inserida/atualizada");
			}
		} catch (NegocioException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public Despesa getDespesa() {
		return despesa;
	}
	
	public void configuraConta() {
		despesa.setConta(despesa.getCodConta().getId());
		despesa.setDescricao(despesa.getCodConta().getNome());
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public void capturaCliente(SelectEvent event) {
		if(event.getObject()!=null) {
			despesa.setCodCliente((Cliente)event.getObject());
		}
	}
	
	public void capturaConta(SelectEvent event) {
		if(event.getObject()!=null) {
			despesa.setCodConta((Conta)event.getObject());
			configuraConta();
		}
	}
}