
package dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicacion
 * @author vn.gomez_kk.penaranda
 */
public class DAOTablaReserva {


	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOReserva
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaReserva() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
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
	 * Metodo que, usando la conexion a la base de datos, saca todas las reservas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM RESERVA;
	 * @return Arraylist con las reservas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Reserva> darReservas() throws SQLException, Exception {
		ArrayList<Reserva> Reservas = new ArrayList<Reserva>();

		String sql = "SELECT * FROM RESERVA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idReserva = rs.getLong("ID_RESERVA");
			Integer numeroComensales = rs.getInt("NUMERO_COMENSALES");
			Date fecha = rs.getDate("FECHA");
			Long idCliente = rs.getLong("ID_CLIENTE");
			Long idEspacio = rs.getLong("ID_ESPACIO");
			Reservas.add(new Reserva(idReserva, numeroComensales, fecha, idCliente, idEspacio));
		}
		return Reservas;
	}


	
	
	/**
	 * Metodo que busca la Reserva con el id que entra como parametro.
	 * @param idReserva - Id de la Reserva a buscar
	 * @return Reserva encontrada
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Reserva buscarReservaPorId(Long idReserva) throws SQLException, Exception 
	{
		Reserva Reserva = null;

		String sql = "SELECT * FROM RESERVA WHERE ID_RESERVA =" + idReserva;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long idReserva2 = rs.getLong("ID_RESERVA");
			Integer numeroComensales = rs.getInt("NUMERO_COMENSALES");
			Date fecha = rs.getDate("FECHA");
			Long idCliente = rs.getLong("ID_CLIENTE");
			Long idEspacio = rs.getLong("ID_ESPACIO");


			Reserva = new Reserva(idReserva2, numeroComensales, fecha, idCliente, idEspacio);
		}

		return Reserva;
	}

	/**
	 * Metodo que agrega la Reserva que entra como parametro a la base de datos.
	 * @param Reserva - la Reserva a agregar. Reserva !=  null
	 * <b> post: </b> se ha agregado la Reserva a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que la Reserva baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la Reserva a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addReserva(Reserva Reserva) throws SQLException, Exception {

		String sql = "INSERT INTO RESERVA (ID_RESERVA, FECHA, NUMERO_COMENSALES, ID_CLIENTE, ID_ESPACIO) VALUES (";
		sql += Reserva.getIdReserva() + ",";
		sql += Reserva.getNumeroComensales() + ",'";
		sql += Reserva.getFecha() + "',";
		sql += Reserva.getIdCliente() + ",";
		sql += Reserva.getIdEspacio() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza la Reserva que entra como parametro en la base de datos.
	 * @param Reserva - la Reserva a actualizar. Reserva !=  null
	 * <b> post: </b> se ha actualizado la Reserva en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la Reserva.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateReserva(Reserva Reserva) throws SQLException, Exception {

		String sql = "UPDATE RESERVA SET ";
		sql += "NUMEROCOMENSALES=" + Reserva.getNumeroComensales() + ",";
		sql += "FECHA='" + Reserva.getFecha() + "',";
		sql += "ID_CLIENTE=" + Reserva.getIdCliente() + ",";
		sql += "ID_ESPACIO=" + Reserva.getIdEspacio();
		sql += " WHERE ID_RESERVA = " + Reserva.getIdReserva();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina la Reserva que entra como parametro en la base de datos.
	 * @param Reserva - la Reserva a borrar. Reserva !=  null
	 * <b> post: </b> se ha borrado la Reserva en la base de datos en la transaction actual. pendiente que el  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la Reserva.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteReserva(Reserva Reserva) throws SQLException, Exception {

		String sql = "DELETE FROM RESERVA";
		sql += " WHERE ID_RESERVA = " + Reserva.getIdReserva();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
