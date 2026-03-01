package br.com.ajasoftware.view.relatorios;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import br.com.ajasoftware.filtro.FiltroGeral;
import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.model.despesas.Despesa;
import br.com.ajasoftware.report.ExecutorRelatorio;
import br.com.ajasoftware.repository.despesas.DespesasRepository;
import br.com.ajasoftware.repository.despesas.FiltroDespesas;
import br.com.ajasoftware.security.UsuarioLogado;
import br.com.ajasoftware.security.UsuarioSistema;
import br.com.ajasoftware.util.Funcoes;
import br.com.ajasoftware.util.Trata;
import br.com.ajasoftware.util.jsf.FacesUtil;

@Named
@RequestScoped
public class ExportarDespesasBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DespesasRepository despesas;
	
	@Inject
	private Funcoes funcoes;
	
	@Inject
	@UsuarioLogado
	private UsuarioSistema usuario;
	
	@Inject
	private FacesContext facesContext;
	
	@NotNull
	private Date dataInicial;
	
	@NotNull
	private Date dataFinal;
	
	@NotNull
	private Cliente codCliente;
	
	public void imprimir() {
		FiltroDespesas filtro = new FiltroDespesas();
		filtro.setDataInicial(dataInicial);
		filtro.setDataFinal(dataFinal);
		filtro.setQuantidadeRegistros(FiltroGeral.TODOS);
		filtro.setCodCliente(codCliente);
		filtro.setAscendente(true);
		
		List<Despesa> lista = despesas.filtrados(filtro);
		
		if(lista!=null && !lista.isEmpty()) {
			StringBuffer arquivo = new StringBuffer();
			
			for(Despesa d: lista) {
				arquivo.append(Trata.FormData(d.getData())+";");
				arquivo.append(StringUtils.isNotBlank(d.getCodConta().getCodigoAuxiliar()) ? d.getCodConta().getCodigoAuxiliar() : d.getConta()+";");
				arquivo.append(Trata.FormValor(d.getValor(), 2)+";");
				arquivo.append(getDescricao(d)+";");
				arquivo.append(Trata.FormValor(BigDecimal.ZERO, 2)+";");
				arquivo.append(Trata.FormValor(BigDecimal.ZERO, 2)+";");
				arquivo.append(Trata.FormMesAno(d.getData())+"\n");
			}
			
			FileWriter arq;
			try {
				String nomeArq="";
				if(System.getProperty("os.name").toUpperCase().contains("LINUX")) {
					nomeArq = "/hermes/despesas"+usuario.getUsuario().getId()+".csv";
				}
				else {
					nomeArq = "c:\\hermes\\despesas"+usuario.getUsuario().getId()+".csv";
				}
				arq = new FileWriter(nomeArq);
				PrintWriter gravarArq = new PrintWriter(arq);
				
				gravarArq.printf(arquivo.toString());
				
				arq.close();
				
				ExecutorRelatorio er = new ExecutorRelatorio();
				String nome = funcoes.removeFormatacao(filtro.getCodCliente().getCpfCnpj())+"-"+
						new SimpleDateFormat("yyyy").format(filtro.getDataFinal())+".csv";
				er.downloadFile(nome, nomeArq, 
						facesContext);
			} catch (IOException e) {
				e.printStackTrace();
				FacesUtil.addErrorMessage("Erro na geração do arquivo csv.");
			}
		}
		else {
			FacesUtil.addWarnMessage("Consulta sem resultados.");
		}
	}

	private String getDescricao(Despesa d) {
		String descricao="";
		if(d.getDescricao()!=null && !d.getDescricao().isEmpty()) {
			descricao = d.getDescricao();
		}
		else if(d.getCodConta()!=null) {
			descricao = d.getCodConta().getNome();
		}
		
		if(descricao.length()>255) {
			descricao = descricao.substring(0, 244);
		}
		
		return Trata.removerAcentos(descricao);
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Cliente getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Cliente codCliente) {
		this.codCliente = codCliente;
	}
	public void capturaCliente(SelectEvent event) {
		if(event.getObject()!=null) {
			codCliente = ((Cliente)event.getObject());
		}
	}
}