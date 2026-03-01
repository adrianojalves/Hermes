package br.com.ajasoftware.view.relatorios;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.primefaces.event.SelectEvent;

import br.com.ajasoftware.filtro.FiltroGeral;
import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.model.despesas.Despesa;
import br.com.ajasoftware.model.receita.Receita;
import br.com.ajasoftware.relatorio.ControleRelatorioPDF;
import br.com.ajasoftware.repository.despesas.DespesasRepository;
import br.com.ajasoftware.repository.despesas.FiltroDespesas;
import br.com.ajasoftware.repository.receitas.FiltroReceitas;
import br.com.ajasoftware.repository.receitas.ReceitasRepository;
import br.com.ajasoftware.util.Funcoes;
import br.com.ajasoftware.util.jsf.FacesUtil;

@Named
@RequestScoped
public class RelatorioAnualBean extends RelatoriosBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Date dataInicial;
	
	@NotNull
	private Date dataFinal;
	
	@NotNull
	private Cliente codCliente;
	
	@Inject
	private ReceitasRepository receitas;
	
	@Inject
	private DespesasRepository despesas;
	
	private List<RelatorioAnualCamposBean> listRel = new ArrayList<>();
	
	private final static String RECEITA="01-RECEITA", DESPESA="02-DESPESA", ACUMULADO="04-ACUMULADO", RESULTADO="03-RESULTADO";
	
	public void imprimir() {
		FiltroReceitas fReceitas = new FiltroReceitas();
		fReceitas.setAscendente(true);
		fReceitas.setQuantidadeRegistros(FiltroGeral.TODOS);
		fReceitas.setCodCliente(codCliente);
		fReceitas.setDataFinal(dataFinal);
		fReceitas.setDataInicial(dataInicial);
		
		FiltroDespesas fDespesas = new FiltroDespesas();
		fDespesas.setAscendente(true);
		fDespesas.setQuantidadeRegistros(FiltroGeral.TODOS);
		fDespesas.setCodCliente(codCliente);
		fDespesas.setDataFinal(dataFinal);
		fDespesas.setDataInicial(dataInicial);
		
		System.out.println("CLINTE="+codCliente.getId());
		List<Receita> listReceitas = receitas.filtrados(fReceitas);
		
		List<Despesa> listDespesas = despesas.filtrados(fDespesas);
		
		listRel = new ArrayList<>();
		
		int ano = Funcoes.getAno(dataInicial);
		
		iniciaListas(ano);
		
		RelatorioAnualCamposBean bean = null;
		
		if((listReceitas!=null && !listReceitas.isEmpty()) || (listDespesas!=null && !listDespesas.isEmpty())) {
			if(listReceitas!=null) {
				
				for(Receita r : listReceitas) {
					bean = RelatorioAnualCamposBean.builder()
												   .ano(Funcoes.getAno(r.getData()))
												   .mes(Funcoes.getMes(r.getData()))
												   .tipo(RECEITA)
												   .valor(r.getValor()!=null?r.getValor():BigDecimal.ZERO)
												   .build();
					
					int index = listRel.indexOf(bean);
					RelatorioAnualCamposBean aux = listRel.get(index);
					aux.setValor(aux.getValor().add(bean.getValor()));
					listRel.set(index, aux);
					
					bean.setTipo(RESULTADO);
					index = listRel.indexOf(bean);
					aux = listRel.get(index);
					aux.setValor(aux.getValor().add(bean.getValor()));
					listRel.set(index, aux);
					
				}//FIM DO FOR DE RECEITAS
			}
			
			if(listDespesas!=null) {
				for(Despesa r : listDespesas) {
					bean = RelatorioAnualCamposBean.builder()
												   .ano(Funcoes.getAno(r.getData()))
												   .mes(Funcoes.getMes(r.getData()))
												   .tipo(DESPESA)
												   .valor(r.getValor()!=null?r.getValor():BigDecimal.ZERO)
												   .build();
					
					int index = listRel.indexOf(bean);
					RelatorioAnualCamposBean aux = listRel.get(index);
					aux.setValor(aux.getValor().add(bean.getValor()));
					listRel.set(index, aux);
					
					bean.setTipo(RESULTADO);
					index = listRel.indexOf(bean);
					aux = listRel.get(index);
					aux.setValor(aux.getValor().subtract(bean.getValor()));
					listRel.set(index, aux);
				}//FIM DO FOR DE DESPESAS
			}
			
			BigDecimal valorAcumulado = BigDecimal.ZERO;
			for(int i=1; i<=12; i++) {
				bean = RelatorioAnualCamposBean.builder()
						.tipo(RESULTADO)
						.ano(ano)
						.mes(i)
						.valor(BigDecimal.ZERO)
						.build();
				
				RelatorioAnualCamposBean aux = listRel.get(listRel.indexOf(bean));
				valorAcumulado = valorAcumulado.add(aux.getValor());
				
				listRel.add(RelatorioAnualCamposBean.builder()
						.tipo(ACUMULADO)
						.ano(ano)
						.mes(i)
						.valor(valorAcumulado!=null?valorAcumulado:BigDecimal.ZERO)
						.build());
				
			}
			
		}//fim do if lists
		
		try {
			parametros.put("nomeCliente", codCliente.getTitular());
			parametros.put("nomeCartorio", codCliente.getNome());
			parametros.put("documento", codCliente.getCpfCnpj());
			parametros.put("endereco", codCliente.getEndereco()+", "+codCliente.getCidade()+" - "+codCliente.getUf().getSigla());
			parametros.put("nomeCidade", codCliente.getCidade()+" - "+codCliente.getUf());
			parametros.put("dataFinal", new SimpleDateFormat("dd/MM/yyyy").format(dataFinal));
			new ControleRelatorioPDF("rel_anual", response, parametros, "relatorioAnual.pdf", listRel).gerar();
				
		} catch (Exception e) {
			FacesUtil.addInfoMessage(e.getMessage());
		}
	}
	
	private void iniciaListas(int ano) {
		for(int i=1; i<=12; i++) {
			listRel.add(RelatorioAnualCamposBean.builder()
												.tipo(RECEITA)
												.ano(ano)
												.mes(i)
												.valor(BigDecimal.ZERO)
												.build());
			
			
		}
		for(int i=1; i<=12; i++) {
			listRel.add(RelatorioAnualCamposBean.builder()
												.tipo(DESPESA)
												.ano(ano)
												.mes(i)
												.valor(BigDecimal.ZERO)
												.build());
		}
		
		for(int i=1; i<=12; i++) {
			listRel.add(RelatorioAnualCamposBean.builder()
												.tipo(RESULTADO)
												.ano(ano)
												.mes(i)
												.valor(BigDecimal.ZERO)
												.build());
		}
		
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