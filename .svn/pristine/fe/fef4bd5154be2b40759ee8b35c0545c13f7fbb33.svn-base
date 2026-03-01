package br.com.ajasoftware.completers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ajasoftware.model.cliente.Cliente;
import br.com.ajasoftware.model.conta.Conta;
import br.com.ajasoftware.repository.geral.RepositoryAll;

@Named
@RequestScoped
public class CompleterMethods {
	
	@Inject
	private RepositoryAll repository;
	
	public List<Cliente> completaCliente(String nome){
		List<Cliente> retorno = null;
		
		retorno = repository.porCampoSemStatus(nome.toUpperCase()+"%", 
				" upper(nome) ", "Cliente", " like ", " order by nome", Cliente.class);
		
		return retorno;
	}
	
	public List<Conta> completaConta(String nome){
		List<Conta> retorno = null;
		
		retorno = repository.porCampoSemStatus("%"+nome.toUpperCase()+"%", 
				" id||upper(nome) ", "Conta", " like ", " order by nome", Conta.class);
		
		return retorno;
	}
}
