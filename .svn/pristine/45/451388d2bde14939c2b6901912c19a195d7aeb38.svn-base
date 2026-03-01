package br.com.ajasoftware.repository.factory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServlet;

import org.flywaydb.core.Flyway;

import br.com.ajasoftware.service.msg.NegocioException;

public class CreateConfiguration extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static EntityManagerFactory factory;
	
	public CreateConfiguration() throws NegocioException{
		configurar();
	}

	private void configurar() throws NegocioException {
		try{
			String senha = "pamonha";
			Flyway flyway = Flyway.configure()
								  .baselineDescription("Base Migration")
								  .locations("classpath:db/migrations")
								  .sqlMigrationSuffixes(".sql")
								  .baselineOnMigrate(true).dataSource("jdbc:postgresql://localhost:5432/hermes", "postgres", senha).load();
			flyway.migrate();
			if(factory==null)
				factory = Persistence.createEntityManagerFactory("AJAPU");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
//		finally {
//			if(factory==null){
//				throw new NegocioException("Erro ao conectar no banco de dados.");
//			}
//		}
	}

	public static EntityManagerFactory getFactory() {
		return factory;
	}
}