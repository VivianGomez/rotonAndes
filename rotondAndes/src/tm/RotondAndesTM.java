/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: RotondAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.sun.corba.se.impl.legacy.connection.SocketFactoryConnectionImpl;
import com.sun.swing.internal.plaf.metal.resources.metal;

import dao.DAOTablaCategoria;
import dao.DAOTablaClienteNoRegistrado;
import dao.DAOTablaClienteRegistrado;
import dao.DAOTablaCondicionTecnica;
import dao.DAOTablaEspacio;
import dao.DAOTablaIngrediente;
import dao.DAOTablaIngrediente_Producto;
import dao.DAOTablaMenu;
import dao.DAOTablaPedido;
import dao.DAOTablaProducto;
import dao.DAOTablaReserva;
import dao.DAOTablaRestaurante;
import dao.DAOTablaTiene_Pedidos;
import dao.DAOTablaTiene_Producto;
import dao.DAOTablaTipoComida;
import dao.DAOTablaUsuario;
import dao.DAOTablaValor_Producto;
import dao.DAOTablaZona;
import vos.Categoria;
import vos.ClienteNoRegistrado;
import vos.ClienteRegistrado;
import vos.CondicionTecnica;
import vos.Espacio;
import vos.Ingrediente;
import vos.IngresarIngrediente;
import vos.IngresarMenu;
import vos.IngresarPedido;
import vos.IngresarProducto;
import vos.IngresoClientesPorAdministrador;
import vos.IngresoRestaurantesPorAdministrador;
import vos.IngresoZonaPorAdministrador;
import vos.Menu;
import vos.Pedido;
import vos.Producto;
import vos.Reserva;
import vos.Restaurante;
import vos.TipoComida;
import vos.Usuario;
import vos.Valor_Producto;
import vos.Zona;

/**
 * Transaction Manager de la aplicacion (TM)
 * Fachada en patron singleton de la aplicacion
 * @author vn.gomez_kk.penaranda
 */
public class RotondAndesTM {


	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;


