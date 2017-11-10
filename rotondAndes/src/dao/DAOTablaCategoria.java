package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Categoria;

public class DAOTablaCategoria {
	
	/**
	 * Arraylist de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> categorias;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;	

	/**
	 * Metodo constructor que crea DAOTablaCategoria
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaCategoria() {
		categorias = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan en el arreglo de categorias
	 * <b>post: </b> Todos los recurso del arreglo de categorias han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : categorias){
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
	 * Metodo que, usando la conexion a la base de datos, saca todos los categorias de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM categoria;
	 * @return Arraylist con los categorias de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Categoria> darCategorias() throws SQLException, Exception {
		ArrayList<Categoria> categories = new ArrayList<Categoria>();

		String sql = "SELECT * FROM CATEGORIA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		categorias.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idCategoria = rs.getLong("ID_CATEGORIA");
			String categoria= rs.getString("CATEGORIA");

			categories.add(new Categoria(idCategoria, categoria));
		}
		return categories;
	}


	
	
	/**
	 * Metodo que busca el categoria con el id que entra como parametro.
	 * @param idcategoria - Id del categoria a buscar
	 * @return categoria encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Categoria buscarCategoriaPorId(Long idCategoria) throws SQLException, Exception 
	{
		Categoria categoria = null;

		String sql = "SELECT * FROM CATEGORIA WHERE ID_CATEGORIA =" + idCategoria;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		categorias.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long idCategoria2 = rs.getLong("ID_CATEGORIA");
			String categoria2= rs.getString("CATEGORIA");

			categoria = new Categoria(idCategoria2, categoria2);
		}

		return categoria;
	}
	
	
	public Categoria buscarCategoriaPorNombre(String nombre) throws SQLException, Exception 
	{
		Categoria categoria = null;

		String sql = "SELECT * FROM CATEGORIA WHERE CATEGORIA = '" + nombre + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		categorias.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long idCategoria2 = rs.getLong("ID_CATEGORIA");
			String categoria2= rs.getString("CATEGORIA");

			categoria = new Categoria(idCategoria2, categoria2);
		}

		return categoria;
	}

	/**
	 * Metodo que agrega el categoria que entra como parametro a la base de datos.
	 * @param categoria - la categoria a agregar. categoria !=  null
	 * <b> post: </b> se ha agregado la categoria a la base de datos en la transaction actual. pendiente que el master
	 * haga commit para que la categoria baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la categoria a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCategoria(Categoria categoria) throws SQLException, Exception {

		String sql = "INSERT INTO CATEGORIA (ID_CATEGORIA, CATEGORIA) VALUES (";
		sql += categoria.getIdCategoria() + ",'";
		sql += categoria.getCategoria() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		categorias.add(prepStmt);
		prepStmt.executeQuery();

	}
}
