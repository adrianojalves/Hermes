package br.com.ajasoftware.model.despesas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.com.ajasoftware.constraints.CpfCnpj;
import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.model.conta.Conta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="table_despesas")
@SequenceGenerator(name="gera_id_despesa",sequenceName="gera_id_despesa", 
		initialValue=1, allocationSize=1)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Despesa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_despesas")
	@GeneratedValue(generator="gera_id_despesa", strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cod_cliente", nullable=false)
	@NotNull
	private Cliente codCliente;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	@NotNull
	private Date data;
	
	@Column(length=2000, nullable=false)
	@NotBlank
	@Size(max=2000)
	private String descricao;
	
	@Column(precision=14, scale=2, nullable=false)
	@NotNull
	private BigDecimal valor;
	
	@Column(nullable=false)
	@NotNull
	private String conta;
	
	@CpfCnpj
	@Column(name="cpf_cnpj_beneficiario", length=20)
	private String cpfCnpjBeneficiario;
	
	@CpfCnpj
	@Column(name="cpf_cnpj_titular", length=20)
	private String cpfCnpjTitular;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cod_conta")
	private Conta codConta;
	
}