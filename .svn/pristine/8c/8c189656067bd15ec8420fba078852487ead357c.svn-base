package br.com.ajasoftware.view.receitas;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.model.receita.Receita;
import br.com.ajasoftware.repository.receitas.ReceitasRepository;
import br.com.ajasoftware.service.msg.NegocioException;
import br.com.ajasoftware.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroReceitaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ReceitasRepository receitas;
	
	private Receita receita;
	
	public void inicializar() {
		if(receita==null) {
			limpar();
		}
	}
	
	public void limpar() {
		Cliente c = receita!=null?receita.getCodCliente():null;
		receita = new Receita();
		receita.setCodCliente(c);
	}
	
	public void guardar() {
		try {
			receita = receitas.guardar(receita);
			FacesUtil.addInfoMessage("Receita inserida/atualizada");
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	public void capturaCliente(SelectEvent event) {
		if(event.getObject()!=null) {
			receita.setCodCliente((Cliente)event.getObject());
		}
	}
}