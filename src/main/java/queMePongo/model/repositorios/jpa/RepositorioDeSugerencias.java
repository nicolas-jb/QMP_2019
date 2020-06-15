package queMePongo.model.repositorios.jpa;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.guardarropas.Sugerencia;
import queMePongo.model.usuaries.Usuarie;
import queMePongo.spark.utils.PersistUtils;

import java.util.List;

public class RepositorioDeSugerencias {
	
	private RepositorioDePrendas repositorioDePrendas = new RepositorioDePrendas();
	
	public Sugerencia getById(Long id){
		return PerThreadEntityManagers.getEntityManager().find(Sugerencia.class, id);
	}
	
	public Sugerencia crearYPersistirSugerencia(Usuarie usuarie, String parteSuperiorId, String parteInferiorId,
	                                      String calzadoId, String accesorioId, String parteSuperior2Id,
	                                      String parteSuperior3Id ){
		Atuendo atuendo = new Atuendo(repositorioDePrendas.getById(parteSuperiorId),
				repositorioDePrendas.getById(parteInferiorId), repositorioDePrendas.getById(calzadoId));
		if(accesorioId != null){
			atuendo.agregarAccesorio(repositorioDePrendas.getById(accesorioId));
		}
		if(parteSuperior2Id != null){
			atuendo.agregarAbrigo(repositorioDePrendas.getById(parteSuperior2Id));
		}
		if(parteSuperior3Id != null){
			atuendo.agregarAbrigo(repositorioDePrendas.getById(parteSuperior3Id));
		}
		Sugerencia sugerencia = new Sugerencia(usuarie,atuendo);
		PersistUtils.persistObjects(sugerencia);
		return sugerencia;
	}
}
