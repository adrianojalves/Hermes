package br.com.ajasoftware.model.conta;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="table_conta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Conta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_conta")
	@NotNull
	@Size(min = 4)
	private String id;
	
	@Column(length=200, nullable=false)
	@NotBlank
	@Size(max=200)
	private String nome;
	
	private Boolean status;
	
	@Column(name="codigo_auxiliar")
	private String codigoAuxiliar;
	
	public String getLabel() {
		return (StringUtils.isNotBlank(codigoAuxiliar)? codigoAuxiliar : id )+" - "+nome;
	}

}