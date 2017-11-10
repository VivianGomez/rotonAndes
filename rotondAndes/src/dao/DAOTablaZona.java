package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Zona;

public class DAOTablaZona {
	/**
	 * Arraylist de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> zonas;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;	

	/**
	 * Metodo constructor que crea DAOTablaZona
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaZona() {
		zonas = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de zonas
	 * <b>post: </b> Todos los recurso del arreglo de zonas han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : zonas){
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
	 * Metodo que, usando la conexion a la base de datos, saca todos los zonas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM ZONA;
	 * @return Arraylist con los zonas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Zona> darZonas() throws SQLException, Exception {
		ArrayList<Zona> zones = new ArrayList<Zona>();

		String sql = "SELECT * FROM ZONA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		zonas.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idzona = rs.getLong("ID_ZONA");
			String nombre= rs.getString("NOMBRE");

			zones.add(new Zona(idzona, nombre));
		}
		return zones;
	}


	
	
	/**
	 * Metodo que busca el zona con el id que entra como parametro.
	 * @param idzona - Id del zona a buscar
	 * @return zona encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Zona buscarZonaPorId(Long idzona) throws SQLException, Exception 
	{
		Zona zona = null;

		String sql = "SELECT * FROM ZONA WHERE ID_ZONA =" + idzona;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		zonas.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long idzona2 = rs.getLong("ID_ZONA");
			String zona2= rs.getString("NOMBRE");

			zona = new Zona(idzona2, zona2);
		}

		return zona;
	}
	
	
	public Zona buscarZonaPorNombre(String nombre) throws SQLException, Exception 
	{
		Zona zona = null;

		String sql = "SELECT * FROM ZONA WHERE ZONA =" + "'" + nombre + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		zonas.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long idzona2 = rs.getLong("ID_ZONA");
			String zona2= rs.getString("NOMBRE");

			zona = new Zona(idzona2, zona2);
		}

		return zona;
	}

	/**
	 * Metodo que agrega el zona que entra como parametro a la base de datos.
	 * @param zona - la zona a agregar. zona !=  null
	 * <b> post: </b> se ha agregado la zona a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que la zona baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la zona a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addZona(Zona zona) throws SQLException, Exception {

		String sql = "INSERT INTO ZONA (ID_ZONA, NOMBRE) VALUES (";
		sql += zona.getIdZona() + ",'";
		sql += zona.getNombre() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		zonas.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el zona que entra como parametro en la base de datos.
	 * @param zona - la zona a actualizar. zona !=  null
	 * <b> post: </b> se ha actualizado la zona en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el zona.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateZona(Zona zona) throws SQLException, Exception {

		String sql = "UPDATE ZONA SET ";
		sql += "NOMBRE= '" +zona.getNombre();
		sql += "' WHERE ID_ZONA = " + zona.getIdZona();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		zonas.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el zona que entra como parametro en la base de datos.
	 * @param zona - la zona a borrar. zona !=  null
	 * <b> post: </b> se ha borrado la zona en la base de datos en la transaction actual. pendiente que el  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la zona.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteZona(Zona zona) throws SQLException, Exception {

		String sql = "DELETE FROM ZONA";
		sql += " WHERE ID_ZONA = " + zona.getIdZona();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		zonas.add(prepStmt);
		prepStmt.executeQuery();
	}
}
