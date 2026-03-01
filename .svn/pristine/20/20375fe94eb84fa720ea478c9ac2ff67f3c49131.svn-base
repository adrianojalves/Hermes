package br.com.ajasoftware.view.despesas;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.model.despesas.Despesa;
import br.com.ajasoftware.repository.despesas.DespesasRepository;
import br.com.ajasoftware.repository.despesas.FiltroDespesas;
import br.com.ajasoftware.service.msg.NegocioException;
import br.com.ajasoftware.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaDespesasBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private DespesasRepository despesas;
	
	private LazyDataModel<Despesa> model;
	private Despesa despesa;
	private FiltroDespesas filtro;

	public void inicializar() {

		filtro = new FiltroDespesas();

		model = new LazyDataModel<Despesa>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Despesa> load(int first, int pageSize, String sortField, SortOrder sortOrder, 
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setPropriedadeOrdenacao(sortField);
				filtro.setAscendente(SortOrder.DESCENDING.equals(sortOrder));
				setRowCount(despesas.quantidadeFiltrados(filtro));
				return (List<Despesa>) despesas.filtrados(filtro);
			}

		};
	}

	public LazyDataModel<Despesa> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Despesa> model) {
		this.model = model;
	}

	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public FiltroDespesas getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroDespesas filtro) {
		this.filtro = filtro;
	}

	public void excluir() {
		String nome = despesa.getDescricao();
		
		try {
			despesas.excluir(despesa);
			
			FacesUtil.addInfoMessage("Despesa: "+nome+" excluída");
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public String editar() {
		return "CadastroDespesa?faces-redirect=true&cod="+despesa.getId();
	}
	
	public void capturaCliente(SelectEvent event) {
		if(event.getObject()!=null) {
			filtro.setCodCliente((Cliente)event.getObject());
		}
	}
}