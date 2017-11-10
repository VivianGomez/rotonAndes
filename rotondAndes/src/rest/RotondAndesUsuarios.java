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
import vos.Usuario;
import vos.Usuario;

@Path("usuarios")
public class RotondAndesUsuarios {

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
	 * Metodo que expone servicio REST usando GET que da todos los usuarios de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios
	 * @return Json con todos los usuarios de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsuarios() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> usuarios;
		try {
			usuarios = tm.darUsuarios();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}
	
	@GET
	@Path("{darTodosLosClientes}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsuariosClientes(@PathParam("darTodosLosClientes") String darTodosLosClientes) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> usuarios;
		try {
			usuarios = tm.darUsuariosClientes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}

	 /**
     * Metodo que expone servicio REST usando GET que busca el Usuario con el nit que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios/<<nit>>" para la busqueda"
     * @param id - Nit del Usuario a buscar que entra en la URL como parametro 
     * @return Json con el Usuario encontrado con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getUsuario( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Usuario v = tm.buscarUsuarioPorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

    /**
     * Metodo que expone servicio REST usando GET que busca el Usuario con el nombre que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios/<<nombre>>" para la busqueda"
     * @param nombre - Nombre del Usuario a buscar que entra en la URL como parametro 
     * @return Json con el usuarios encontrado con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getUsuarioNombre( @QueryParam("nombre") String nombre) {
		System.out.println(nombre+",,"+getPath());
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if (nombre == null || nombre.length() == 0){
				System.out.println(nombre.length());
				throw new Exception("Nombre del cliente no valido");
				}
			Usuario v = tm.buscarUsuarioPorNombre(nombre);
			return Response.status( 200 ).entity( v ).build( );			

			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


    /**
     * Metodo que expone servicio REST usando POST que agrega el Usuario que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios/Usuario
     * @param Usuario - Usuario a agregar
     * @return Json con el Usuario que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUsuario(Usuario Usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addUsuario(Usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Usuario).build();
	}
	
    
	
    /**
     * Metodo que expone servicio REST usando PUT que actualiza el Usuario que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios
     * @param Usuario - Usuario a actualizar. 
     * @return Json con el Usuario que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(Usuario Usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			System.out.println("ss");

			tm.updateUsuario(Usuario);
			return Response.status(200).entity(Usuario).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el Usuario que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios
     * @param Usuario - Usuario a aliminar. 
     * @return Json con el Usuario que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(Usuario Usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteUsuario(Usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Usuario).build();
	}
	
}
