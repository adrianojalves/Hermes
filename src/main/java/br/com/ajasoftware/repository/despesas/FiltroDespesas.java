package br.com.ajasoftware.repository.despesas;

import java.util.Date;

import br.com.ajasoftware.filtro.FiltroGeral;
import br.com.ajasoftware.model.cliente.Cliente;

public class FiltroDespesas extends FiltroGeral{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date dataInicial;
	private Date dataFinal;
	private Cliente codCliente;
	private Integer conta;
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
	public Integer getConta() {
		return conta;
	}
	public void setConta(Integer conta) {
		this.conta = conta;
	}
	
}