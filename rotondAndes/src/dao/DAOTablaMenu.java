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
public class DAOTablaMenu {


	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOMenu
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaMenu() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de recursos
	 * <b>post: </b> Todos los recursos del arreglo de recursos han sido cerrados
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
	 * Metodo que, usando la conexion a la base de datos, saca todos los menus de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM MENU;
	 * @return Arraylist con las menus de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Menu> darMenus() throws SQLException, Exception {
		ArrayList<Menu> Menus = new ArrayList<Menu>();

		String sql = "SELECT * FROM MENU";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID_MENU");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			String nombre = rs.getString("NOMBRE");
			Menus.add(new Menu(id, nombre, idRestaurante ));
		}
		return Menus;
	}

	
	/**
	 * Metodo que busca el Menu con el nombre que entra como parametro.
	 * @param nombre - nombre del Menu a buscar
	 * @return Menu encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Menu buscarMenuPorNombre(String nombre) throws SQLException, Exception 
	{
		Menu Menu = null;

		String sql = "SELECT * FROM MENU WHERE NOMBRE = '" + nombre + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long id = rs.getLong("ID_MENU");
			String nombre2 = rs.getString("NOMBRE");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");

			Menu = new Menu(id, nombre2,idRestaurante);
		}

		return Menu;
	}
	
	
	
	/**
	 * Metodo que busca el Menu con el  id que entra como parametro.
	 * @param id - id del Menu a buscar
	 * @return Menu encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Menu buscarMenuPorNit(Long id) throws SQLException, Exception 
	{
		Menu Menu = null;

		String sql = "SELECT * FROM MENU WHERE ID_MENU =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long id2 = rs.getLong("ID_MENU");
			String nombre = rs.getString("NOMBRE");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");

			Menu = new Menu(id2, nombre,idRestaurante);
		}

		return Menu;
	}

	/**
	 * Metodo que agrega el Menu que entra como parametro a la base de datos.
	 * @param Menu - el Menu a agregar. Menu !=  null
	 * <b> post: </b> se ha agregado la Menu a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que el Menu baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el Menu a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addMenu(Menu Menu) throws SQLException, Exception {

		String sql = "INSERT INTO MENU (ID_MENU, ID_RESTAURANTE, NOMBRE) VALUES (";
		sql += Menu.getId() + ",";
		sql += Menu.getIdRestaurante()+", '";
		sql += Menu.getNombre() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el Menu que entra como parametro en la base de datos.
	 * @param Menu - el Menu a actualizar. Menu !=  null
	 * <b> post: </b> se ha actualizado el Menu en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Menu.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateMenu(Menu Menu) throws SQLException, Exception {

		String sql = "UPDATE MENU SET ";
		sql += "NOMBRE= '" + Menu.getNombre()+"'";
		sql += " ID_RESTAURANTE=  " + Menu.getIdRestaurante();
		sql += " WHERE ID_MENU = " + Menu.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el Menu que entra como parametro en la base de datos.
	 * @param Menu - el Menu a borrar. Menu !=  null
	 * <b> post: </b> se ha borrado el Menu en la base de datos en la transaction actual. pendiente que el  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Menu.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteMenu(Menu Menu) throws SQLException, Exception {

		String sql = "DELETE FROM MENU";
		sql += " WHERE ID_MENU = " + Menu.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
