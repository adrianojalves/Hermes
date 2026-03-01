package br.com.ajasoftware.util;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.ajasoftware.enums.Estados;
import br.com.ajasoftware.report.TipoRel;

@Named
@RequestScoped
public class ArrayUtils implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Estados[] getEstados() {
		return Estados.values();
	}
	
	public TipoRel[] getTiposRels() {
		return TipoRel.values();
	}
}