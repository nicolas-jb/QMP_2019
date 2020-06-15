package queMePongo.spark.utils;

import static org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers.getEntityManager;

public class PersistUtils {
	
	public static void persistObjects(Object... objects){
		getEntityManager().getTransaction().begin();
		for (Object object : objects) {
			getEntityManager().persist(object);
		}
		getEntityManager().getTransaction().commit();
	}
	
	public static void detachObjects(Object... objects){
		getEntityManager().getTransaction().begin();
		for (Object object : objects) {
			getEntityManager().detach(object);
		}
		getEntityManager().getTransaction().commit();
	}
}
