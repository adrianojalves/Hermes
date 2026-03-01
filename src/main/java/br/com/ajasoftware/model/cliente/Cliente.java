package br.com.ajasoftware.model.cliente;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.ajasoftware.constraints.CpfCnpj;
import br.com.ajasoftware.enums.Estados;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="table_cliente")
@SequenceGenerator(name="gera_id_cliente",sequenceName="gera_id_cliente", 
		initialValue=1, allocationSize=1)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of="id")
public class Cliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cliente")
	@GeneratedValue(generator="gera_id_cliente", strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(length=200, nullable=false)
	@NotBlank
	private String nome;
	
	@Column(length=2000, nullable=false)
	@NotBlank
	private String endereco;
	
	@Column(length=20, nullable=false)
	@NotBlank
	private String cep;
	
	@Column(length=200, nullable=false)
	@NotBlank
	private String cidade;
	
	@Enumerated(EnumType.STRING)
	@Column(length=2, nullable=false)
	@NotNull
	@Builder.Default
	private Estados uf = Estados.BA;
	
	@Column(length=200, nullable=false)
	@NotBlank
	private String titular;
	
	@CpfCnpj
	@Column(length=20, name="cpf_cnpj")
	private String cpfCnpj;
	
	private Boolean status;
	
}