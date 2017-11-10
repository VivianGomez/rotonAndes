package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.CondicionTecnica;

public class DAOTablaCondicionTecnica {
	/**
	 * Arraylist de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> condiciones;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;	

	/**
	 * Metodo constructor que crea DAOTablaCondicion
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaCondicionTecnica() {
		condiciones = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de Condicions
	 * <b>post: </b> Todos los recurso del arreglo de Condicions han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : condiciones){
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
	 * Metodo que, usando la conexion a la base de datos, saca todos los Condicions de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM Condicion;
	 * @return Arraylist con los Condicions de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<CondicionTecnica> darCondicionesTecnicas() throws SQLException, Exception {
		ArrayList<CondicionTecnica> conditions = new ArrayList<CondicionTecnica>();

		String sql = "SELECT * FROM CONDICION_TECNICA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		condiciones.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idCondicion = rs.getLong("ID_CONDICION");
			String condicion= rs.getString("CONDICION");
			Long idEspacio = rs.getLong("ID_ESPACIO");

			conditions.add(new CondicionTecnica(idCondicion, condicion, idEspacio));
		}
		return conditions;
	}


	
	
	/**
	 * Metodo que busca el Condicion con el id que entra como parametro.
	 * @param idCondicion - Id del Condicion a buscar
	 * @return Condicion encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public CondicionTecnica buscarCondicionPorId(Long idCondicion) throws SQLException, Exception 
	{
		CondicionTecnica condicion = null;

		String sql = "SELECT * FROM CONDICION_TECNICA WHERE ID_CONDICION =" + idCondicion;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		condiciones.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long idCondicion2 = rs.getLong("ID_CONDICION");
			String Condicion2= rs.getString("CONDICION");
			Long idEspacio = rs.getLong("ID_ESPACIO");

			
			condicion = new CondicionTecnica(idCondicion2, Condicion2, idEspacio);
		}

		return condicion;
	}
	
	/**
	 * Metodo que agrega el Condicion que entra como parametro a la base de datos.
	 * @param condicion - la Condicion a agregar. Condicion !=  null
	 * <b> post: </b> se ha agregado la Condicion a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que la Condicion baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la Condicion a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCondicion(CondicionTecnica condicion) throws SQLException, Exception {

		String sql = "INSERT INTO CONDICION_TECNICA (ID_CONDICION, CONDICION, ID_ESPACIO) VALUES (";
		sql += condicion.getIdCondicion() + ",'";
		sql += condicion.getCondicion() + "',";
		sql += condicion.getIdEspacio() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		condiciones.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el Condicion que entra como parametro en la base de datos.
	 * @param condicion - la Condicion a actualizar. Condicion !=  null
	 * <b> post: </b> se ha actualizado la Condicion en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Condicion.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateCondicion(CondicionTecnica condicion) throws SQLException, Exception {

		String sql = "UPDATE CONDICION_TECNICA SET ";
		sql += "CONDICION ='" +condicion.getCondicion() + "',"; 
		sql += "ID_ESPACIO=" + condicion.getIdEspacio();
		sql += " WHERE ID_CONDICION = " + condicion.getIdCondicion();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		condiciones.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el Condicion que entra como parametro en la base de datos.
	 * @param condicion - la Condicion a borrar. Condicion !=  null
	 * <b> post: </b> se ha borrado la Condicion en la base de datos en la transaction actual. pendiente que el  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la Condicion.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteCondicion(CondicionTecnica condicion) throws SQLException, Exception {

		String sql = "DELETE FROM CONDICION_TECNICA";
		sql += " WHERE ID_CONDICION = " + condicion.getIdCondicion();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		condiciones.add(prepStmt);
		prepStmt.executeQuery();
	}
}
