package br.com.ajasoftware.repository.contas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.ajasoftware.filtro.FiltroGeral;
import br.com.ajasoftware.model.conta.Conta;
import br.com.ajasoftware.repository.despesas.DespesasRepository;
import br.com.ajasoftware.repository.transactional.Transactional;
import br.com.ajasoftware.service.msg.NegocioException;

public class ContasRepository implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@Inject
	private DespesasRepository despesas;
	
	private List<Predicate> criarPredicatesParaFiltro(FiltroConta filtro, Root<Conta> contaRoot) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if(StringUtils.isNotBlank(filtro.getNome())){
			predicates.add(builder.like(builder.upper(contaRoot.get("nome")), "%" + filtro.getNome().toUpperCase() + "%"));
		}
		if(filtro.getId()!=null) {
			predicates.add(builder.equal(contaRoot.get("id"), filtro.getId()));
		}
		
		if(filtro.getStatus()!=null) {
			predicates.add(builder.equal(contaRoot.get("status"), filtro.getStatus()));
		}

		return predicates;
	}

	public List<Conta> filtrados(FiltroConta filtro) {
		
		From<?, ?> orderByFromEntity = null;
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Conta> criteriaQuery = builder.createQuery(Conta.class);

		Root<Conta> contaRoot = criteriaQuery.from(Conta.class);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, contaRoot);

		criteriaQuery.select(contaRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));


		List<Order> listOrder = new ArrayList<>();
//		String nomePropriedadeOrdenacao = StringUtils.isNotBlank(filtro.getPropriedadeOrdenacao())?
//				filtro.getPropriedadeOrdenacao():"status";
				orderByFromEntity = contaRoot;			

		listOrder.add(builder.desc(orderByFromEntity.get( "status")));
		listOrder.add(builder.asc(orderByFromEntity.get( "nome")));

//				if (filtro.isAscendente() && !nomePropriedadeOrdenacao.equals("status")) {
//					criteriaQuery.orderBy(builder.asc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
//				} else{
//					criteriaQuery.orderBy(builder.desc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
//				}
		criteriaQuery.orderBy(listOrder);

				TypedQuery<Conta> query = manager.createQuery(criteriaQuery);
				if(filtro.getQuantidadeRegistros()!=FiltroGeral.TODOS) {
					query.setFirstResult(filtro.getPrimeiroRegistro());
					query.setMaxResults(filtro.getQuantidadeRegistros());
				}

				return query.getResultList();
	}
	public int quantidadeFiltrados(FiltroConta filtro){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		
		Root<Conta> objetoRoot = criteriaQuery.from(Conta.class);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, objetoRoot);

		criteriaQuery.select(builder.count(objetoRoot));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> query = manager.createQuery(criteriaQuery);

		return query.getSingleResult().intValue();
	}
	
	@Transactional
	public Conta guardar(Conta p) throws NegocioException{
		// TODO Auto-generated method stub
		return manager.merge(p);
	}

	@Transactional
	public void excluir(Conta conta) throws NegocioException{
		// TODO Auto-generated method stub
		try{
			Long qtde = despesas.getDespesaPorConta(conta);
			
			if(qtde==0){
				conta = manager.find(Conta.class, conta.getId());
				manager.remove(conta);
				manager.flush();
			}
			else{
				throw new NegocioException("Não é possível excluir uma conta associada a despesas.");
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