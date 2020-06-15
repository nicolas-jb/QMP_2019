package queMePongo.arena;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;
import queMePongo.arena.view.VentanaPrincipal;

public class QueMePongoApplication extends Application {
	
	public static void main(String[] args) {
		new QueMePongoApplication().start();
	}
	
	@Override
	protected Window<?> createMainWindow() {
		return new VentanaPrincipal(this);
	}
	
}
