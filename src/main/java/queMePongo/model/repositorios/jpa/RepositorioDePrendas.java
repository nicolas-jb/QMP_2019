package queMePongo.model.repositorios.jpa;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import queMePongo.model.prendas.Prenda;

public class RepositorioDePrendas {
	
	public Prenda getById(String id){
		return PerThreadEntityManagers.getEntityManager().find(Prenda.class, Long.parseLong(id));
	}
}
