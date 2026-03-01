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

@Entity
@Table(name="table_cliente")
@SequenceGenerator(name="gera_id_cliente",sequenceName="gera_id_cliente", 
		initialValue=1, allocationSize=1)
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
	private Estados uf;
	
	@Column(length=200, nullable=false)
	@NotBlank
	private String titular;
	
	@CpfCnpj
	@Column(length=20, name="cpf_cnpj")
	private String cpfCnpj;
	
	public Cliente() {
		super();
		uf = Estados.BA;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public Estados getUf() {
		return uf;
	}
	public void setUf(Estados uf) {
		this.uf = uf;
	}
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}