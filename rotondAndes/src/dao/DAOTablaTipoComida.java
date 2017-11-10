package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.TipoComida;

public class DAOTablaTipoComida {
	/**
	 * Arraylist de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> tipos_comida;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;	

	/**
	 * Metodo constructor que crea DAOTablaTipoComida
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaTipoComida() {
		tipos_comida = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de tipo_Comidas
	 * <b>post: </b> Todos los recurso del arreglo de tipo_Comidas han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : tipos_comida){
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
	 * Metodo que, usando la conexion a la base de datos, saca todos los tipo_Comidas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM tipo_Comida;
	 * @return Arraylist con los tipo_Comidas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<TipoComida> darTiposComidas() throws SQLException, Exception {
		ArrayList<TipoComida> tipos = new ArrayList<TipoComida>();

		String sql = "SELECT * FROM TIPO_COMIDA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		tipos_comida.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String tipo= rs.getString("TIPO");

			tipos.add(new TipoComida(id, tipo));
		}
		return tipos;
	}


	
	
	/**
	 * Metodo que busca el tipo_Comida con el id que entra como parametro.
	 * @param idtipo_Comida - Id del tipo_Comida a buscar
	 * @return tipo_Comida encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public TipoComida buscarTipoComidaPorId(Long idTipo_Comida) throws SQLException, Exception 
	{
		TipoComida tipo_Comida = null;

		String sql = "SELECT * FROM TIPO_COMIDA WHERE ID =" + idTipo_Comida;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		tipos_comida.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long id = rs.getLong("ID");
			String tipo= rs.getString("TIPO");

			tipo_Comida = new TipoComida(id, tipo);
		}

		return tipo_Comida;
	}

	/**
	 * Metodo que agrega el tipo_Comida que entra como parametro a la base de datos.
	 * @param tipo_Comida - la tipo_Comida a agregar. tipo_Comida !=  null
	 * <b> post: </b> se ha agregado la tipo_Comida a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que la tipo_Comida baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la tipo_Comida a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addTipo_Comida(TipoComida tipo_Comida) throws SQLException, Exception {

		String sql = "INSERT INTO TIPO_COMIDA (ID, TIPO) VALUES (";
		sql += tipo_Comida.getIdTipoComida() + ",'";
		sql += tipo_Comida.getClasificacion() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		tipos_comida.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el tipo_Comida que entra como parametro en la base de datos.
	 * @param tipo_Comida - la tipo_Comida a actualizar. tipo_Comida !=  null
	 * <b> post: </b> se ha actualizado la tipo_Comida en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el tipo_Comida.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateTipo_Comida(TipoComida tipo_Comida) throws SQLException, Exception {
		
		String sql = "UPDATE TIPO_COMIDA SET ";
		sql += "TIPO= '" + tipo_Comida.getClasificacion();
		sql += "' WHERE ID = " + tipo_Comida.getIdTipoComida();

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		tipos_comida.add(prepStmt);
		prepStmt.executeQuery();
	}
//
//	/**
//	 * Metodo que elimina el tipo_Comida que entra como parametro en la base de datos.
//	 * @param tipo_Comida - la tipo_Comida a borrar. tipo_Comida !=  null
//	 * <b> post: </b> se ha borrado la tipo_Comida en la base de datos en la transaction actual. pendiente que el  master
//	 * haga commit para que los cambios bajen a la base de datos.
//	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la tipo_Comida.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public void deleteTipo_Comida(TipoComida tipo_Comida) throws SQLException, Exception {
//
//		String sql = "DELETE FROM TIPO_COMIDA";
//		sql += " WHERE ID = " + tipo_Comida.getIdTipoComida();
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		tipos_comida.add(prepStmt);
//		prepStmt.executeQuery();
//	}
}
