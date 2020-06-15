package queMePongo.model.prendas;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.sf.oval.guard.Pre;
import org.hibernate.annotations.Cascade;
import queMePongo.model.excepciones.FaltaComponenteException;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

@Getter
@Entity
@NoArgsConstructor
public class Prenda {
	@Id
	@GeneratedValue
	private long id;

	private String nombre;
	@Enumerated(EnumType.STRING)
	private Material material;
	@ManyToOne
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private TipoPrenda tipoPrenda;
	@Enumerated(EnumType.STRING)
	//@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Color colorPrimario;
	@Enumerated(EnumType.STRING)
	//@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Color colorSecundario;
	@Enumerated(EnumType.STRING)
	private Trama trama;
	private String fotoPrenda;

    /*Constructor para no inicializar prenda sin que tenga nada de esto y sin ningún componente ppal en null (por si
    alguien la crea directamente con el constructor*/
	
	public Prenda(TipoPrenda unTipoPrenda, Material unMaterial, Trama unaTrama, Color unColor, Color otroColor, String fotoPrenda) {
		if (unTipoPrenda == null || unColor == null || unMaterial == null) {
			throw new FaltaComponenteException();
		}
		this.tipoPrenda = unTipoPrenda;
		this.material = unMaterial;
		this.colorPrimario = unColor;
		this.colorSecundario = otroColor;
		this.trama = unaTrama;
		this.fotoPrenda = fotoPrenda != null ? fotoPrenda : "/fotos/remera.png";
	}
	public Prenda(TipoPrenda unTipoPrenda, Material unMaterial, Trama unaTrama, Color unColor, Color otroColor, String fotoPrenda, String nombre){
		this(unTipoPrenda,unMaterial,unaTrama,unColor,otroColor,fotoPrenda);
		this.nombre = nombre;
	}
	
	public boolean razonableAcordeATemperatura(Double temperatura) {
		return tipoPrenda.razonableAcordeATemperatura(temperatura);
	}
	
	public TipoPrenda getTipoPrenda() {
		return tipoPrenda;
	}
	
	public Categoria getCategoria() {
		return this.tipoPrenda.getCategoria();
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public Color getColorPrimario() {
		return colorPrimario;
	}
	
	public Optional<Color> getColorSecundario() {
		return Optional.ofNullable(colorSecundario);
	}
	
	public Trama getTrama() {
		return trama;
	}
	
	public Capa getCapa() {
		return tipoPrenda.getCapa();
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
