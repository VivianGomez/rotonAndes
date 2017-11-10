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
public class DAOTablaIngrediente {


	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOIngrediente
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaIngrediente() {
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
	 * Metodo que, usando la conexion a la base de datos, saca todos los ingredientes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM INGREDIENTE;
	 * @return Arraylist con las ingredientes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Ingrediente> darIngredientes() throws SQLException, Exception {
		ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();

		String sql = "SELECT * FROM INGREDIENTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idIngrediente = rs.getLong("ID_INGREDIENTE");
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccionDescripcion = rs.getString("TRADUCCION");
			ingredientes.add(new Ingrediente(idIngrediente, nombre, descripcion, traduccionDescripcion));
		}
		return ingredientes;
	}

	
	/**
	 * Metodo que busca el Ingrediente con el nombre que entra como parametro.
	 * @param nombre - nombre del Ingrediente a buscar
	 * @return Ingrediente encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Ingrediente buscarIngredientePorNombre(String nombre) throws SQLException, Exception 
	{
		Ingrediente Ingrediente = null;

		String sql = "SELECT * FROM INGREDIENTE WHERE NOMBRE = '" + nombre + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long idIngrediente = rs.getLong("ID_INGREDIENTE");
			String nombre2 = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccionDescripcion = rs.getString("TRADUCCION");

			Ingrediente = new Ingrediente(idIngrediente, nombre2, descripcion, traduccionDescripcion);
		}

		return Ingrediente;
	}
	
	
	
	/**
	 * Metodo que busca el Ingrediente con el  idIngrediente que entra como parametro.
	 * @param idIngrediente - idIngrediente del Ingrediente a buscar
	 * @return Ingrediente encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Ingrediente buscarIngredientePorId(Long idIngrediente) throws SQLException, Exception 
	{
		Ingrediente Ingrediente = null;

		String sql = "SELECT * FROM INGREDIENTE WHERE ID_INGREDIENTE =" + idIngrediente;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long idIngrediente2 = rs.getLong("ID_INGREDIENTE");
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccionDescripcion = rs.getString("TRADUCCION");

			Ingrediente = new Ingrediente(idIngrediente2, nombre, descripcion, traduccionDescripcion);
		}

		return Ingrediente;
	}

	/**
	 * Metodo que agrega el Ingrediente que entra como parametro a la base de datos.
	 * @param Ingrediente - el Ingrediente a agregar. Ingrediente !=  null
	 * <b> post: </b> se ha agregado la Ingrediente a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que el Ingrediente baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el Ingrediente a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addIngrediente(Ingrediente ingrediente) throws SQLException, Exception {

		String sql = "INSERT INTO INGREDIENTE (ID_INGREDIENTE, NOMBRE, DESCRIPCION, TRADUCCION) VALUES (";
		sql += ingrediente.getIdIngrediente() + ",'";
		sql += ingrediente.getNombre() + "','";
		sql += ingrediente.getDescripcion() + "','";
		sql += ingrediente.getTraduccionDescripcion() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		System.out.println(sql);

	}
	
	/**
	 * Metodo que actualiza el Ingrediente que entra como parametro en la base de datos.
	 * @param ingrediente - el Ingrediente a actualizar. Ingrediente !=  null
	 * <b> post: </b> se ha actualizado el Ingrediente en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Ingrediente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateIngrediente(Ingrediente ingrediente) throws SQLException, Exception {

		String sql = "UPDATE INGREDIENTE SET ";
		sql += "NOMBRE='" + ingrediente.getNombre() + "',";
		sql += "DESCRIPCION='" + ingrediente.getDescripcion()+ "',";
		sql += "TRADUCCION='" + ingrediente.getTraduccionDescripcion()+ "'";
		sql += " WHERE ID_INGREDIENTE = " + ingrediente.getIdIngrediente();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el Ingrediente que entra como parametro en la base de datos.
	 * @param Ingrediente - el Ingrediente a borrar. Ingrediente !=  null
	 * <b> post: </b> se ha borrado el Ingrediente en la base de datos en la transaction actual. pendiente que el  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Ingrediente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteIngrediente(Ingrediente Ingrediente) throws SQLException, Exception {

		String sql = "DELETE FROM INGREDIENTE";
		sql += " WHERE ID_INGREDIENTE = " + Ingrediente.getIdIngrediente();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
