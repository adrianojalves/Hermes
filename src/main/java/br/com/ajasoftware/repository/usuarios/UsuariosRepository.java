package br.com.ajasoftware.repository.usuarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.ajasoftware.filtro.FiltroGeral;
import br.com.ajasoftware.model.usuario.Usuario;
import br.com.ajasoftware.repository.transactional.Transactional;
import br.com.ajasoftware.service.msg.NegocioException;

public class UsuariosRepository implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Usuario porLogin(String login) throws NegocioException {
		Usuario u =null;
		try{
			u =(Usuario)manager.createQuery("  from Usuario where login = :login ")
				.setParameter("login", login)
				.getSingleResult();
		}
		catch(NoResultException ex){
			throw new NegocioException("Usuário não encontrado.");
		}
		return u;
	}

	protected List<Predicate> criarPredicatesParaFiltro(FiltroUsuario filtro, Root<Usuario> usuarioRoot) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if(StringUtils.isNotBlank(filtro.getNome())){
			predicates.add(builder.like(builder.upper(usuarioRoot.get("nome")), "%" + filtro.getNome().toUpperCase() + "%"));
		}
		if(filtro.getId()!=null) {
			predicates.add(builder.equal(usuarioRoot.get("id"), filtro.getId()));
		}

		return predicates;
	}

	public List<Usuario> filtrados(FiltroUsuario filtro) {
		
		From<?, ?> orderByFromEntity = null;
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);

		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, usuarioRoot);

		criteriaQuery.select(usuarioRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));


		String nomePropriedadeOrdenacao = StringUtils.isNotBlank(filtro.getPropriedadeOrdenacao())?
				filtro.getPropriedadeOrdenacao():"nome";
				orderByFromEntity = usuarioRoot;			

				if (filtro.isAscendente()) {
					criteriaQuery.orderBy(builder.asc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
				} else{
					criteriaQuery.orderBy(builder.desc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
				}

				TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
				if(filtro.getQuantidadeRegistros()!=FiltroGeral.TODOS) {
					query.setFirstResult(filtro.getPrimeiroRegistro());
					query.setMaxResults(filtro.getQuantidadeRegistros());
				}

				return query.getResultList();
	}
	public int quantidadeFiltrados(FiltroUsuario filtro){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		
		Root<Usuario> objetoRoot = criteriaQuery.from(Usuario.class);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, objetoRoot);

		criteriaQuery.select(builder.count(objetoRoot));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> query = manager.createQuery(criteriaQuery);

		return query.getSingleResult().intValue();
	}
	
	@Transactional
	public Usuario guardar(Usuario p) throws NegocioException{
		// TODO Auto-generated method stub
		return manager.merge(p);
	}

	@Transactional
	public void excluir(Usuario usu) throws NegocioException{
		// TODO Auto-generated method stub
		try{
			Long qtde= 0l;
			
			if(qtde==0){
				usu = manager.find(Usuario.class, usu.getId());
				manager.remove(usu);
				manager.flush();
			}
			else{
				throw new NegocioException("Usuário não pode ser excluído.");
			}
		}
		catch(NegocioException e){
			throw new NegocioException(e.getMessage());
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new NegocioException("A exclusão não pode ser executada!");
		}
	}
}