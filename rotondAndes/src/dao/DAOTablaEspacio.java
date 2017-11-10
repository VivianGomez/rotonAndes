package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Espacio;

public class DAOTablaEspacio {
	/**
	 * Arraylist de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> espacios;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;	

	/**
	 * Metodo constructor que crea DAOTablaEspacio
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaEspacio() {
		espacios = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de espacios
	 * <b>post: </b> Todos los recurso del arreglo de espacios han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : espacios){
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
	 * Metodo que, usando la conexion a la base de datos, saca todos los espacios de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM ESPACIO;
	 * @return Arraylist con los espacios de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Espacio> darEspacios() throws SQLException, Exception {
		ArrayList<Espacio> spaces = new ArrayList<Espacio>();

		String sql = "SELECT * FROM ESPACIO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		espacios.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idEspacio = rs.getLong("ID_ESPACIO");
			String acondicionamiento= rs.getString("ACONDICIONAMIENTO");
			String tipoEspacio = rs.getString("TIPO_ESPACIO");
			Integer capacidadPublico= rs.getInt("CAPACIDAD");
			boolean aptoNecEspeciales = rs.getBoolean("APTO_NEC_ESPECIALES");
			Long idZona = rs.getLong("ID_ZONA");


			spaces.add(new Espacio(idEspacio, acondicionamiento, tipoEspacio, capacidadPublico, aptoNecEspeciales, idZona));
		}
		return spaces;
	}


	
	
	/**
	 * Metodo que busca el espacio con el id que entra como parametro.
	 * @param idEspacio - Id del espacio a buscar
	 * @return espacio encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Espacio buscarEspacioPorId(Long idEspacio) throws SQLException, Exception 
	{
		Espacio espacio = null;

		String sql = "SELECT * FROM ESPACIO WHERE ID_ESPACIO =" + idEspacio;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		espacios.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long idEspacio2 = rs.getLong("ID_ESPACIO");
			String acondicionamiento= rs.getString("ACONDICIONAMIENTO");
			String tipoEspacio = rs.getString("TIPO_ESPACIO");
			Integer capacidadPublico= rs.getInt("CAPACIDAD");
			boolean aptoNecEspeciales = rs.getBoolean("APTO_NEC_ESPECIALES");
			Long idZona = rs.getLong("ID_ZONA");


			espacio = new Espacio(idEspacio2, acondicionamiento, tipoEspacio, capacidadPublico, aptoNecEspeciales, idZona);
		}

		return espacio;
	}

	/**
	 * Metodo que agrega el espacio que entra como parametro a la base de datos.
	 * @param espacio - la espacio a agregar. espacio !=  null
	 * <b> post: </b> se ha agregado la espacio a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que la espacio baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la espacio a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addEspacio(Espacio espacio) throws SQLException, Exception {

		String sql = "INSERT INTO ESPACIO (ID_ESPACIO, ACONDICIONAMIENTO, TIPO_ESPACIO, CAPACIDAD, APTO_NEC_ESPACIALES, ID_ZONA) VALUES (";
		sql += espacio.getIdEspacio() + ",'";
		sql += espacio.getAcondicionamiento() + "','";
		sql += espacio.getTipoEspacio() + "',";
		sql += espacio.getCapacidadPublico() + ",";
		sql += espacio.isAptoNecEspeciales() + ",";
		sql += espacio.getIdZona() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		espacios.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el espacio que entra como parametro en la base de datos.
	 * @param espacio - la espacio a actualizar. espacio !=  null
	 * <b> post: </b> se ha actualizado la espacio en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el espacio.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateEspacio(Espacio espacio) throws SQLException, Exception {
		
		String sql = "UPDATE ESPACIO SET ";
		sql += "ACONDICIONAMIENTO='" + espacio.getAcondicionamiento()+ "',";
		sql += "TIPO_ESPACIO='" + espacio.getTipoEspacio()+ "',";
		sql += "CAPACIDAD=" + espacio.getCapacidadPublico() + ",";
		sql += "ID_ZONA=" + espacio.getIdZona();
		sql += " WHERE ID_ESPACIO = " + espacio.getIdEspacio();
		System.out.println(sql);


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		espacios.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el espacio que entra como parametro en la base de datos.
	 * @param espacio - la espacio a borrar. espacio !=  null
	 * <b> post: </b> se ha borrado la espacio en la base de datos en la transaction actual. pendiente que el  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la espacio.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteEspacio(Espacio espacio) throws SQLException, Exception {

		String sql = "DELETE FROM ESPACIO";
		sql += " WHERE ID_ESPACIO = " + espacio.getIdEspacio();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		espacios.add(prepStmt);
		prepStmt.executeQuery();
	}
}
