package br.com.ajasoftware.model.grupo.acesso;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="table_grupo_acesso")
@SequenceGenerator(name="gera_id_grupo_acesso", sequenceName="gera_id_grupo_acesso",
		initialValue=2, allocationSize=1)
public class GrupoAcesso implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="id_grupo_acesso")
	@Id
	@GeneratedValue(generator="gera_id_grupo_acesso",strategy=GenerationType.AUTO)
	private Integer id;
	
	@Size(min=4, message="Digite o nome com pelo menos 4 caracteres")
	@Column(length=50,nullable=false)
	private String nome;
	
	public GrupoAcesso() {
		super();
	}
	public GrupoAcesso(Integer id) {
		super();
		this.id = id;
	}
	public GrupoAcesso(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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
		GrupoAcesso other = (GrupoAcesso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}