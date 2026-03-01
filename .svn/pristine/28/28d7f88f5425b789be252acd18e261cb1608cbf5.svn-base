package br.com.ajasoftware.view.relatorios;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.ajasoftware.qualifiers.GeraParametroReport;
import br.com.ajasoftware.report.ExecutorRelatorio;
import br.com.ajasoftware.report.TipoRel;
import br.com.ajasoftware.util.Funcoes;
import br.com.ajasoftware.util.jsf.FacesUtil;

public abstract class RelatoriosBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@GeraParametroReport
	protected Map<String, Object> parametros;

	@Inject
	protected HttpServletResponse response;

	@Inject
	protected EntityManager manager;

	@Inject
	protected FacesContext facesContext;

	@Inject
	protected ExternalContext context;

	@Inject
	protected Funcoes funcoes;

	protected String nomeReport;
	protected String nomeArquivo;

	protected TipoRel tipoRel;

	protected ExecutorRelatorio executor;

	public RelatoriosBean() {
		super();
		tipoRel = TipoRel.PDF;
	}

	public TipoRel getTipoRel() {
		return tipoRel;
	}

	public void setTipoRel(TipoRel tipoRel) {
		this.tipoRel = tipoRel;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public ExternalContext getContext() {
		return context;
	}

	public void setContext(ExternalContext context) {
		this.context = context;
	}

	//funcao que deve ser sobrescrita
	public void imprimir() {
		parametros.put("pdfOuXls", tipoRel.ordinal());
		download();
	}

	public void download() {
		executor = new ExecutorRelatorio(nomeReport+".jasper",
				this.response, parametros, nomeArquivo+tipoRel.getExtensao(), context, tipoRel);

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	public String getNomeReport() {
		return nomeReport;
	}

	public void setNomeReport(String nomeReport) {
		this.nomeReport = nomeReport;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

}