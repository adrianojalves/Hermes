package br.com.ajasoftware.view.usuarios;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.ajasoftware.model.usuario.Usuario;
import br.com.ajasoftware.repository.usuarios.FiltroUsuario;
import br.com.ajasoftware.repository.usuarios.UsuariosRepository;
import br.com.ajasoftware.service.msg.NegocioException;
import br.com.ajasoftware.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuariosBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuariosRepository usuarios;

	private LazyDataModel<Usuario> model;
	private FiltroUsuario filtro;
	private Usuario usuario;

	public void inicializar() {

		filtro = new FiltroUsuario();

		model = new LazyDataModel<Usuario>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder, 
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setPropriedadeOrdenacao(sortField);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				setRowCount(usuarios.quantidadeFiltrados(filtro));
				return (List<Usuario>) usuarios.filtrados(filtro);
			}

		};
	}

	public LazyDataModel<Usuario> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Usuario> model) {
		this.model = model;
	}

	public FiltroUsuario getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroUsuario filtro) {
		this.filtro = filtro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void excluir() {
		String nome = usuario.getNome();
		
		try {
			usuarios.excluir(usuario);
			
			FacesUtil.addInfoMessage("Usuário: "+nome+" excluído");
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesUtil.addErrorMessage("Não foi possível excluir o usuário");
		}
	}
	
	public String editar() {
		return "CadastroUsuario?faces-redirect=true&cod="+usuario.getId();
	}
}