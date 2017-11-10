package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingrediente_Producto;

public class DAOTablaIngrediente_Producto {
	/**
	 * Arraylist de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> ingredientes_productos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;	

	/**
	 * Metodo constructor que crea DAOTablaIngrediente_Producto
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaIngrediente_Producto() {
		ingredientes_productos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de ingredientes_productos
	 * <b>post: </b> Todos los recurso del arreglo de ingredientes_productos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : ingredientes_productos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexion que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Metodo que, usando la conexion a la base de datos, saca todos los ingredientes_productos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM INGREDIENTE_PRODUCTO;
	 * @return Arraylist con los ingredientes_productos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Ingrediente_Producto> darIngrediente_Productos() throws SQLException, Exception {
		ArrayList<Ingrediente_Producto> zones = new ArrayList<Ingrediente_Producto>();

		String sql = "SELECT * FROM INGREDIENTE_PRODUCTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		ingredientes_productos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id_ingrediente = rs.getLong("ID_INGREDIENTE");
			Long id_producto= rs.getLong("ID_PRODUCTO");

			zones.add(new Ingrediente_Producto(id_ingrediente, id_producto));
		}
		return zones;
	}


	/**
	 * Metodo que agrega el ingrediente_producto que entra como parametro a la base de datos.
	 * @param ingrediente_producto - la ingrediente_producto a agregar. ingrediente_producto !=  null
	 * <b> post: </b> se ha agregado la ingrediente_producto a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que la ingrediente_producto baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la ingrediente_producto a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addIngrediente_Producto(Long idIngrediente, Long idProducto) throws SQLException, Exception {

		String sql = "INSERT INTO INGREDIENTE_PRODUCTO (ID_INGREDIENTE, ID_PRODUCTO) VALUES (";
		sql += idIngrediente + ",'";
		sql += idProducto + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		ingredientes_productos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
}
