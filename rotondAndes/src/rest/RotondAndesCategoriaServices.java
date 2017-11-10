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
import vos.Categoria;
import vos.Restaurante;

@Path("categorias")
public class RotondAndesCategoriaServices {

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
	 * Metodo que expone servicio REST usando GET que da todos los Categorias de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/Categorias
	 * @return Json con todos los Categorias de la base de datos o json con 
	 * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCategorias() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Categoria> categorias;
		try {
			categorias = tm.darCategorias();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(categorias).build();
	}

	/**
	 * Metodo que expone servicio REST usando GET que busca el Categoria con el id que entra como parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/Categorias/<<nit>>" para la busqueda"
	 * @param nit - Id de la Categoria a buscar que entra en la URL como parametro 
	 * @return Json con el Categoria encontrado con el nombre que entra como parametro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getCategoria( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Categoria v = tm.buscarCategoriaPorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que expone servicio REST usando GET que busca el Categoria con el nombre que entra como parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/Categorias/<<nombre>>" para la busqueda"
	 * @param nombre - Nombre del Categoria a buscar que entra en la URL como parametro 
	 * @return Json con el Categorias encontrado con el nombre que entra como parametro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getCategoriaNombre( @QueryParam("nombre") String nombre) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Categoria categoria;
		try {
			if (nombre == null || nombre.length() == 0)
				throw new Exception("Nombre del Categoria no valido");
			categoria = tm.buscarCategoriaPorNombre(nombre);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(categoria).build();
	}


	/**
	 * Metodo que expone servicio REST usando POST que agrega el Categoria que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/Categorias/Categoria
	 * @param Categoria - Categoria a agregar
	 * @return Json con el Categoria que agrego o Json con el error que se produjo
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCategoria(Categoria Categoria) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addCategoria(Categoria);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Categoria).build();
	}

	/**
	 * Metodo que expone servicio REST usando POST que agrega los Categorias que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/Categorias/varios
	 * @param Categorias - Categorias a agregar. 
	 * @return Json con el Categoria que agrego o Json con el error que se produjo
	 */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCategoria(List<Categoria> Categorias) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addCategorias(Categorias);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Categorias).build(); 
	}
	



	

}
