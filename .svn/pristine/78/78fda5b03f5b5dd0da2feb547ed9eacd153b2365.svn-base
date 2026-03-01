package br.com.ajasoftware.repository.geral;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;


public class RepositoryAll implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public <T> T porId(Object id, Class<?> classe) {
		if(id!=null) {
			return (T) manager.find(classe, id);
		}
		return null;
	}
	
	public <T> List<T> porCampoSemStatus(Object valor, String campo, String classe, String comparacao, String orderBy,
								Class<T> type) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(classe)){

			return manager.createQuery(" from "+classe+" where "+campo+" "+comparacao+" :campo"+
							orderBy , type)
					.setParameter("campo", valor)
					.getResultList();
		}
		else{
			return null;
		}
	}
}