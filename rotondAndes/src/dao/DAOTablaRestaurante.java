
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
public class DAOTablaRestaurante {


	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAORestaurante
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaRestaurante() {
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
	 * Metodo que, usando la conexion a la base de datos, saca todas las restaurantes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM RESTAURANTE;
	 * @return Arraylist con las restaurantes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Restaurante> darRestaurantes() throws SQLException, Exception {
		ArrayList<Restaurante> Restaurantes = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long nit = rs.getLong("NIT");
			String nombre = rs.getString("NOMBRE");
			String especialidad = rs.getString("ESPECIALIDAD");
			Integer totalProductosDisponibles = rs.getInt("TOTALPRODUCTOSDISPONIBLES");
			String tipoComida = rs.getString("TIPOCOMIDA");
			String paginaWebURL = rs.getString("PAGINAWEBURL");
			Long idZona = rs.getLong("ID_ZONA");
			Restaurantes.add(new Restaurante(nit, nombre, especialidad, totalProductosDisponibles, tipoComida, paginaWebURL,idZona));
		}
		return Restaurantes;
	}

	
	/**
	 * Metodo que busca el Restaurante con el nombre que entra como parametro.
	 * @param nombre - nombre del Restaurante a buscar
	 * @return Restaurante encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Restaurante buscarRestaurantePorNombre(String nombre) throws SQLException, Exception 
	{
		Restaurante Restaurante = null;

		String sql = "SELECT * FROM RESTAURANTE WHERE NOMBRE ='" + nombre + "'";
System.out.println("aquii");
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long nit = rs.getLong("NIT");
			String nombre2 = rs.getString("NOMBRE");
			String especialidad = rs.getString("ESPECIALIDAD");
			Integer totalProductosDisponibles = rs.getInt("TOTALPRODUCTOSDISPONIBLES");
			String tipoComida = rs.getString("TIPOCOMIDA");
			String paginaWebURL = rs.getString("PAGINAWEBURL");
			Long idZona = rs.getLong("ID_ZONA");

			Restaurante = new Restaurante(nit, nombre2, especialidad, totalProductosDisponibles, tipoComida, paginaWebURL,idZona);
		}

		return Restaurante;
	}
	
	
	
	/**
	 * Metodo que busca el Restaurante con el  nit que entra como parametro.
	 * @param nit - nit del Restaurante a buscar
	 * @return Restaurante encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Restaurante buscarRestaurantePorNit(Long nit) throws SQLException, Exception 
	{
		Restaurante Restaurante = null;

		String sql = "SELECT * FROM RESTAURANTE WHERE NIT =" + nit;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long nit2 = rs.getLong("NIT");
			String nombre = rs.getString("NOMBRE");
			String especialidad = rs.getString("ESPECIALIDAD");
			Integer totalProductosDisponibles = rs.getInt("TOTALPRODUCTOSDISPONIBLES");
			String tipoComida = rs.getString("TIPOCOMIDA");
			String paginaWebURL = rs.getString("PAGINAWEBURL");
			Long idZona = rs.getLong("ID_ZONA");

			Restaurante = new Restaurante(nit2, nombre, especialidad, totalProductosDisponibles, tipoComida, paginaWebURL,idZona);
		}

		return Restaurante;
	}

	/**
	 * Metodo que agrega el Restaurante que entra como parametro a la base de datos.
	 * @param Restaurante - el Restaurante a agregar. Restaurante !=  null
	 * <b> post: </b> se ha agregado la Restaurante a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que el Restaurante baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el Restaurante a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addRestaurante(Restaurante Restaurante) throws SQLException, Exception {

		String sql = "INSERT INTO RESTAURANTE (NIT, NOMBRE, ESPECIALIDAD,TOTALPRODUCTOSDISPONIBLES,TIPOCOMIDA,PAGINAWEBURL,ID_ZONA) VALUES (";
		sql += Restaurante.getNit() + ",'";
		sql += Restaurante.getNombre() + "','";
		sql += Restaurante.getEspecialidad() + "',";
		sql += Restaurante.getTotalProductosDisponibles() + ",'";
		sql += Restaurante.getTipoComida() + "','";
		sql += Restaurante.getPaginaWebURL() + "',";
		sql += Restaurante.getIdZona() + ")";
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		System.out.println("no!!!");

	}
	
	/**
	 * Metodo que actualiza el Restaurante que entra como parametro en la base de datos.
	 * @param Restaurante - el Restaurante a actualizar. Restaurante !=  null
	 * <b> post: </b> se ha actualizado el Restaurante en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateRestaurante(Restaurante Restaurante) throws SQLException, Exception {

		System.out.println("dto");
		
		String sql = "UPDATE RESTAURANTE SET ";
		sql += "NOMBRE='" + Restaurante.getNombre() + "',";
		sql += "ESPECIALIDAD='" + Restaurante.getEspecialidad()+ "',";
		sql += "TOTALPRODUCTOSDISPONIBLES=" + Restaurante.getTotalProductosDisponibles()+ ",";
		sql += "TIPOCOMIDA='" + Restaurante.getTipoComida()+ "',";
		sql += "PAGINAWEBURL='" + Restaurante.getPaginaWebURL()+ "',";
		sql += "ID_ZONA='" + Restaurante.getIdZona();
		sql += " WHERE NIT = " + Restaurante.getNit();
		System.out.println(sql);


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el Restaurante que entra como parametro en la base de datos.
	 * @param Restaurante - el Restaurante a borrar. Restaurante !=  null
	 * <b> post: </b> se ha borrado el Restaurante en la base de datos en la transaction actual. pendiente que el  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteRestaurante(Restaurante Restaurante) throws SQLException, Exception {

		String sql = "DELETE FROM RESTAURANTE";
		sql += " WHERE NIT = " + Restaurante.getNit();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
