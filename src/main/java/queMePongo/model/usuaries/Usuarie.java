package queMePongo.model.usuaries;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.annotations.Cascade;
import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.atuendos.Calificacion;
import queMePongo.model.atuendos.Estado;
import queMePongo.model.excepciones.ContraseñaInvalidaException;
import queMePongo.model.excepciones.SuscripcionNoPermiteAgregarGuardarropaException;
import queMePongo.model.guardarropas.Guardarropa;
import queMePongo.model.prendas.Categoria;
import queMePongo.model.usuaries.decisiones.Aceptar;
import queMePongo.model.usuaries.decisiones.Calificar;
import queMePongo.model.usuaries.decisiones.Decision;
import queMePongo.model.usuaries.decisiones.Rechazar;
import queMePongo.model.utils.CodificadorDePassword;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

@Entity
@NoArgsConstructor
@Data
public class Usuarie {
    @Id
    @GeneratedValue
    private long id;
    private String nombre;
    private String contraseña;
    @Transient
    private Stack<Decision> decisiones = new Stack<>();
    @Transient
    private CodificadorDePassword codificadorDePassword = new CodificadorDePassword();

    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Guardarropa> guardarropas = new ArrayList<>();
    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private SensibilidadTermica sensibilidadTermica = new SensibilidadTermica();
    @Enumerated(EnumType.STRING)
    private Suscripcion suscripcion = Suscripcion.GRATUITA;
    @OneToMany
    private List<Atuendo> atuendosAceptados = new ArrayList<>();

    public Usuarie(String nombre) {
        this.nombre = nombre;
    }

    public Usuarie(String nombre, String password) {
        this.nombre = nombre;
        this.contraseña = codificadorDePassword.codificarPass(password);
    }

    public Usuarie(String nombre, Suscripcion suscripcion) {
        this.nombre = nombre;
        this.suscripcion = suscripcion;
    }

    public void agregarGuardarropa(Guardarropa guardarropa) {
        if (!suscripcion.puedeAgregarGuardarropa(guardarropa)) {
            throw new SuscripcionNoPermiteAgregarGuardarropaException();
        }
        guardarropas.add(guardarropa);
    }

    public void recibirAlerta(String alerta) {
        System.out.println(alerta);
    }

    public void aceptar(Atuendo atuendo, Guardarropa guardarropa, LocalDate fecha) {
        decisiones.push(new Aceptar(atuendo));
        atuendo.cambiarEstado(Estado.ACEPTADO);
        atuendo.reservarPrendas(guardarropa, fecha);
        atuendosAceptados.add(atuendo);
    }

    public void aceptar(Atuendo atuendo) {
        decisiones.push(new Aceptar(atuendo));
        atuendo.cambiarEstado(Estado.ACEPTADO);
        atuendosAceptados.add(atuendo);
    }

    public void rechazar(Atuendo atuendo) {
        decisiones.push(new Rechazar(atuendo));
        atuendo.cambiarEstado(Estado.RECHAZADO);
    }


    public void calificar(Atuendo atuendo, Calificacion calificacionGeneral, Pair<Categoria, Calificacion>... calificaciones) {
        decisiones.push(new Calificar(atuendo));
        atuendo.calificar(calificacionGeneral);
        Stream.of(calificaciones).forEach(sensibilidadTermica::agregarCalificacion);
    }

    public void deshacer() {
        decisiones.pop().deshacer();
    }

    public void cambiarSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    public void validarContraseña(String contraseña) {
        CodificadorDePassword codificador = new CodificadorDePassword();
        String contraseñaCodificada = codificador.codificarPass(contraseña);
        if(!this.contraseña.equals(contraseñaCodificada)) throw new ContraseñaInvalidaException();
    }
}
