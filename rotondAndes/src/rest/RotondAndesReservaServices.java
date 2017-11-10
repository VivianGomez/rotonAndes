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
import vos.Reserva;

@Path("reservas")
public class RotondAndesReservaServices {

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
	 * Metodo que expone servicio REST usando GET que da todos los reservas de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/reservas
	 * @return Json con todos los reservas de la base de datos o json con 
	 * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getreservas() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Reserva> reservaes;
		try {
			reservaes = tm.darReservas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservaes).build();
	}

	/**
	 * Metodo que expone servicio REST usando GET que busca el reserva con el id que entra como parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/reservas/<<nit>>" para la busqueda"
	 * @param nit - Id de la reserva a buscar que entra en la URL como parametro 
	 * @return Json con el reserva encontrado con el nombre que entra como parametro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getreserva( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Reserva v = tm.buscarReservaPorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}


	/**
	 * Metodo que expone servicio REST usando POST que agrega el reserva que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/reservas/reserva
	 * @param reserva - reserva a agregar
	 * @return Json con el reserva que agrego o Json con el error que se produjo
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addreserva(Reserva reserva) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addReserva(reserva);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}

	/**
	 * Metodo que expone servicio REST usando POST que agrega los reservas que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/reservas/varios
	 * @param reservas - reservas a agregar. 
	 * @return Json con el reserva que agrego o Json con el error que se produjo
	 */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addreserva(List<Reserva> reservas) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addReservas(reservas);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservas).build(); 
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatereserva(Reserva reserva) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			System.out.println("ss");

			tm.updateReserva(reserva);
			return Response.status(200).entity(reserva).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el reserva que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/reservas
     * @param reserva - reserva a aliminar. 
     * @return Json con el reserva que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletereserva(Reserva reserva) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteReserva(reserva);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}

	
}
