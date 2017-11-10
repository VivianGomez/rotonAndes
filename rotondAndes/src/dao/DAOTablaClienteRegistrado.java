package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ClienteRegistrado;

public class DAOTablaClienteRegistrado {


	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOclienteRegistrado
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaClienteRegistrado() {
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
	 * Metodo que, usando la conexion a la base de datos, saca todas las clienteRegistrados de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM clienteRegistrado;
	 * @return Arraylist con las clienteRegistrados de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ClienteRegistrado> darClientesRegistrados() throws SQLException, Exception {
		ArrayList<ClienteRegistrado> clientesRegistrados = new ArrayList<ClienteRegistrado>();

		String sql = "SELECT * FROM CLIENTE_REGISTRADO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long identificacion = rs.getLong("IDENTIFICACION");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO_ELECTRONICO");
			String banco = rs.getString("BANCO");
			clientesRegistrados.add(new ClienteRegistrado(identificacion, nombre, correo, banco));
		}
		return clientesRegistrados;
	}

	
	/**
	 * Metodo que busca el clienteRegistrado con el nombre que entra como parametro.
	 * @param nombre - nombre del clienteRegistrado a buscar
	 * @return clienteRegistrado encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ClienteRegistrado buscarClienteRegistradoPorNombre(String nombre) throws SQLException, Exception 
	{
		ClienteRegistrado clienteRegistrado = null;

		String sql = "SELECT * FROM CLIENTE_REGISTRADO WHERE NOMBRE ='" + nombre + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long identificacion = rs.getLong("IDENTIFICACION");
			String nombre2 = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO_ELECTRONICO");
			String banco = rs.getString("BANCO");

			clienteRegistrado = new ClienteRegistrado(identificacion, nombre2, correo, banco);
		}

		return clienteRegistrado;
	}
	
	
	
	/**
	 * Metodo que busca el clienteRegistrado con el  nit que entra como parametro.
	 * @param id - nit del clienteRegistrado a buscar
	 * @return clienteRegistrado encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ClienteRegistrado buscarClienteRegistradoPorId(Long id) throws SQLException, Exception 
	{
		ClienteRegistrado clienteRegistrado = null;

		String sql = "SELECT * FROM CLIENTE_REGISTRADO WHERE IDENTIFICACION =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long identificacion = rs.getLong("IDENTIFICACION");
			String nombre2 = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO_ELECTRONICO");
			String banco = rs.getString("BANCO");

			clienteRegistrado = new ClienteRegistrado(identificacion, nombre2, correo, banco);
		}

		return clienteRegistrado;
	}

	/**
	 * Metodo que agrega el clienteRegistrado que entra como parametro a la base de datos.
	 * @param clienteRegistrado - el clienteRegistrado a agregar. clienteRegistrado !=  null
	 * <b> post: </b> se ha agregado la clienteRegistrado a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que el clienteRegistrado baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el clienteRegistrado a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addClienteRegistrado(ClienteRegistrado clienteRegistrado) throws SQLException, Exception {

		String sql = "INSERT INTO CLIENTE_REGISTRADO (IDENTIFICACION, NOMBRE, CORREO_ELECTRONICO, BANCO) VALUES (";
		sql += clienteRegistrado.getId() + ",'";
		sql += clienteRegistrado.getNombre() + "','";
		sql += clienteRegistrado.getCorreoElectronico() + "','";
		sql += clienteRegistrado.getBanco() + "')";

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el clienteRegistrado que entra como parametro en la base de datos.
	 * @param clienteRegistrado - el clienteRegistrado a actualizar. clienteRegistrado !=  null
	 * <b> post: </b> se ha actualizado el clienteRegistrado en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el clienteRegistrado.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateClienteRegistrado(ClienteRegistrado clienteRegistrado) throws SQLException, Exception {

		System.out.println("dto");
		
		String sql = "UPDATE CLIENTE_REGISTRADO SET ";
		sql += "NOMBRE= '" + clienteRegistrado.getNombre() + "',";
		sql += "CORREO_ELECTRONICO='" + clienteRegistrado.getCorreoElectronico() + "',";
		sql += "BANCO='" + clienteRegistrado.getBanco() + "'";
		sql += " WHERE IDENTIFICACION = " + clienteRegistrado.getId();
		

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el clienteRegistrado que entra como parametro en la base de datos.
	 * @param clienteRegistrado - el clienteRegistrado a borrar. clienteRegistrado !=  null
	 * <b> post: </b> se ha borrado el clienteRegistrado en la base de datos en la transaction actual. pendiente que el  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el clienteRegistrado.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteClienteRegistrado(ClienteRegistrado clienteRegistrado) throws SQLException, Exception {

		String sql = "DELETE FROM CLIENTE_REGISTRADO";
		sql += " WHERE IDENTIFICACION = " + clienteRegistrado.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
}
