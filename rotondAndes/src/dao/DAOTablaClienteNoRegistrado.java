package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ClienteNoRegistrado;
import vos.ClienteNoRegistrado;

public class DAOTablaClienteNoRegistrado {

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOClienteNoRegistrado
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaClienteNoRegistrado() {
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
	 * Metodo que, usando la conexion a la base de datos, saca todas las ClienteNoRegistrados de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM ClienteNoRegistrado;
	 * @return Arraylist con las ClienteNoRegistrados de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ClienteNoRegistrado> darClientesNoRegistrados() throws SQLException, Exception {
		ArrayList<ClienteNoRegistrado> clientesRegistrados = new ArrayList<ClienteNoRegistrado>();

		String sql = "SELECT * FROM CLIENTE_NO_REGISTRADO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long ID = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO_ELECTRONICO");
			String banco = rs.getString("BANCO");
			clientesRegistrados.add(new ClienteNoRegistrado(ID, nombre, correo, banco));
		}
		return clientesRegistrados;
	}

	
	/**
	 * Metodo que busca el ClienteNoRegistrado con el nombre que entra como parametro.
	 * @param nombre - nombre del ClienteNoRegistrado a buscar
	 * @return ClienteNoRegistrado encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ClienteNoRegistrado buscarClienteNoRegistradoPorNombre(String nombre) throws SQLException, Exception 
	{
		ClienteNoRegistrado clienteNoRegistrado = null;

		String sql = "SELECT * FROM CLIENTE_NO_REGISTRADO WHERE NOMBRE ='" + nombre + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long ID = rs.getLong("ID");
			String nombre2 = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO_ELECTRONICO");
			String banco = rs.getString("BANCO");

			clienteNoRegistrado = new ClienteNoRegistrado(ID, nombre2, correo, banco);
		}

		return clienteNoRegistrado;
	}
	
	
	
	/**
	 * Metodo que busca el ClienteNoRegistrado con el  nit que entra como parametro.
	 * @param id - nit del ClienteNoRegistrado a buscar
	 * @return ClienteNoRegistrado encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ClienteNoRegistrado buscarClienteNoRegistradoPorId(Long id) throws SQLException, Exception 
	{
		ClienteNoRegistrado ClienteNoRegistrado = null;

		String sql = "SELECT * FROM CLIENTE_NO_REGISTRADO WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long ID = rs.getLong("ID");
			String nombre2 = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO_ELECTRONICO");
			String banco = rs.getString("BANCO");

			ClienteNoRegistrado = new ClienteNoRegistrado(ID, nombre2, correo, banco);
		}

		return ClienteNoRegistrado;
	}

	/**
	 * Metodo que agrega el ClienteNoRegistrado que entra como parametro a la base de datos.
	 * @param ClienteNoRegistrado - el ClienteNoRegistrado a agregar. ClienteNoRegistrado !=  null
	 * <b> post: </b> se ha agregado la ClienteNoRegistrado a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que el ClienteNoRegistrado baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el ClienteNoRegistrado a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addClienteNoRegistrado(ClienteNoRegistrado ClienteNoRegistrado) throws SQLException, Exception {

		String sql = "INSERT INTO CLIENTE_NO_REGISTRADO (ID, NOMBRE, CORREO_ELECTRONICO, BANCO) VALUES (";
		sql += ClienteNoRegistrado.getId() + ",'";
		sql += ClienteNoRegistrado.getNombre() + "','";
		sql += ClienteNoRegistrado.getCorreoElectronico() + "','";
		sql += ClienteNoRegistrado.getBanco() + "')";

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el ClienteNoRegistrado que entra como parametro en la base de datos.
	 * @param ClienteNoRegistrado - el ClienteNoRegistrado a actualizar. ClienteNoRegistrado !=  null
	 * <b> post: </b> se ha actualizado el ClienteNoRegistrado en la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el ClienteNoRegistrado.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateClienteNoRegistrado(ClienteNoRegistrado ClienteNoRegistrado) throws SQLException, Exception {

		System.out.println("dto");
		
		String sql = "UPDATE CLIENTE_NO_REGISTRADO SET ";
		sql += "NOMBRE= '" + ClienteNoRegistrado.getNombre() + "',";
		sql += "CORREO_ELECTRONICO='" + ClienteNoRegistrado.getCorreoElectronico() + "',";
		sql += "BANCO='" + ClienteNoRegistrado.getBanco() + "'";
		sql += " WHERE ID = " + ClienteNoRegistrado.getId();
		

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el ClienteNoRegistrado que entra como parametro en la base de datos.
	 * @param ClienteNoRegistrado - el ClienteNoRegistrado a borrar. ClienteNoRegistrado !=  null
	 * <b> post: </b> se ha borrado el ClienteNoRegistrado en la base de datos en la transaction actual. pendiente que el  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el ClienteNoRegistrado.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteClienteNoRegistrado(ClienteNoRegistrado ClienteNoRegistrado) throws SQLException, Exception {

		String sql = "DELETE FROM CLIENTE_NO_REGISTRADO";
		sql += " WHERE ID = " + ClienteNoRegistrado.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
}
