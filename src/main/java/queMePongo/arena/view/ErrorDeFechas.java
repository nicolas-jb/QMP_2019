package queMePongo.arena.view;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import queMePongo.model.usuaries.Evento;

import java.time.LocalDate;

public class ErrorDeFechas extends Dialog<Evento> {
	
	public ErrorDeFechas(WindowOwner owner) {
		super(owner, new Evento(LocalDate.now(), ""));
		this.setTitle("ERROR!");
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		new Label(mainPanel)
				.setText("El formato de las fechas debe ser: dd/mm/yyyy")
				.setFontSize(18);
	}
	
	@Override
	protected void addActions(Panel actions) {
		new Button(actions)
				.setCaption("Ok")
				.onClick(this::accept)
				.setAsDefault()
				.disableOnError();
	}
}
