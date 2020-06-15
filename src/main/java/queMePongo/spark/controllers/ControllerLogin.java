package queMePongo.spark.controllers;

import queMePongo.model.excepciones.ContraseñaInvalidaException;
import queMePongo.model.usuaries.Usuarie;
import queMePongo.spark.utils.StringParser;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
import java.util.Map;

import static org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers.getEntityManager;

public class ControllerLogin implements ControllerBase {

    public ModelAndView logUser(Request request, Response response) {
        Map<String, String> params = StringParser.parseBody(request.body());
        String username = params.get("user");
        String contraseña = params.get("pass");

        try {
            Usuarie usuarie = getEntityManager()
                    .createQuery("SELECT u from Usuarie u WHERE u.nombre = :username", Usuarie.class)
                    .setParameter("username", username)
                    .getResultList().get(0);

            usuarie.validarContraseña(contraseña);
            request.session().attribute("userId", usuarie.getId());
            response.redirect("/home");
        } catch (Exception e) {
            redirectToLoginErrorPage(response);
        }

        return null;
    }

    public ModelAndView logPage(Request request, Response response) {
        Long userId = request.session().attribute("userId");
        if(userId != null){
            response.redirect("/home");
        }
        return new ModelAndView(null, "login.hbs");
    }
}
