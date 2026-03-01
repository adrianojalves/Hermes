package br.com.ajasoftware.repository.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;

import org.hibernate.Session;

@ApplicationScoped
public class EntityManagerProducer {

	@Produces @RequestScoped
	public Session createEntityManager() {
		return (Session)CreateConfiguration.getFactory().createEntityManager();
	}
	
	public void closeEntityManager(@Disposes EntityManager manager) {
		manager.close();
	}
	
}