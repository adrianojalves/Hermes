package br.com.ajasoftware.model.receita;

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

import br.com.ajasoftware.model.cliente.Cliente;

@Entity
@Table(name="table_receita")
@SequenceGenerator(name="gera_id_receita",sequenceName="gera_id_receita", 
		initialValue=1, allocationSize=1)
public class Receita implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_receita")
	@GeneratedValue(generator="gera_id_receita", strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cod_cliente", nullable=false)
	@NotNull
	private Cliente codCliente;
	
	@Column(length=30)
	@Size(max=30)
	private String documento;
	
	@Column(length=2500, nullable=false)
	@NotBlank
	@Size(max=2500)
	private String descricao;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	@NotNull
	private Date data;
	
	@Column(nullable=false, precision=14, scale=2)
	@NotNull
	private BigDecimal valor;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Cliente getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(Cliente codCliente) {
		this.codCliente = codCliente;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return "Receita [id=" + id + ", codCliente=" + codCliente + ", documento=" + documento + ", descricao="
				+ descricao + ", data=" + data + ", valor=" + valor + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receita other = (Receita) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}