
package rest;


import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import tm.RotondAndesTM;
import vos.IngresarIngrediente;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/RotondAndes/rest/IngresarIngrediente/...
 * @author vn.gomez_kk.penaranda
 */
@Path("IngresarIngrediente")
public class RotondAndesIngresarIngredienteRestauranteServices {

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}

    /**
     * Metodo que expone servicio REST usando POST que agrega el Ingrediente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ingresosRestaurante/ingresoRestaurante
     * @param ingresoRestaurante - ingresoRestaurante a agregar
     * @return Json con el ingresoRestaurante que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addIngresosRestaurante(IngresarIngrediente ingresoRestaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addIngresarIngredienteRestaurante(ingresoRestaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingresoRestaurante).build();
	}	
}
