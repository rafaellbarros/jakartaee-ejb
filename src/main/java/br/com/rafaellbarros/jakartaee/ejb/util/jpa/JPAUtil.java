package br.com.rafaellbarros.jakartaee.ejb.util.jpa;

import org.jboss.logging.Logger;

import javax.persistence.*;

public class JPAUtil {

	private static final Logger logger = Logger.getLogger(JPAUtil.class);

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("keycloak-user-storage-jpa");

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public static void beginTransaction() {
		try {
			logger.info("beginTransaction()");
			getEntityManager().getTransaction().begin();
		} catch (IllegalStateException e) {
			rollBackTransaction();
		}
	}

	public static void commitTransaction() {
		try {
			logger.info("commitTransaction()");
			getEntityManager().getTransaction().commit();
		} catch (IllegalStateException | RollbackException e) {
			logger.error(e.getMessage());
			rollBackTransaction();
		}
	}

	private static void rollBackTransaction() {
		try {
			getEntityManager().getTransaction().rollback();
		} catch (IllegalStateException | PersistenceException e) {
			e.printStackTrace();
		}
	}

}