package queMePongo.model.repositorios;

import queMePongo.model.usuaries.Usuarie;

import java.util.ArrayList;
import java.util.List;


public class RepoDeUsuarios {
	private static RepoDeUsuarios ourInstance = new RepoDeUsuarios();
	
	public static RepoDeUsuarios getInstance() {
		return ourInstance;
	}
	
	private RepoDeUsuarios() {
	}
	
	private List<Usuarie> usuaries = new ArrayList<>();
	
	public void agregarUsuarie(Usuarie usuarie) {
		usuaries.add(usuarie);
	}
	
	public List<Usuarie> getUsuaries() {
		return usuaries;
	}
	
}
