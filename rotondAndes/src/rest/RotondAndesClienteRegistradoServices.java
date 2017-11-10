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
import vos.ClienteRegistrado;
import vos.ClienteRegistrado;

@Path("clientesRegistrados")
public class RotondAndesClienteRegistradoServices {

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
	 * Metodo que expone servicio REST usando GET que da todos los clientesRegistrados de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientesRegistrados
	 * @return Json con todos los clientesRegistrados de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientesRegistrados() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<ClienteRegistrado> clientesRegistrados;
		try {
			clientesRegistrados = tm.darClientesRegistrados();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(clientesRegistrados).build();
	}

	 /**
     * Metodo que expone servicio REST usando GET que busca el ClienteRegistrado con el nit que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientesRegistrados/<<nit>>" para la busqueda"
     * @param id - Nit del ClienteRegistrado a buscar que entra en la URL como parametro 
     * @return Json con el ClienteRegistrado encontrado con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getClienteRegistrado( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			ClienteRegistrado v = tm.buscarClienteRegistradoPorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

    /**
     * Metodo que expone servicio REST usando GET que busca el ClienteRegistrado con el nombre que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientesRegistrados/<<nombre>>" para la busqueda"
     * @param nombre - Nombre del ClienteRegistrado a buscar que entra en la URL como parametro 
     * @return Json con el clientesRegistrados encontrado con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getClienteRegistradoNombre( @QueryParam("nombre") String nombre) {
		System.out.println(nombre+",,"+getPath());
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if (nombre == null || nombre.length() == 0){
				System.out.println(nombre.length());
				throw new Exception("Nombre del cliente no valido");
				}
			ClienteRegistrado v = tm.buscarClienteRegistradoPorNombre(nombre);
			return Response.status( 200 ).entity( v ).build( );			

			
		} catch (Exception e) {
			System.out.println("HOLA");
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


    /**
     * Metodo que expone servicio REST usando POST que agrega el ClienteRegistrado que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientesRegistrados/ClienteRegistrado
     * @param ClienteRegistrado - ClienteRegistrado a agregar
     * @return Json con el ClienteRegistrado que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addClienteRegistrado(ClienteRegistrado ClienteRegistrado) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addClienteRegistrado(ClienteRegistrado);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ClienteRegistrado).build();
	}
	
    
	
    /**
     * Metodo que expone servicio REST usando PUT que actualiza el ClienteRegistrado que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientesRegistrados
     * @param ClienteRegistrado - ClienteRegistrado a actualizar. 
     * @return Json con el ClienteRegistrado que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateClienteRegistrado(ClienteRegistrado ClienteRegistrado) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			System.out.println("ss");

			tm.updateClienteRegistrado(ClienteRegistrado);
			return Response.status(200).entity(ClienteRegistrado).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el ClienteRegistrado que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientesRegistrados
     * @param ClienteRegistrado - ClienteRegistrado a aliminar. 
     * @return Json con el ClienteRegistrado que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteClienteRegistrado(ClienteRegistrado ClienteRegistrado) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteClienteRegistrado(ClienteRegistrado);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ClienteRegistrado).build();
	}
	
}
