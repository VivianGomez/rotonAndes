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

import tm.RotondAndesTM;
import vos.CondicionTecnica;
import vos.Restaurante;

@Path("condicionesTecnicas")
public class RotondAndesCondicionTecnicaServices {
	
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
	 * Metodo que expone servicio REST usando GET que da todos los condicions de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/condicions
	 * @return Json con todos los condicions de la base de datos o json con 
	 * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getcondicions() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<CondicionTecnica> condiciones;
		try {
			condiciones = tm.darCondicionesTecnicas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(condiciones).build();
	}

	/**
	 * Metodo que expone servicio REST usando GET que busca el condicion con el id que entra como parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/condicions/<<nit>>" para la busqueda"
	 * @param nit - Id de la condicion a buscar que entra en la URL como parametro 
	 * @return Json con el condicion encontrado con el nombre que entra como parametro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getcondicion( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			CondicionTecnica v = tm.buscarCondicionPorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}


	/**
	 * Metodo que expone servicio REST usando POST que agrega el condicion que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/condicions/condicion
	 * @param condicion - condicion a agregar
	 * @return Json con el condicion que agrego o Json con el error que se produjo
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addcondicion(CondicionTecnica condicion) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addCondicionTecnica(condicion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(condicion).build();
	}

	/**
	 * Metodo que expone servicio REST usando POST que agrega los condicions que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/condicions/varios
	 * @param condicions - condicions a agregar. 
	 * @return Json con el condicion que agrego o Json con el error que se produjo
	 */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addcondicion(List<CondicionTecnica> condicions) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addCondiciones(condicions);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(condicions).build(); 
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCondicion(CondicionTecnica condicion) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			System.out.println("ss");

			tm.updateCondicionesTecnicas(condicion);
			return Response.status(200).entity(condicion).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCondicion(CondicionTecnica condicion) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteCondicion(condicion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(condicion).build();
	}


}
