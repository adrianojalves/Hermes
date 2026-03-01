package br.com.ajasoftware.util;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class Patterns implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getData(){
		return "dd/MM/yyyy";
	}
	
	public String getDataHora(){
		return "dd/MM/yyyy HH:mm";
	}
	
	public String getTimeZone(){
		return "GMT-3";
	}
	
	public String getNumero(){
		return "###,###,###,##0.00";
	}
	
	public String getInteiro(){
		return "###,###,###,##0";
	}
	public String getLocale(){
		return "pt_BR";
	}
}