	/**
	 * Metodo constructor de la clase RotondAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto RotondAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////


	/**
	 * Metodo que modela la transaccion que retorna todos los restaurantes de la base de datos.
	 * @return ListaRestaurantes - objeto que modela  un arreglo de restaurantes. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Restaurante> darRestaurantes() throws Exception {
		List<Restaurante> restaurantes;
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			restaurantes = daoRestaurantes.darRestaurantes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurantes;
	}

	public List<Producto> darProductos() throws Exception {
		List<Producto> productos;
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.darProductos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}


	public List<Categoria> darCategorias() throws Exception {
		List<Categoria> categorias;
		DAOTablaCategoria daoCategorias = new DAOTablaCategoria();
		try 
		{
			this.conn = darConexion();
			daoCategorias.setConn(conn);
			categorias = daoCategorias.darCategorias();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCategorias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return categorias;
	}

	public List<CondicionTecnica> darCondicionesTecnicas() throws Exception {
		List<CondicionTecnica> condiciones;
		DAOTablaCondicionTecnica daoCondiciones = new DAOTablaCondicionTecnica();
		try 
		{
			this.conn = darConexion();
			daoCondiciones.setConn(conn);
			condiciones = daoCondiciones.darCondicionesTecnicas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCondiciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return condiciones;
	}


	public List<Espacio> darEspacios() throws Exception {
		List<Espacio> espacios;
		DAOTablaEspacio daoEspacios = new DAOTablaEspacio();
		try 
		{
			this.conn = darConexion();
			daoEspacios.setConn(conn);
			espacios = daoEspacios.darEspacios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspacios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return espacios;
	}

	public List<Pedido> darPedidos() throws Exception {
		List<Pedido> pedidos;
		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		try 
		{
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			pedidos = daoPedidos.darPedidos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedidos;
	}

	public List<Reserva> darReservas() throws Exception {
		List<Reserva> reservas;
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try 
		{
			this.conn = darConexion();
			daoReservas.setConn(conn);
			reservas = daoReservas.darReservas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reservas;
	}

	public List<TipoComida> darTiposComida() throws Exception {
		List<TipoComida> tipos;
		DAOTablaTipoComida daoTiposComida = new DAOTablaTipoComida();
		try 
		{
			this.conn = darConexion();
			daoTiposComida.setConn(conn);
			tipos = daoTiposComida.darTiposComidas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTiposComida.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return tipos;
	}

	public List<Zona> darZonas() throws Exception {
		List<Zona> zonas;
		DAOTablaZona daoZonas = new DAOTablaZona();
		try 
		{
			this.conn = darConexion();
			daoZonas.setConn(conn);
			zonas = daoZonas.darZonas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zonas;
	}

	public List<Ingrediente> darIngredientes() throws Exception {
		List<Ingrediente> ingredientes;
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingredientes = daoIngredientes.darIngredientes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredientes;
	}

	public List<Menu> darMenus() throws Exception {
		List<Menu> menus;
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		try 
		{
			this.conn = darConexion();
			daoMenus.setConn(conn);
			menus = daoMenus.darMenus();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menus;
	}

	public List<Usuario> darUsuarios() throws Exception {
		List<Usuario> usuarios;
		DAOTablaUsuario  daoUsuario = new DAOTablaUsuario();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			usuarios = daoUsuario.darUsuarios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}

	public List<ClienteRegistrado> darClientesRegistrados() throws Exception {
		List<ClienteRegistrado> clienteRegistrado;
		DAOTablaClienteRegistrado daoClienteRegistrado = new DAOTablaClienteRegistrado();
		try 
		{
			this.conn = darConexion();
			daoClienteRegistrado.setConn(conn);
			clienteRegistrado = daoClienteRegistrado.darClientesRegistrados();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClienteRegistrado.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clienteRegistrado;
	}

	public List<ClienteNoRegistrado> darClientesNoRegistrados() throws Exception {
		List<ClienteNoRegistrado> clientesNoRegistrados;
		DAOTablaClienteNoRegistrado daoClientes = new DAOTablaClienteNoRegistrado();
		try 
		{
			this.conn = darConexion();
			daoClientes.setConn(conn);
			clientesNoRegistrados = daoClientes.darClientesNoRegistrados();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientesNoRegistrados;
	}

	/**
	 * Metodo que modela la transaccion que busca el restaurante en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del restaurante a buscar. name != null
	 * @return Restaurante - restaurante que  contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Restaurante buscarRestaurantePorNombre(String nombre) throws Exception {
		Restaurante restaurante;
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			restaurante = daoRestaurantes.buscarRestaurantePorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurante;
	}

	public Menu buscarMenuPorNombre(String nombre) throws Exception {
		Menu menu;
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		try 
		{
			this.conn = darConexion();
			daoMenus.setConn(conn);
			menu = daoMenus.buscarMenuPorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menu;
	}

	public Ingrediente buscarIngredientePorNombre(String nombre) throws Exception {
		Ingrediente ingrediente;
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingrediente = daoIngredientes.buscarIngredientePorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingrediente;
	}

	public Producto buscarProductoPorNombre(String nombre) throws Exception {
		Producto productos;
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.buscarProductoPorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}

	public Categoria buscarCategoriaPorNombre(String nombre) throws Exception {
		Categoria categoria;
		DAOTablaCategoria daoCategoria = new DAOTablaCategoria();
		try 
		{
			this.conn = darConexion();
			daoCategoria.setConn(conn);
			categoria = daoCategoria.buscarCategoriaPorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCategoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return categoria;
	}


	public Zona buscarZonaPorNombre(String nombre) throws Exception {
		Zona zona;
		DAOTablaZona daoZona = new DAOTablaZona();
		try 
		{
			this.conn = darConexion();
			daoZona.setConn(conn);
			zona = daoZona.buscarZonaPorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zona;
	}

	public Usuario buscarUsuarioPorNombre(String nombre) throws Exception {
		Usuario usuario;
		DAOTablaUsuario daoUsuario = new DAOTablaUsuario();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			usuario = daoUsuario.buscarUsuarioPorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuario;
	}

	public ClienteRegistrado buscarClienteRegistradoPorNombre(String nombre) throws Exception {
		ClienteRegistrado cliente;
		DAOTablaClienteRegistrado daoCliente = new DAOTablaClienteRegistrado();
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			cliente = daoCliente.buscarClienteRegistradoPorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}


	public ClienteNoRegistrado buscarClienteNoRegistradoPorNombre(String nombre) throws Exception {
		ClienteNoRegistrado cliente;
		DAOTablaClienteNoRegistrado daoCliente = new DAOTablaClienteNoRegistrado();
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			cliente = daoCliente.buscarClienteNoRegistradoPorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}



	/**
	 * Metodo que modela la transaccion que busca el restaurante en la base de datos con el nit que entra como parametro.
	 * @param nit - nit del restaurante a buscar. nit != null
	 * @return Restaurante - Resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Restaurante buscarRestaurantePorNit(Long nit) throws Exception {
		Restaurante restaurante;
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			restaurante = daoRestaurantes.buscarRestaurantePorNit(nit);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurante;
	}

	public Menu buscarMenuPorId(Long id) throws Exception {
		Menu menu;
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		try 
		{
			this.conn = darConexion();
			daoMenus.setConn(conn);
			menu = daoMenus.buscarMenuPorNit(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menu;
	}

	public Pedido buscarPedidoPorNumeroDeOrden(Long id) throws Exception {
		Pedido pedido;
		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		try 
		{
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			pedido = daoPedidos.buscarPedidoPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedido;
	}


	public Reserva buscarReservaPorId(Long id) throws Exception {
		Reserva reserva;
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try 
		{
			this.conn = darConexion();
			daoReservas.setConn(conn);
			reserva = daoReservas.buscarReservaPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reserva;
	}

	public TipoComida buscarTipoComidaPorId(Long id) throws Exception {
		TipoComida tipoComida;
		DAOTablaTipoComida daoTablaTipoComida = new DAOTablaTipoComida();
		try 
		{
			this.conn = darConexion();
			daoTablaTipoComida.setConn(conn);
			tipoComida = daoTablaTipoComida.buscarTipoComidaPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaTipoComida.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return tipoComida;
	}

	public Zona buscarZonaPorId(Long id) throws Exception {
		Zona zona;
		DAOTablaZona daoTablaZonas = new DAOTablaZona();
		try 
		{
			this.conn = darConexion();
			daoTablaZonas.setConn(conn);
			zona = daoTablaZonas.buscarZonaPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zona;
	}

	public Ingrediente buscarIngredientePorNit(Long id) throws Exception {
		Ingrediente ingrediente;
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingrediente = daoIngredientes.buscarIngredientePorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingrediente;
	}

	public Producto buscarProductoPorId(Long id) throws Exception {
		Producto producto;
		DAOTablaProducto daoTablaProductos = new DAOTablaProducto();
		try 
		{
			this.conn = darConexion();
			daoTablaProductos.setConn(conn);
			producto = daoTablaProductos.buscarProductoPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return producto;
	}

	public Espacio buscarEspacioPorId(Long id) throws Exception {
		Espacio espacio;
		DAOTablaEspacio daoEspacios = new DAOTablaEspacio();
		try 
		{
			this.conn = darConexion();
			daoEspacios.setConn(conn);
			espacio = daoEspacios.buscarEspacioPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspacios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return espacio;
	}

	public Categoria buscarCategoriaPorId(Long id) throws Exception {
		Categoria categoria;
		DAOTablaCategoria daoTablaCategoria = new DAOTablaCategoria();
		try 
		{
			this.conn = darConexion();
			daoTablaCategoria.setConn(conn);
			categoria = daoTablaCategoria.buscarCategoriaPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaCategoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return categoria;
	}

	public CondicionTecnica buscarCondicionPorId(Long id) throws Exception {
		CondicionTecnica condicion;
		DAOTablaCondicionTecnica tablaCondicion = new DAOTablaCondicionTecnica();
		try 
		{
			this.conn = darConexion();
			tablaCondicion.setConn(conn);
			condicion = tablaCondicion.buscarCondicionPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				tablaCondicion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return condicion;
	}

	public Usuario buscarUsuarioPorId(Long id) throws Exception {
		Usuario usuario;
		DAOTablaUsuario tablaUsuario = new DAOTablaUsuario();
		try 
		{
			this.conn = darConexion();
			tablaUsuario.setConn(conn);
			usuario = tablaUsuario.buscarUsuarioPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				tablaUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuario;
	}

	public ClienteRegistrado buscarClienteRegistradoPorId(Long id) throws Exception {
		ClienteRegistrado cliente;
		DAOTablaClienteRegistrado tablaCliente = new DAOTablaClienteRegistrado();
		try 
		{
			this.conn = darConexion();
			tablaCliente.setConn(conn);
			cliente = tablaCliente.buscarClienteRegistradoPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				tablaCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}


	public ClienteNoRegistrado buscarClienteNoRegistradoPorId(Long id) throws Exception {
		ClienteNoRegistrado cliente;
		DAOTablaClienteNoRegistrado tablaCliente = new DAOTablaClienteNoRegistrado();
		try 
		{
			this.conn = darConexion();
			tablaCliente.setConn(conn);
			cliente = tablaCliente.buscarClienteNoRegistradoPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				tablaCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo restaurante a la base de datos.
	 * <b> post: </b> se ha agregado el restaurante que entra como parametro
	 * @param restaurante - el restaurante a agregar. restaurante != null
	 * @throws Exception - cualquier error que se genere agregando el restaurante
	 */
	public void addRestaurante(Restaurante restaurante) throws Exception {
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.addRestaurante(restaurante);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addIngrediente(Ingrediente ingrediente) throws Exception {
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.addIngrediente(ingrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addMenu(Menu menu) throws Exception {
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			daoMenus.addMenu(menu);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addEspacio(Espacio espacio) throws Exception {
		DAOTablaEspacio daoEspacios = new DAOTablaEspacio();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoEspacios.setConn(conn);
			daoEspacios.addEspacio(espacio);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspacios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addCondicionTecnica(CondicionTecnica condicion) throws Exception {
		DAOTablaCondicionTecnica daoCondiciones = new DAOTablaCondicionTecnica();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoCondiciones.setConn(conn);
			daoCondiciones.addCondicion(condicion);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCondiciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void addProducto(Producto producto) throws Exception {
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.addProducto(producto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addCategoria(Categoria categorie) throws Exception {
		DAOTablaCategoria categoria = new DAOTablaCategoria();
		try 
		{
			this.conn = darConexion();
			categoria.setConn(conn);
			categoria.addCategoria(categorie);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				categoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void addPedido(Pedido pedido) throws Exception {
		DAOTablaPedido pedido2 = new DAOTablaPedido();
		try 
		{
			this.conn = darConexion();
			pedido2.setConn(conn);
			pedido2.addPedido(pedido);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				pedido2.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addReserva(Reserva reserva) throws Exception {
		DAOTablaReserva reserva2 = new DAOTablaReserva();
		try 
		{
			this.conn = darConexion();
			reserva2.setConn(conn);
			reserva2.addReserva(reserva);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				reserva2.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addTipoComida(TipoComida tipo) throws Exception {
		DAOTablaTipoComida tipoComida = new DAOTablaTipoComida();
		try 
		{
			this.conn = darConexion();
			tipoComida.setConn(conn);
			tipoComida.addTipo_Comida(tipo);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				tipoComida.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addZona(Zona zona) throws Exception {
		DAOTablaZona zona2 = new DAOTablaZona();
		try 
		{
			this.conn = darConexion();
			zona2.setConn(conn);
			zona2.addZona(zona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				zona2.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuario daoUsuario = new DAOTablaUsuario();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoUsuario.addUsuario(usuario);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addClienteRegistrado(ClienteRegistrado cliente) throws Exception {
		DAOTablaClienteRegistrado daoCliente = new DAOTablaClienteRegistrado();
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			daoCliente.addClienteRegistrado(cliente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addClienteNoRegistrado(ClienteNoRegistrado cliente) throws Exception {
		DAOTablaClienteNoRegistrado daoCliente = new DAOTablaClienteNoRegistrado();
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			daoCliente.addClienteNoRegistrado(cliente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que agrega los restaurantes que entran como parametro a la base de datos.
	 * <b> post: </b> se han agregado los restaurantes que entran como parametro
	 * @param restaurantes - objeto que modela una lista de restaurantes y se estos se pretenden agregar. restaurantes != null
	 * @throws Exception - cualquier error que se genera agregando los restaurantes
	 */
	public void addRestaurantes(List<Restaurante> restaurantes) throws Exception {
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoRestaurantes.setConn(conn);
			Iterator<Restaurante> it = restaurantes.iterator();
			while(it.hasNext())
			{
				daoRestaurantes.addRestaurante(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addEspacios(List<Espacio> espacios) throws Exception {
		DAOTablaEspacio daoEspacios = new DAOTablaEspacio();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoEspacios.setConn(conn);
			Iterator<Espacio> it = espacios.iterator();
			while(it.hasNext())
			{
				daoEspacios.addEspacio(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoEspacios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addProductos(List<Producto> productos) throws Exception {
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoProductos.setConn(conn);
			Iterator<Producto> it = productos.iterator();
			while(it.hasNext())
			{
				daoProductos.addProducto(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addCondiciones(List<CondicionTecnica> condiciones) throws Exception {
		DAOTablaCondicionTecnica daoCondiciones = new DAOTablaCondicionTecnica();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoCondiciones.setConn(conn);
			Iterator<CondicionTecnica> it = condiciones.iterator();
			while(it.hasNext())
			{
				daoCondiciones.addCondicion(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoCondiciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addIngredientes(List<Ingrediente> ingredientes) throws Exception {
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoIngredientes.setConn(conn);
			Iterator<Ingrediente> it = ingredientes.iterator();
			while(it.hasNext())
			{
				daoIngredientes.addIngrediente(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addMenus(List<Menu> menus) throws Exception {
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoMenus.setConn(conn);
			Iterator<Menu> it = menus.iterator();
			while(it.hasNext())
			{
				daoMenus.addMenu(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addCategorias(List<Categoria> categorias) throws Exception {
		DAOTablaCategoria daoCategorias = new DAOTablaCategoria();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoCategorias.setConn(conn);
			Iterator<Categoria> it = categorias.iterator();
			while(it.hasNext())
			{
				daoCategorias.addCategoria(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoCategorias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void addPedidos(List<Pedido> pedidos) throws Exception {
		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoPedidos.setConn(conn);
			Iterator<Pedido> it = pedidos.iterator();
			while(it.hasNext())
			{
				daoPedidos.addPedido(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addReservas(List<Reserva> reservas) throws Exception {
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoReservas.setConn(conn);
			Iterator<Reserva> it = reservas.iterator();
			while(it.hasNext())
			{
				daoReservas.addReserva(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoReservas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void addClientesRegistrados(List<ClienteRegistrado> clientes) throws Exception {
		DAOTablaClienteRegistrado daoTablaClientes = new DAOTablaClienteRegistrado();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoTablaClientes.setConn(conn);
			Iterator<ClienteRegistrado> it = clientes.iterator();
			while(it.hasNext())
			{
				daoTablaClientes.addClienteRegistrado(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTablaClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addClientesNoRegistrados(List<ClienteNoRegistrado> clientes) throws Exception {
		DAOTablaClienteNoRegistrado daoTablaClientes = new DAOTablaClienteNoRegistrado();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoTablaClientes.setConn(conn);
			Iterator<ClienteNoRegistrado> it = clientes.iterator();
			while(it.hasNext())
			{
				daoTablaClientes.addClienteNoRegistrado(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTablaClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que actualiza el restaurante que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el restaurante que entra como parametro
	 * @param restaurante - Restaurante a actualizar. restaurante != null
	 * @throws Exception - cualquier error que se genera actualizando los restaurantes
	 */
	public void updateRestaurante(Restaurante restaurante) throws Exception {
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.updateRestaurante(restaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateEspacio(Espacio espacio) throws Exception {
		DAOTablaEspacio daoEspacios = new DAOTablaEspacio();
		try 
		{
			this.conn = darConexion();
			daoEspacios.setConn(conn);
			daoEspacios.updateEspacio(espacio);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspacios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateIngrediente(Ingrediente ingrediente) throws Exception {
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		try 
		{
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.updateIngrediente(ingrediente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateMenu(Menu menu) throws Exception {
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		try 
		{
			this.conn = darConexion();
			daoMenus.setConn(conn);
			daoMenus.updateMenu(menu);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateCondicionesTecnicas(CondicionTecnica condiciones) throws Exception {
		DAOTablaCondicionTecnica daoCondiciones = new DAOTablaCondicionTecnica();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoCondiciones.setConn(conn);
			daoCondiciones.updateCondicion(condiciones);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCondiciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void updateProducto(Producto producto) throws Exception {
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.updateProducto(producto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updatePedido(Pedido pedido) throws Exception {
		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		try 
		{
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			daoPedidos.updatePedido(pedido);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void updateReserva(Reserva reserva) throws Exception {
		DAOTablaReserva daoProductos = new DAOTablaReserva();
		try 
		{
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.updateReserva(reserva);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuario daoUsuarios = new DAOTablaUsuario();
		try 
		{
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.updateUsuario(usuario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateClienteRegistrado(ClienteRegistrado cliente) throws Exception {
		DAOTablaClienteRegistrado daoClientes = new DAOTablaClienteRegistrado();
		try 
		{
			this.conn = darConexion();
			daoClientes.setConn(conn);
			daoClientes.updateClienteRegistrado(cliente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateZona(Zona zona) throws Exception {
		DAOTablaZona daoZona = new DAOTablaZona();
		try 
		{
			this.conn = darConexion();
			daoZona.setConn(conn);
			daoZona.updateZona(zona);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateClienteNoRegistrado(ClienteNoRegistrado cliente) throws Exception {
		DAOTablaClienteNoRegistrado daoClientes = new DAOTablaClienteNoRegistrado();
		try 
		{
			this.conn = darConexion();
			daoClientes.setConn(conn);
			daoClientes.updateClienteNoRegistrado(cliente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateTipoComida(TipoComida tipoComida) throws Exception {
		DAOTablaTipoComida daoTipoComida = new DAOTablaTipoComida();
		try 
		{
			this.conn = darConexion();
			daoTipoComida.setConn(conn);
			daoTipoComida.updateTipo_Comida(tipoComida);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTipoComida.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el restaurante que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el restaurante que entra como parametro
	 * @param restaurante - Restaurante a eliminar. restaurante != null
	 * @throws Exception - cualquier error que se genera actualizando los restaurantes
	 */
	public void deleteRestaurante(Restaurante restaurante) throws Exception {
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.deleteRestaurante(restaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void deleteProducto(Producto producto) throws Exception {
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.deleteProducto(producto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteCondicion(CondicionTecnica condicion) throws Exception {
		DAOTablaCondicionTecnica daoCondicion = new DAOTablaCondicionTecnica();
		try 
		{
			this.conn = darConexion();
			daoCondicion.setConn(conn);
			daoCondicion.deleteCondicion(condicion);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCondicion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteEspacio(Espacio espacio) throws Exception {
		DAOTablaEspacio daoEspacio = new DAOTablaEspacio();
		try 
		{
			this.conn = darConexion();
			daoEspacio.setConn(conn);
			daoEspacio.deleteEspacio(espacio);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspacio.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteIngrediente(Ingrediente ingrediente) throws Exception {
		DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
		try 
		{
			this.conn = darConexion();
			daoIngrediente.setConn(conn);
			daoIngrediente.deleteIngrediente(ingrediente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngrediente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteMenu(Menu menu) throws Exception {
		DAOTablaMenu daoMenu = new DAOTablaMenu();
		try 
		{
			this.conn = darConexion();
			daoMenu.setConn(conn);
			daoMenu.deleteMenu(menu);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenu.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deletePedido(Pedido pedido) throws Exception {
		DAOTablaPedido pedidoAEliminar = new DAOTablaPedido();
		try 
		{
			this.conn = darConexion();
			pedidoAEliminar.setConn(conn);
			pedidoAEliminar.deletePedido(pedido);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				pedidoAEliminar.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void deleteReserva(Reserva reserva) throws Exception {
		DAOTablaReserva daoReserva = new DAOTablaReserva();
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoReserva.deleteReserva(reserva);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuario daoUsuario = new DAOTablaUsuario();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoUsuario.deleteUsuario(usuario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteClienteRegistrado(ClienteRegistrado cliente) throws Exception {
		DAOTablaClienteRegistrado daoCliente = new DAOTablaClienteRegistrado();
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			daoCliente.deleteClienteRegistrado(cliente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void deleteClienteNoRegistrado(ClienteNoRegistrado cliente) throws Exception {
		DAOTablaClienteNoRegistrado daoCliente = new DAOTablaClienteNoRegistrado();
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			daoCliente.deleteClienteNoRegistrado(cliente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void deleteZona(Zona zona) throws Exception {
		DAOTablaZona daoZona = new DAOTablaZona();
		try 
		{
			this.conn = darConexion();
			daoZona.setConn(conn);
			daoZona.deleteZona(zona);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addIngresarIngredienteRestaurante(IngresarIngrediente ingresoRestaurante) throws Exception {
		DAOTablaIngrediente daoIngredientes = new DAOTablaIngrediente();
		DAOTablaUsuario daoUsuario = new DAOTablaUsuario();
		DAOTablaIngrediente_Producto daoIngrediente_Producto = new DAOTablaIngrediente_Producto();
		try 
		{
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoUsuario.setConn(conn);
			daoIngrediente_Producto.setConn(conn);
			if(daoUsuario.buscarUsuarioPorId(ingresoRestaurante.getIdUsuarioRestaurante())!=null){
				if(daoUsuario.buscarUsuarioPorId(ingresoRestaurante.getIdUsuarioRestaurante()).getRol().equalsIgnoreCase("Restaurante")){
					System.out.println("Se verifico el restaurante");
					daoIngredientes.addIngrediente(ingresoRestaurante.getIngrediente());
					daoIngrediente_Producto.addIngrediente_Producto(ingresoRestaurante.getIngrediente().getIdIngrediente(), ingresoRestaurante.getIdProducto());
					conn.commit();
				}
				else
				{
					throw new Exception("El usuario no tiene nol de Restaurante, su rol es "+daoUsuario.buscarUsuarioPorId(ingresoRestaurante.getIdUsuarioRestaurante()).getRol());
				}	
			}
			else
			{
				throw new Exception("No existe un usuario restaurante con la identificacion ingresada");
			}	

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				daoUsuario.cerrarRecursos();
				daoIngrediente_Producto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addIngresarProductoRestaurante(IngresarProducto ingresoRestaurante) throws Exception {
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		DAOTablaUsuario daoUsuario = new DAOTablaUsuario();
		DAOTablaTiene_Producto daoTieneProducto = new DAOTablaTiene_Producto();
		try 
		{
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoUsuario.setConn(conn);
			daoTieneProducto.setConn(conn);
			if(daoUsuario.buscarUsuarioPorId(ingresoRestaurante.getIdUsuarioRestaurante())!=null){
				if(daoUsuario.buscarUsuarioPorId(ingresoRestaurante.getIdUsuarioRestaurante()).getRol().equalsIgnoreCase("Restaurante")){
					System.out.println("Se verifico el restaurante");
					daoProductos.addProducto(ingresoRestaurante.getProducto());
					daoTieneProducto.addTiene_Producto(ingresoRestaurante.getIdMenu(), ingresoRestaurante.getProducto().getIdProducto(), ingresoRestaurante.getProducto().getIdCategoria());
					//if((daoTieneProducto.buscarTiene_Producto(ingresoRestaurante.getIdMenu(), ingresoRestaurante.getProducto().getIdProducto(), ingresoRestaurante.getProducto().getIdCategoria()))!=null){
					conn.commit();
					//}
				}
				else
				{
					throw new Exception("El usuario no tiene nol de Restaurante, su rol es "+daoUsuario.buscarUsuarioPorId(ingresoRestaurante.getIdUsuarioRestaurante()).getRol());
				}	
			}
			else
			{
				throw new Exception("No existe un usuario restaurante con la identificacion ingresada");
			}	

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				daoTieneProducto.cerrarRecursos();
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addIngresarMenuRestaurante(IngresarMenu ingresoRestaurante) throws Exception {
		DAOTablaMenu daoMenus = new DAOTablaMenu();
		DAOTablaUsuario daoUsuario = new DAOTablaUsuario();
		try 
		{
			this.conn = darConexion();
			daoMenus.setConn(conn);
			daoUsuario.setConn(conn);
			if(daoUsuario.buscarUsuarioPorId(ingresoRestaurante.getIdUsuarioRestaurante())!=null){
				if(daoUsuario.buscarUsuarioPorId(ingresoRestaurante.getIdUsuarioRestaurante()).getRol().equalsIgnoreCase("Restaurante")){
					System.out.println("Se verifico el restaurante");
					daoMenus.addMenu(ingresoRestaurante.getMenu());
					conn.commit();
				}
				else
				{
					throw new Exception("El usuario no tiene nol de Restaurante, su rol es "+daoUsuario.buscarUsuarioPorId(ingresoRestaurante.getIdUsuarioRestaurante()).getRol());
				}	
			}
			else
			{
				throw new Exception("No existe un usuario restaurante con la identificacion ingresada");
			}	

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void addClienteAdministrador(IngresoClientesPorAdministrador cliente) throws Exception {
		DAOTablaClienteRegistrado daoClientes = new DAOTablaClienteRegistrado();
		DAOTablaUsuario daoUsuarios= new DAOTablaUsuario();
		try 
		{
			this.conn = darConexion();
			daoClientes.setConn(conn);
			daoUsuarios.setConn(conn);
			if(daoUsuarios.buscarUsuarioPorId(cliente.getIdAdministrador())!=null)
			{
				if(daoUsuarios.buscarUsuarioPorId(cliente.getIdAdministrador()).getRol().equalsIgnoreCase("Administrador"))
					daoClientes.addClienteRegistrado(cliente.getCliente());
				else
					throw new Exception("El identificador dado no le corresponde a un administrador. Su rol es " + daoUsuarios.buscarUsuarioPorId(cliente.getIdAdministrador()).getRol());
				conn.commit();
			}
			else
				throw new Exception("El identificador dado no le pertenece a un usuario registrado en el sistema.");


		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addRestauranteAdministrador(IngresoRestaurantesPorAdministrador elemento) throws Exception {
		DAOTablaRestaurante daoTablaRestaurante = new DAOTablaRestaurante();
		DAOTablaUsuario daoUsuarios= new DAOTablaUsuario();
		try 
		{
			this.conn = darConexion();
			daoTablaRestaurante.setConn(conn);
			daoUsuarios.setConn(conn);
			if(daoUsuarios.buscarUsuarioPorId(elemento.getIdAdministrador())!=null)
			{
				if(daoUsuarios.buscarUsuarioPorId(elemento.getIdAdministrador()).getRol().equalsIgnoreCase("Administrador"))
					daoTablaRestaurante.addRestaurante(elemento.getRestaurante());
				else
					throw new Exception("El identificador dado no le corresponde a un administrador. Su rol es " + daoUsuarios.buscarUsuarioPorId(elemento.getIdAdministrador()).getRol());
				conn.commit();
			}
			else
				throw new Exception("El identificador dado no le pertenece a un usuario registrado en el sistema.");


		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaRestaurante.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addZonaAdministrador(IngresoZonaPorAdministrador zona) throws Exception {
		DAOTablaZona daoTablaZona = new DAOTablaZona();
		DAOTablaUsuario daoUsuarios= new DAOTablaUsuario();
		try 
		{
			this.conn = darConexion();
			daoTablaZona.setConn(conn);
			daoUsuarios.setConn(conn);
			if(daoUsuarios.buscarUsuarioPorId(zona.getIdAdministrador())!=null)
			{
				if(daoUsuarios.buscarUsuarioPorId(zona.getIdAdministrador()).getRol().equalsIgnoreCase("Administrador"))
					daoTablaZona.addZona(zona.getZona());
				else
					throw new Exception("El identificador dado no le corresponde a un administrador. Su rol es " + daoUsuarios.buscarUsuarioPorId(zona.getIdAdministrador()).getRol());
				conn.commit();
			}
			else
				throw new Exception("El identificador dado no le pertenece a un usuario registrado en el sistema.");


		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaZona.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void addIngresoPedido(IngresarPedido ingresoPedido) throws Exception {
		DAOTablaPedido daoTablaPedido = new DAOTablaPedido();
		DAOTablaTiene_Pedidos daoTiene_Pedidos = new DAOTablaTiene_Pedidos();
		DAOTablaValor_Producto daoValorProducto = new DAOTablaValor_Producto();

		try 
		{
			this.conn = darConexion();
			daoTablaPedido.setConn(conn);
			daoTiene_Pedidos.setConn(conn);
			daoValorProducto.setConn(conn);

			Iterator<Long> it = ingresoPedido.getProductos().iterator();
			Integer totalAPagar = 0;

			boolean faltan = false;
			while(it.hasNext() && !faltan) {
				Long prodAct = it.next();
				Valor_Producto valAct = daoValorProducto.buscarProductoPorId(prodAct);
				if(valAct.getCantidad()>0){
					totalAPagar += valAct.getPrecio();
					daoValorProducto.actualizarCantidadProductos(prodAct, valAct.getCantidad()-1);
				}
				else
					faltan = true;
			}
			System.out.println("Total"+totalAPagar);
			Pedido pedido = new Pedido(ingresoPedido.getIdPedido(), totalAPagar);
			daoTablaPedido.addPedido(pedido);
			daoTiene_Pedidos.addTiene_Pedido(ingresoPedido.getIdRestaurante(), ingresoPedido.getIdPedido());
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaPedido.cerrarRecursos();
				daoTiene_Pedidos.cerrarRecursos();
				daoValorProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public ArrayList<Producto> buscarProductoFiltros(Long idRes) throws Exception {
		ArrayList<Producto> productos;
		DAOTablaProducto daoProducto = new DAOTablaProducto();
		try 
		{
			this.conn = darConexion();
			daoProducto.setConn(conn);
			productos = daoProducto.buscarPorFiltrosLosProductosServidosRotondAndes(idRes);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}
	
	public Producto buscarProductoMayorMenu() throws Exception {
		Producto productos;
		DAOTablaProducto daoProductos = new DAOTablaProducto();
		try 
		{
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.buscarProductoEncontradoMayorCantidadMenus();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}
	
	
	public List<Usuario> darUsuariosClientes() throws Exception {
		List<Usuario> usuarios;
		DAOTablaUsuario  daoUsuario = new DAOTablaUsuario();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			usuarios = daoUsuario.darUsuariosCliente();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}

}
