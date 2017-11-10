package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Producto;
import vos.Valor_Producto;
import vos.Zona;

public class DAOTablaValor_Producto {
	/**
	 * Arraylist de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> valors_productos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;	

	/**
	 * Metodo constructor que crea DAOTablaValor_Producto
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaValor_Producto() {
		valors_productos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de valors_productos
	 * <b>post: </b> Todos los recurso del arreglo de valors_productos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : valors_productos){
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
	 * Metodo que, usando la conexion a la base de datos, saca todos los valors_productos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VALOR_PRODUCTO;
	 * @return Arraylist con los valors_productos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Valor_Producto> darValor_Productos() throws SQLException, Exception {
		ArrayList<Valor_Producto> valores_productos = new ArrayList<Valor_Producto>();

		String sql = "SELECT * FROM VALOR_PRODUCTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		valors_productos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id_Producto = rs.getLong("ID_PRODUCTO");
			Long id_Restaurante= rs.getLong("ID_RESTAURANTE");
			Integer costo= rs.getInt("COSTO");
			Integer precio= rs.getInt("PRECIO");
			Integer cantidad= rs.getInt("CANTIDAD");

			valores_productos.add(new Valor_Producto(id_Producto, id_Restaurante, costo, precio,cantidad));
		}
		return valores_productos;
	}


	/**
	 * Metodo que agrega el tiene_producto que entra como parametro a la base de datos.
	 * <b> post: </b> se ha agregado la tiene_producto a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que la tiene_producto baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la tiene_producto a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addValor_Producto(Long idProducto, Long idRestaurante, Long idCategoria, Integer costo, Integer precio, Integer cantidad) throws SQLException, Exception {

		String sql = "INSERT INTO VALOR_PRODUCTO (ID_PRODUCTO, ID_RESTAURANTE, COSTO,PRECIO,CANTIDAD) VALUES (";
		sql += idProducto + ",";
		sql += idRestaurante + ",";
		sql += costo + ",";
		sql += precio + ",";
		sql += cantidad + ")";

		System.out.println("sql:"+sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		valors_productos.add(prepStmt);
		System.out.println(sql+"casi");
		prepStmt.executeQuery();

	}
	
	public Valor_Producto buscarProductoPorId(Long id) throws SQLException, Exception 
	{
		Valor_Producto val_producto = null;

		String sql = "SELECT * FROM VALOR_PRODUCTO WHERE ID_PRODUCTO =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		valors_productos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long idProd = rs.getLong("ID_PRODUCTO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			Integer costo= rs.getInt("COSTO");
			Integer precio= rs.getInt("PRECIO");
			Integer cantidad= rs.getInt("CANTIDAD");
			val_producto = new Valor_Producto(idProd, idRestaurante, costo, precio,cantidad);
		}

		return val_producto;
	}
	
	public void actualizarCantidadProductos(Long id, Integer cantidad) throws SQLException, Exception 
	{
		System.out.println("actualizando cantidad de producto...");
		
		String sql = "UPDATE VALOR_PRODUCTO SET ";
		sql += "CANTIDAD=" + cantidad;
		sql += " WHERE ID_PRODUCTO = " + id;
		System.out.println(sql);


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		valors_productos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
}
