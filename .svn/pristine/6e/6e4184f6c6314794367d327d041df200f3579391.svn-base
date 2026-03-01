package br.com.ajasoftware.view.contas;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.ajasoftware.model.conta.Conta;
import br.com.ajasoftware.repository.contas.ContasRepository;
import br.com.ajasoftware.repository.contas.FiltroConta;
import br.com.ajasoftware.service.msg.NegocioException;
import br.com.ajasoftware.util.jsf.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class PesquisaContasBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ContasRepository contas;

	private LazyDataModel<Conta> model;
	private FiltroConta filtro;
	private Conta conta;
	
	@Getter @Setter
	private Boolean lupa;

	public void inicializar() {

		filtro = new FiltroConta();

		model = new LazyDataModel<Conta>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Conta> load(int first, int pageSize, String sortField, SortOrder sortOrder, 
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setPropriedadeOrdenacao(sortField);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				
				if(lupa!=null && lupa) {
					filtro.setStatus(true);
				}
				
				setRowCount(contas.quantidadeFiltrados(filtro));
				return (List<Conta>) contas.filtrados(filtro);
			}

		};
	}

	public LazyDataModel<Conta> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Conta> model) {
		this.model = model;
	}

	public FiltroConta getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroConta filtro) {
		this.filtro = filtro;
	}
	
	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public void excluir() {
		String nome = conta.getNome();
		
		try {
			contas.excluir(conta);
			
			FacesUtil.addInfoMessage("Conta: "+nome+" excluída");
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public String editar() {
		return "CadastroConta?faces-redirect=true&cod="+conta.getId();
	}
	
	public void selecionar() {
		RequestContext.getCurrentInstance().closeDialog(conta);
	}
}