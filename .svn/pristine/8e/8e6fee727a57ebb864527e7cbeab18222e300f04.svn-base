package br.com.ajasoftware.model.usuario;

import java.io.Serializable;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import br.com.ajasoftware.model.grupo.acesso.GrupoAcesso;

@Entity
@Table(name="table_usuario")
@SequenceGenerator(name="gera_id_usuario",sequenceName="gera_id_usuario", 
		initialValue=2, allocationSize=1)
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_usuario")
	@GeneratedValue(generator="gera_id_usuario", strategy=GenerationType.AUTO)
	private Integer id;
	
	@Size(min=4, message="Digite o nome com pelo menos 4 caracteres")
	@Column(nullable=false, length=100)
	@NotNull
	private String nome;
	
	@CPF
	@Column(nullable=false)
	@NotNull
	private String cpf;
	
	@Column(nullable=false)
	@NotNull
	private String login;
	
	@Column(nullable=false)
	@NotNull
	private String senha;
	
	private Boolean administrador;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cod_grupo_acesso", nullable=false)
	private GrupoAcesso codGrupoAcesso;
	
	public Usuario() {
		super();
	}
	public Usuario(Integer id) {
		super();
		this.id = id;
	}
	
	public Usuario(Integer id, String nome, String cpf, String login,
			String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.login = login;
		this.senha = senha;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {		
		this.senha = senha;
	}
	public Boolean getAdministrador() {
		return administrador;
	}
	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}
	public GrupoAcesso getCodGrupoAcesso() {
		return codGrupoAcesso;
	}
	public void setCodGrupoAcesso(GrupoAcesso codGrupoAcesso) {
		this.codGrupoAcesso = codGrupoAcesso;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}