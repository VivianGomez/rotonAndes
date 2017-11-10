
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
import vos.TipoComida;
import vos.TipoComida;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/RotondAndes/rest/tiposComida/...
 * @author vn.gomez_kk.penaranda
 */
@Path("tiposComida")
public class RotondAndesTipoComidaServices {

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
	 * Metodo que expone servicio REST usando GET que da todos los tiposComida de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/tiposComida
	 * @return Json con todos los tiposComida de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTipoComidas() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<TipoComida> tiposComida;
		try {
			tiposComida = tm.darTiposComida();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(tiposComida).build();
	}

	 /**
     * Metodo que expone servicio REST usando GET que busca el tipoComida con el id que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/tiposComida/<<id>>" para la busqueda"
     * @param nit - Nit del tipoComida a buscar que entra en la URL como parametro 
     * @return Json con el tipoComida encontrado con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getTipoComida( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			TipoComida v = tm.buscarTipoComidaPorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

//    /**
//     * Metodo que expone servicio REST usando GET que busca el tipoComida con el nombre que entra como parametro
//     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/tiposComida/<<nombre>>" para la busqueda"
//     * @param clasificacion - Clasificacion del tipoComida a buscar que entra en la URL como parametro 
//     * @return Json con el tiposComida encontrado con el nombre que entra como parametro o json con 
//     * el error que se produjo
//     */
//	@GET
//	@Path( "{clasificacion}" )
//	@Produces( { MediaType.APPLICATION_JSON } )
//	public Response getTipoComidaNombre( @QueryParam("clasificacion") String clasificacion) {
//		System.out.println(clasificacion+",,"+getPath());
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		try {
//			if (clasificacion == null || clasificacion.length() == 0){
//				System.out.println(clasificacion.length());
//				throw new Exception("Clasificacion del tipoComida no valido");
//				}
//			TipoComida v = tm.buscarTipoComidaPorClasificacion(clasificacion);
//			return Response.status( 200 ).entity( v ).build( );			
//
//			
//		} catch (Exception e) {
//			System.out.println("HOLA");
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//	}


    /**
     * Metodo que expone servicio REST usando POST que agrega el tipoComida que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/tiposComida/tipoComida
     * @param tipoComida - tipoComida a agregar
     * @return Json con el tipoComida que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTipoComida(TipoComida tipoComida) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addTipoComida(tipoComida);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(tipoComida).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTipoComida(TipoComida tipoComida) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			System.out.println("ss");

			tm.updateTipoComida(tipoComida);
			return Response.status(200).entity(tipoComida).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
}
