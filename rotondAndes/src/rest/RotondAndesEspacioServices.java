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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Espacio;

@Path("espacios")
public class RotondAndesEspacioServices {

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servnitor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}


	/**
	 * Metodo que expone servicio REST usando GET que da todos los espacios de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/espacios
	 * @return Json con todos los espacios de la base de datos o json con 
	 * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getespacios() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Espacio> espacios;
		try {
			espacios = tm.darEspacios();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(espacios).build();
	}

	/**
	 * Metodo que expone servicio REST usando GET que busca el espacio con el id que entra como parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/espacios/<<nit>>" para la busqueda"
	 * @param nit - Id de la espacio a buscar que entra en la URL como parametro 
	 * @return Json con el espacio encontrado con el nombre que entra como parametro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getespacio( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Espacio v = tm.buscarEspacioPorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	
	
	/**
	 * Metodo que expone servicio REST usando POST que agrega el espacio que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/espacios/espacio
	 * @param espacio - espacio a agregar
	 * @return Json con el espacio que agrego o Json con el error que se produjo
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addespacio(Espacio espacio) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addEspacio(espacio);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(espacio).build();
	}

	/**
	 * Metodo que expone servicio REST usando POST que agrega los espacios que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/espacios/varios
	 * @param espacios - espacios a agregar. 
	 * @return Json con el espacio que agrego o Json con el error que se produjo
	 */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addespacio(List<Espacio> espacios) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addEspacios(espacios);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(espacios).build(); 
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEspacio(Espacio espacio) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			System.out.println("ss");

			tm.updateEspacio(espacio);
			return Response.status(200).entity(espacio).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el espacio que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/espacios
     * @param espacio - espacio a aliminar. 
     * @return Json con el espacio que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEspacio(Espacio espacio) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteEspacio(espacio);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(espacio).build();
	}

}
