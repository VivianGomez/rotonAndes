package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Usuario;

public class DAOTablaUsuario {


	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOusuario
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaUsuario() {
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
	 * Metodo que, usando la conexion a la base de datos, saca todas las usuarios de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM usuario;
	 * @return Arraylist con las usuarios de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Usuario> darUsuarios() throws SQLException, Exception {
		ArrayList<Usuario> clientesRegistrados = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO";

		System.out.println("SACANDO INFO DE USUARIOS...");
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long identificacion = rs.getLong("ID_USUARIO");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			clientesRegistrados.add(new Usuario(identificacion, nombre, correo, rol));
		}
		return clientesRegistrados;
	}
	
	
	public ArrayList<Usuario> darUsuariosCliente() throws SQLException, Exception {
		ArrayList<Usuario> clientesRegistrados = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO WHERE ROL = 'Cliente'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long identificacion = rs.getLong("ID_USUARIO");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			clientesRegistrados.add(new Usuario(identificacion, nombre, correo, rol));
		}
		return clientesRegistrados;
	}

	
	/**
	 * Metodo que busca el usuario con el nombre que entra como parametro.
	 * @param nombre - nombre del usuario a buscar
	 * @return usuario encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Usuario buscarUsuarioPorNombre(String nombre) throws SQLException, Exception 
	{
		Usuario usuario = null;

		String sql = "SELECT * FROM USUARIO WHERE NOMBRE ='" + nombre + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long identificacion = rs.getLong("ID_USUARIO");
			String nombre2 = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");

			usuario = new Usuario(identificacion, nombre2, correo, rol);
		}

		return usuario;
	}
	
	
	
	/**
	 * Metodo que busca el usuario con el  nit que entra como parametro.
	 * @param id - nit del usuario a buscar
	 * @return usuario encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Usuario buscarUsuarioPorId(Long id) throws SQLException, Exception 
	{
		Usuario usuario = null;

		String sql = "SELECT * FROM USUARIO WHERE ID_USUARIO =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long identificacion = rs.getLong("ID_USUARIO");
			String nombre2 = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");

			usuario = new Usuario(identificacion, nombre2, correo, rol);
		}

		return usuario;
	}

	/**
	 * Metodo que agrega el usuario que entra como parametro a la base de datos.
	 * @param usuario - el usuario a agregar. usuario !=  null
	 * <b> post: </b> se ha agregado la usuario a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que el usuario baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el usuario a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "INSERT INTO USUARIO (ID_USUARIO, NOMBRE, CORREO, ROL) VALUES (";
		sql += usuario.getId() + ",'";
		sql += usuario.getNombre() + "','";
		sql += usuario.getCorreoElectronico() + "','";
		sql += usuario.getRol() + "')";

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el usuario que entra como parametro en la base de datos.
	 * @param usuario - el usuario a actualizar. usuario !=  null
	 * <b> post: </b> se ha actualizado el usuario en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el usuario.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateUsuario(Usuario usuario) throws SQLException, Exception {

		System.out.println("dto");
		
		String sql = "UPDATE USUARIO SET ";
		sql += "NOMBRE= '" + usuario.getNombre() + "',";
		sql += "CORREO='" + usuario.getCorreoElectronico() + "',";
		sql += "ROL= '" + usuario.getRol() + "'";
		sql += " WHERE ID_USUARIO = " + usuario.getId();
		

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el usuario que entra como parametro en la base de datos.
	 * @param usuario - el usuario a borrar. usuario !=  null
	 * <b> post: </b> se ha borrado el usuario en la base de datos en la transaction actual. pendiente que el  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el usuario.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "DELETE FROM USUARIO";
		sql += " WHERE ID_USUARIO = " + usuario.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
}
