package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Pedido;

public class DAOTablaPedido {
	/**
	 * Arraylist de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> pedidos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;	

	/**
	 * Metodo constructor que crea DAOTablapedido
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaPedido() {
		pedidos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de pedidos
	 * <b>post: </b> Todos los recurso del arreglo de pedidos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : pedidos){
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
	 * Metodo que, usando la conexion a la base de datos, saca todos los pedidos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM pedido;
	 * @return Arraylist con los pedidos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Pedido> darPedidos() throws SQLException, Exception {
		ArrayList<Pedido> pedido = new ArrayList<Pedido>();

		String sql = "SELECT * FROM PEDIDO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		pedidos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idPedido = rs.getLong("NUMERO_ORDEN");
			double totalAPagar= rs.getDouble("TOTAL_PAGAR");

			pedido.add(new Pedido(idPedido, totalAPagar));
		}
		return pedido;
	}


	
	
	/**
	 * Metodo que busca el pedido con el id que entra como parametro.
	 * @param numeroOrden - Id del pedido a buscar
	 * @return pedido encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Pedido buscarPedidoPorId(Long numeroOrden) throws SQLException, Exception 
	{
		Pedido pedido = null;

		String sql = "SELECT * FROM PEDIDO WHERE NUMERO_ORDEN =" + numeroOrden;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		pedidos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long numeroOrden2 = rs.getLong("NUMERO_ORDEN");
			double totalPagar= rs.getDouble("TOTAL_PAGAR");

			pedido = new Pedido(numeroOrden2, totalPagar);
		}

		return pedido;
	}
	

	/**
	 * Metodo que agrega el pedido que entra como parametro a la base de datos.
	 * @param pedido - la pedido a agregar. pedido !=  null
	 * <b> post: </b> se ha agregado la pedido a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que la pedido baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la pedido a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPedido(Pedido pedido) throws SQLException, Exception {

		String sql = "INSERT INTO PEDIDO (NUMERO_ORDEN, TOTAL_PAGAR) VALUES (";
		sql += pedido.getNumeroOrden() + ",";
		sql += pedido.getTotalAPagar() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		pedidos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el pedido que entra como parametro en la base de datos.
	 * @param pedido - la pedido a actualizar. pedido !=  null
	 * <b> post: </b> se ha actualizado la pedido en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el pedido.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updatePedido(Pedido pedido) throws SQLException, Exception {

		String sql = "UPDATE PEDIDO SET ";
		sql += "TOTAL_PAGAR=" +pedido.getTotalAPagar();
		sql += " WHERE NUMERO_ORDEN = " + pedido.getNumeroOrden();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		pedidos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el pedido que entra como parametro en la base de datos.
	 * @param pedido - la pedido a borrar. pedido !=  null
	 * <b> post: </b> se ha borrado la pedido en la base de datos en la transaction actual. pendiente que el  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la pedido.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deletePedido(Pedido pedido) throws SQLException, Exception {

		String sql = "DELETE FROM PEDIDO";
		sql += " WHERE NUMERO_ORDEN = " + pedido.getNumeroOrden();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		pedidos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
