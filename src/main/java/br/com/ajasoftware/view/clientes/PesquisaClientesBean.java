package br.com.ajasoftware.view.clientes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.repository.clientes.FiltroCliente;
import br.com.ajasoftware.repository.clientes.ClientesRepository;
import br.com.ajasoftware.service.msg.NegocioException;
import br.com.ajasoftware.util.jsf.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class PesquisaClientesBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ClientesRepository clientes;

	private LazyDataModel<Cliente> model;
	private FiltroCliente filtro;
	private Cliente cliente;
	
	@Getter @Setter
	private Boolean soAtivos;

	public void inicializar() {

		filtro = new FiltroCliente();

		model = new LazyDataModel<Cliente>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Cliente> load(int first, int pageSize, String sortField, SortOrder sortOrder, 
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setPropriedadeOrdenacao(sortField);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setSoAtivos(soAtivos);
				setRowCount(clientes.quantidadeFiltrados(filtro));
				return (List<Cliente>) clientes.filtrados(filtro);
			}

		};
	}

	public LazyDataModel<Cliente> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Cliente> model) {
		this.model = model;
	}

	public FiltroCliente getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroCliente filtro) {
		this.filtro = filtro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void excluir() {
		String nome = cliente.getNome();
		
		try {
			clientes.excluir(cliente);
			
			FacesUtil.addInfoMessage("Cliente: "+nome+" excluído");
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public String editar() {
		return "CadastroCliente?faces-redirect=true&cod="+cliente.getId();
	}
	
	public void selecionar() {
		RequestContext.getCurrentInstance().closeDialog(cliente);
	}
}