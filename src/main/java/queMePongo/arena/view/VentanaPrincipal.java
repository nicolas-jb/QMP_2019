package queMePongo.arena.view;

import org.apache.commons.collections15.Transformer;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;
import queMePongo.model.excepciones.NoSePuedeParsearLaFecha;
import queMePongo.model.usuaries.Evento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VentanaPrincipal extends SimpleWindow<FormVentanaPrincipal> {
	
	private Table<Evento> tabla;
	
	public VentanaPrincipal(WindowOwner windowOwner) {
		super(windowOwner, new FormVentanaPrincipal());
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
	}
	
	@Override
	public void createMainTemplate(Panel panel) {
		this.setTitle("Que me pongo?");
		
		new Label(panel)
				.setText("Lista de mostrarError")
				.setFontSize(18);
		
		crearBuscadorDeEventos(panel);
		
		tabla = new Table<>(panel, Evento.class);
		tabla.bindItemsToProperty("mostrarError"); //El atributo FormVentanaPrincipal
		tabla.setNumberVisibleRows(10);
		tabla.setWidth(450);
		
		//Los atributos de las columnas vienen de los mostrarError
		new Column<>(tabla)
				.setTitle("Fecha")
				.setFixedSize(100)
				.alignCenter()
				.bindContentsToProperty("fecha").setTransformer(new Transformer<LocalDate, String>() {
			@Override
			public String transform(LocalDate localDate) {
				return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
			}
		});
		
		new Column<>(tabla)
				.setTitle("Nombre")
				.setFixedSize(150)
				.bindContentsToProperty("nombre");
		
		new Column<>(tabla)
				.setTitle("Descripcion")
				.setFixedSize(150)
				.bindContentsToProperty("descripcion");
		
		new Column<>(tabla)
				.setTitle("Posee sugerencias")
				.setFixedSize(150)
				.bindContentsToProperty("sugerencias");
		
		new Button(panel).setCaption("Nuevo Evento")
				.onClick(this::agregarEvento);
		
		new Button(panel).setCaption("Eliminar")
				.onClick(this::eliminarEvento);
	}
	
	public void crearBuscadorDeEventos(Panel owner) {
		Panel panelFiltradorDeEventos = new Panel(owner);
		panelFiltradorDeEventos.setLayout(new HorizontalLayout());
		
		//Los atributos vienen de los FormVentanaPrincipal
		new Label(panelFiltradorDeEventos).setText("Entre");
		
		new TextBox(panelFiltradorDeEventos)
				.setWidth(80)
				.bindValueToProperty("initialDate");
		
		new Label(panelFiltradorDeEventos).setText("Y");
		
		new TextBox(panelFiltradorDeEventos)
				.setWidth(80)
				.bindValueToProperty("endDate");
		
		new Button(panelFiltradorDeEventos)
				.setCaption("Buscar")
				.onClick(this::buscarEventos);
	}
	
	public void agregarEvento() {
		System.out.println("Agregando evento...");
	}
	
	public void eliminarEvento() {
		System.out.println("Eliminando evento...");
	}
	
	public void buscarEventos() {
		tabla.bindItemsToProperty("mostrarError");
		
		try {
			this.getModelObject().buscar();
		} catch (NoSePuedeParsearLaFecha error) {
			popUpFechasInvalidas();
		}
	}
	
	public void popUpFechasInvalidas() {
		new ErrorDeFechas(this).open();
	}
}
