package br.com.ajasoftware.repository.factory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static EntityManagerFactory factory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Segurança: O ideal é ler a senha de uma variável de ambiente (System.getenv("DB_PASSWORD"))
            // Mas para manter simples agora, deixaremos a string.
            String senha = "pamonha"; 
            
            // 1. Roda o Flyway apenas 1 vez na subida da aplicação
            Flyway flyway = Flyway.configure()
                    .baselineDescription("Base Migration")
                    .locations("classpath:db/migrations")
                    .sqlMigrationSuffixes(".sql")
                    .baselineOnMigrate(true)
                    .dataSource("jdbc:postgresql://localhost:5432/hermes", "postgres", senha)
                    .load();
            flyway.migrate();

            // 2. Cria o Factory apenas 1 vez
            factory = Persistence.createEntityManagerFactory("AJAPU");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Falha crítica ao iniciar o banco de dados", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Fechar o factory na descida do Tomcat devolve a memória e mata as conexões!
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }
}