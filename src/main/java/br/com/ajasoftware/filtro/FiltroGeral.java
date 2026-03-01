package br.com.ajasoftware.filtro;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class FiltroGeral implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int TODOS=-1;
	
	protected String nome;
	protected Integer id;
	protected int primeiroRegistro;
	protected int quantidadeRegistros;
	protected String propriedadeOrdenacao;
	protected boolean ascendente;
}