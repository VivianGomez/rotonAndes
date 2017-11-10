package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Tiene_Pedido;
import vos.Zona;

public class DAOTablaTiene_Pedidos {
	/**
	 * Arraylist de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> tiene_pedidos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;	

	/**
	 * Metodo constructor que crea DAOTablaTiene_Pedido
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaTiene_Pedidos() {
		tiene_pedidos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de tiene_pedidos
	 * <b>post: </b> Todos los recurso del arreglo de tiene_pedidos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : tiene_pedidos){
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
	 * Metodo que, usando la conexion a la base de datos, saca todos los tiene_pedidos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM TIENEN_PEDIDOS;
	 * @return Arraylist con los tiene_pedidos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Tiene_Pedido> darTiene_Pedidos() throws SQLException, Exception {
		ArrayList<Tiene_Pedido> tiene_pedidoss = new ArrayList<Tiene_Pedido>();

		String sql = "SELECT * FROM TIENEN_PEDIDOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		tiene_pedidos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id_restaurante = rs.getLong("ID_RESTAURANTE");
			Long id_pedido= rs.getLong("ID_PEDIDO");

			tiene_pedidoss.add(new Tiene_Pedido(id_restaurante, id_pedido));
		}
		return tiene_pedidoss;
	}


	/**
	 * Metodo que agrega el tiene_pedido que entra como parametro a la base de datos.
	 * <b> post: </b> se ha agregado la tiene_pedido a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que la tiene_pedido baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la tiene_pedido a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addTiene_Pedido(Long idRestaurante, Long idPedido) throws SQLException, Exception {

		String sql = "INSERT INTO TIENEN_PEDIDOS (ID_RESTAURANTE, ID_PEDIDO) VALUES (";
		sql += idRestaurante + ",";
		sql += idPedido + ")";

		System.out.println("sql:"+sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		tiene_pedidos.add(prepStmt);
		prepStmt.executeQuery();

	}
}
