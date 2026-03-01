package br.com.ajasoftware.repository.clientes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.ajasoftware.filtro.FiltroGeral;
import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.repository.despesas.DespesasRepository;
import br.com.ajasoftware.repository.receitas.ReceitasRepository;
import br.com.ajasoftware.repository.transactional.Transactional;
import br.com.ajasoftware.service.msg.NegocioException;

public class ClientesRepository implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@Inject
	private DespesasRepository despesas;
	
	@Inject
	private ReceitasRepository receitas;
	
	private List<Predicate> criarPredicatesParaFiltro(FiltroCliente filtro, Root<Cliente> clienteRoot) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if(StringUtils.isNotBlank(filtro.getNome())){
			predicates.add(builder.like(builder.upper(clienteRoot.get("nome")), "%" + filtro.getNome().toUpperCase() + "%"));
		}
		if(filtro.getId()!=null) {
			predicates.add(builder.equal(clienteRoot.get("id"), filtro.getId()));
		}

		return predicates;
	}

	public List<Cliente> filtrados(FiltroCliente filtro) {
		
		From<?, ?> orderByFromEntity = null;
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = builder.createQuery(Cliente.class);

		Root<Cliente> clienteRoot = criteriaQuery.from(Cliente.class);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, clienteRoot);

		criteriaQuery.select(clienteRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));


		String nomePropriedadeOrdenacao = StringUtils.isNotBlank(filtro.getPropriedadeOrdenacao())?
				filtro.getPropriedadeOrdenacao():"nome";
				orderByFromEntity = clienteRoot;			

				if (filtro.isAscendente()) {
					criteriaQuery.orderBy(builder.asc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
				} else{
					criteriaQuery.orderBy(builder.desc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
				}

				TypedQuery<Cliente> query = manager.createQuery(criteriaQuery);
				if(filtro.getQuantidadeRegistros()!=FiltroGeral.TODOS) {
					query.setFirstResult(filtro.getPrimeiroRegistro());
					query.setMaxResults(filtro.getQuantidadeRegistros());
				}

				return query.getResultList();
	}
	public int quantidadeFiltrados(FiltroCliente filtro){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		
		Root<Cliente> objetoRoot = criteriaQuery.from(Cliente.class);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, objetoRoot);

		criteriaQuery.select(builder.count(objetoRoot));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> query = manager.createQuery(criteriaQuery);

		return query.getSingleResult().intValue();
	}
	
	@Transactional
	public Cliente guardar(Cliente p) throws NegocioException{
		// TODO Auto-generated method stub
		return manager.merge(p);
	}

	@Transactional
	public void excluir(Cliente cliente) throws NegocioException{
		// TODO Auto-generated method stub
		try{
			Long qtde= receitas.getReceitaPorCliente(cliente)+despesas.getDespesaPorCliente(cliente);
			
			if(qtde==0){
				cliente = manager.find(Cliente.class, cliente.getId());
				manager.remove(cliente);
				manager.flush();
			}
			else{
				throw new NegocioException("Não é possível excluir um cliente associado a despesas e receitas.");
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