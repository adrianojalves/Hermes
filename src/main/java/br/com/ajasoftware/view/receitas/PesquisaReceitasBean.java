package br.com.ajasoftware.view.receitas;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.model.receita.Receita;
import br.com.ajasoftware.repository.receitas.FiltroReceitas;
import br.com.ajasoftware.repository.receitas.ReceitasRepository;
import br.com.ajasoftware.service.msg.NegocioException;
import br.com.ajasoftware.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaReceitasBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ReceitasRepository receitas;
	
	private LazyDataModel<Receita> model;
	private Receita receita;
	private FiltroReceitas filtro;
	
	private Boolean formatoBrasil;

	public void inicializar() {

		filtro = new FiltroReceitas();
		
		formatoBrasil= false;

		model = new LazyDataModel<Receita>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Receita> load(int first, int pageSize, String sortField, SortOrder sortOrder, 
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setPropriedadeOrdenacao(sortField);
				filtro.setAscendente(SortOrder.DESCENDING.equals(sortOrder));
				setRowCount(receitas.quantidadeFiltrados(filtro));
				return (List<Receita>) receitas.filtrados(filtro);
			}

		};
	}

	public LazyDataModel<Receita> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Receita> model) {
		this.model = model;
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public FiltroReceitas getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroReceitas filtro) {
		this.filtro = filtro;
	}

	public void excluir() {
		String nome = receita.getDocumento();
		
		try {
			receitas.excluir(receita);
			
			FacesUtil.addInfoMessage("Receita: "+nome+" excluída");
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public String editar() {
		return "CadastroReceita?faces-redirect=true&cod="+receita.getId();
	}
	
	public void capturaCliente(SelectEvent event) {
		if(event.getObject()!=null) {
			filtro.setCodCliente((Cliente)event.getObject());
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		
		try {
			receitas.importarXls(event, filtro.getCodCliente(), formatoBrasil);
			
			FacesUtil.addInfoMessage("Receitas importadas com sucesso");
		}
		catch(NegocioException ex) {
			FacesUtil.addErrorMessage(ex.getMessage());
		}
	}
	
	public void excluirSelecao() {
		try {
			int result = receitas.excluirSelecao(filtro);
			
			FacesUtil.addInfoMessage(result+" registro(s) excluído(s).");
		}
		catch(NegocioException ex) {
			FacesUtil.addErrorMessage(ex.getMessage());
		}
	}

	public Boolean getFormatoBrasil() {
		return formatoBrasil;
	}

	public void setFormatoBrasil(Boolean formatoBrasil) {
		this.formatoBrasil = formatoBrasil;
	}
	
}