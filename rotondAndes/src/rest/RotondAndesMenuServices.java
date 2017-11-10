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
import vos.Menu;

@Path("menus")
public class RotondAndesMenuServices {

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
	 * Metodo que expone servicio REST usando GET que da todos los menus de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus
	 * @return Json con todos los menus de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMenus() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Menu> menus;
		try {
			menus = tm.darMenus();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menus).build();
	}

	 /**
     * Metodo que expone servicio REST usando GET que busca el menu con el nit que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus/<<nit>>" para la busqueda"
     * @param nit - Nit del menu a buscar que entra en la URL como parametro 
     * @return Json con el menu encontrado con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getMenu( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Menu v = tm.buscarMenuPorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

    /**
     * Metodo que expone servicio REST usando GET que busca el menu con el nombre que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus/<<nombre>>" para la busqueda"
     * @param nombre - Nombre del menu a buscar que entra en la URL como parametro 
     * @return Json con el menus encontrado con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getMenuNombre( @QueryParam("nombre") String nombre) {
		System.out.println(nombre+",,"+getPath());
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			if (nombre == null || nombre.length() == 0){
				System.out.println(nombre.length());
				throw new Exception("Nombre del menu no valido");
				}
			Menu v = tm.buscarMenuPorNombre(nombre);
			return Response.status( 200 ).entity( v ).build( );			

			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


    /**
     * Metodo que expone servicio REST usando POST que agrega el menu que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus/menu
     * @param menu - menu a agregar
     * @return Json con el menu que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenu(Menu menu) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addMenu(menu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}
	
    
	
    /**
     * Metodo que expone servicio REST usando PUT que actualiza el menu que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus
     * @param menu - menu a actualizar. 
     * @return Json con el menu que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMenu(Menu menu) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			System.out.println("ss");

			tm.updateMenu(menu);
			return Response.status(200).entity(menu).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el menu que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus
     * @param menu - menu a aliminar. 
     * @return Json con el menu que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletemenu(Menu menu) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteMenu(menu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}

}
