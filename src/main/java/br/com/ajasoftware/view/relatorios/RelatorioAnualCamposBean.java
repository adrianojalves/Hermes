package br.com.ajasoftware.view.relatorios;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"tipo", "mes", "ano"})
public class RelatorioAnualCamposBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tipo;
	private Integer mes;
	private Integer ano;
	private BigDecimal valor;
	
}