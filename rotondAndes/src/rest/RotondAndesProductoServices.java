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
import vos.Producto;

@Path("productos")
public class RotondAndesProductoServices {


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
	 * Metodo que expone servicio REST usando GET que da todos los productos de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/productos
	 * @return Json con todos los productos de la base de datos o json con 
	 * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getproductos() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> productos;
		try {
			productos = tm.darProductos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build();
	}

	/**
	 * Metodo que expone servicio REST usando GET que busca el producto con el id que entra como parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/productos/<<nit>>" para la busqueda"
	 * @param nit - Nit del producto a buscar que entra en la URL como parametro 
	 * @return Json con el producto encontrado con el nombre que entra como parametro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getproducto( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Producto v = tm.buscarProductoPorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	@GET
	@Path("{idRestaurante}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getproductosFiltros(@QueryParam( "idRestaurante" ) Long idRestaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> productos;
		try {
			productos = tm.buscarProductoFiltros(idRestaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build();
	}
	
	
	@GET
	@Path("{productoMasOfrecido}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getproductosFiltros(@PathParam( "productoMasOfrecido" ) String productoMasOfrecido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Producto productos;
		try {
			productos = tm.buscarProductoMayorMenu();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build();
	}
	

	/**
	 * Metodo que expone servicio REST usando GET que busca el producto con el nombre que entra como parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/productos/<<nombre>>" para la busqueda"
	 * @param nombre - Nombre del producto a buscar que entra en la URL como parametro 
	 * @return Json con el productos encontrado con el nombre que entra como parametro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getproductoNombre( @QueryParam("nombre") String nombre) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Producto producto;
		try {
			if (nombre == null || nombre.length() == 0)
				throw new Exception("Nombre del producto no valido");
			producto = tm.buscarProductoPorNombre(nombre);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}


	/**
	 * Metodo que expone servicio REST usando POST que agrega el producto que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/productos/producto
	 * @param producto - producto a agregar
	 * @return Json con el producto que agrego o Json con el error que se produjo
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProducto(Producto producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addProducto(producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}

	/**
	 * Metodo que expone servicio REST usando POST que agrega los productos que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/productos/varios
	 * @param productos - productos a agregar. 
	 * @return Json con el producto que agrego o Json con el error que se produjo
	 */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProducto(List<Producto> productos) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addProductos(productos);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build(); 
	}

	/**
	 * Metodo que expone servicio REST usando PUT que actualiza el producto que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/productos
	 * @param producto - producto a actualizar. 
	 * @return Json con el producto que actualizo o Json con el error que se produjo
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProducto(Producto producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateProducto(producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}

	/**
	 * Metodo que expone servicio REST usando DELETE que elimina el producto que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/productos
	 * @param producto - producto a aliminar. 
	 * @return Json con el producto que elimino o Json con el error que se produjo
	 */

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProducto(Producto producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteProducto(producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}
}
