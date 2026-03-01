package br.com.ajasoftware.repository.despesas;

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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.ajasoftware.filtro.FiltroGeral;
import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.model.conta.Conta;
import br.com.ajasoftware.model.despesas.Despesa;
import br.com.ajasoftware.repository.transactional.Transactional;
import br.com.ajasoftware.service.msg.NegocioException;

public class DespesasRepository implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	private List<Predicate> criarPredicatesParaFiltro(FiltroDespesas filtro, Root<Despesa> despesaRoot,
			From<?, ?> clienteJoin) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if(filtro.getId()!=null) {
			predicates.add(builder.equal(despesaRoot.get("id"), filtro.getId()));
		}
		if(filtro.getConta()!=null) {
			predicates.add(builder.equal(despesaRoot.get("conta"), filtro.getConta()));
		}
		if(filtro.getDataInicial()!=null){
			predicates.add(builder.greaterThanOrEqualTo(despesaRoot.get("data"), filtro.getDataInicial()));
		}
		if(filtro.getDataFinal()!=null){
			predicates.add(builder.lessThanOrEqualTo(despesaRoot.get("data"), filtro.getDataFinal()));
		}
		if(filtro.getCodCliente()!=null) {
			predicates.add(builder.equal(clienteJoin.get("id"), filtro.getCodCliente().getId()));
		}

		return predicates;
	}

	public List<Despesa> filtrados(FiltroDespesas filtro) {
		
		From<?, ?> orderByFromEntity = null;
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Despesa> criteriaQuery = builder.createQuery(Despesa.class);

		Root<Despesa> despesaRoot = criteriaQuery.from(Despesa.class);
		From<?,?> clienteJoin = despesaRoot.join("codCliente", JoinType.INNER);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, despesaRoot, clienteJoin);

		criteriaQuery.select(despesaRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));


		String nomePropriedadeOrdenacao = StringUtils.isNotBlank(filtro.getPropriedadeOrdenacao())?
				filtro.getPropriedadeOrdenacao():"data";
				orderByFromEntity = despesaRoot;			

				if (filtro.isAscendente()) {
					criteriaQuery.orderBy(builder.asc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
				} else{
					criteriaQuery.orderBy(builder.desc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
				}

				TypedQuery<Despesa> query = manager.createQuery(criteriaQuery);
				if(filtro.getQuantidadeRegistros()!=FiltroGeral.TODOS) {
					query.setFirstResult(filtro.getPrimeiroRegistro());
					query.setMaxResults(filtro.getQuantidadeRegistros());
				}

				return query.getResultList();
	}
	public int quantidadeFiltrados(FiltroDespesas filtro){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		
		Root<Despesa> despesaRoot = criteriaQuery.from(Despesa.class);
		From<?,?> clienteJoin = despesaRoot.join("codCliente", JoinType.INNER);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, despesaRoot, clienteJoin);

		criteriaQuery.select(builder.count(despesaRoot));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> query = manager.createQuery(criteriaQuery);

		return query.getSingleResult().intValue();
	}
	
	@Transactional
	public Despesa guardar(Despesa p) throws NegocioException{
		// TODO Auto-generated method stub
		return manager.merge(p);
	}

	@Transactional
	public void excluir(Despesa usu) throws NegocioException{
		// TODO Auto-generated method stub
		try{
			Long qtde= 0l;
			
			if(qtde==0){
				usu = manager.find(Despesa.class, usu.getId());
				manager.remove(usu);
				manager.flush();
			}
			else{
				throw new NegocioException("Despesa não pode ser excluída.");
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
	
	public Long getDespesaPorCliente(Cliente c) {
		try {
			return (Long)manager.createQuery(" select count(*) from Despesa where codCliente.id = :cli ")
							.setParameter("cli", c.getId())
							.getSingleResult();
		}
		catch(NoResultException ex) {
			return 0l;
		}
	}

	public Long getDespesaPorConta(Conta conta) {
		// TODO Auto-generated method stub
		try {
			return (Long)manager.createQuery(" select count(*) from Despesa where codConta.id = :conta ")
							.setParameter("conta", conta.getId())
							.getSingleResult();
		}
		catch(NoResultException ex) {
			return 0l;
		}
	}
}