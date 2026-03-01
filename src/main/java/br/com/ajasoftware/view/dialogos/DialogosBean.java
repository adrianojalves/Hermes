package br.com.ajasoftware.view.dialogos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

@Named
@RequestScoped
public class DialogosBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void abrirDialogoCliente() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("responsive", true);
		//		opcoes.put("width", 640);
		opcoes.put("height", 450);
		opcoes.put("contentWidth", "100%");
		opcoes.put("contentHeight", "100%");
		opcoes.put("headerElement", "customheader");

		Map<String, List<String>> parametros = new HashMap<>();

		RequestContext.getCurrentInstance().openDialog("/dialogos/DialogoCliente", opcoes, parametros);
	}
	
	public void abrirDialogoConta() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("responsive", true);
		//		opcoes.put("width", 640);
		opcoes.put("height", 450);
		opcoes.put("contentWidth", "100%");
		opcoes.put("contentHeight", "100%");
		opcoes.put("headerElement", "customheader");

		Map<String, List<String>> parametros = new HashMap<>();

		RequestContext.getCurrentInstance().openDialog("/dialogos/DialogoConta", opcoes, parametros);
	}
}