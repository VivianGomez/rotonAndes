package vos;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

public class Producto {

////Atributos

	/**
	 * Id del producto
	 */
	@JsonProperty(value="idProducto")
	private Long idProducto;
	
	
	@JsonProperty(value= "personalizable")
	private boolean personalizable;
	
	
	@JsonProperty(value= "nombre")
	private String nombre;
	
	@JsonProperty(value="traduccionDescripcion")
	private String traduccionDescripcion;
	
	@JsonProperty(value= "tiempoPreparacion")
	private double tiempoPreparacion;
	
	@JsonProperty(value= "descripcion")
	private String descripcion;
	
	@JsonProperty(value= "idCategoria")
	private Long idCategoria;
	
	@JsonProperty(value= "idTipoComida")
	private Long idTipoComida;

	/**
	 * Metodo constructor de la clase Producto
	 * <b>post: </b> Crea el producto con los valores que entran como parametro
	 * @param idProducto
	 * @param personalizable
	 * @param nombre
	 * @param traduccionDescripcion
	 * @param tiempoPreparacion
	 * @param descripcion
	 */
	public Producto(@JsonProperty(value="idProducto") Long idProducto, @JsonProperty(value= "personalizable") boolean personalizable, @JsonProperty(value= "nombre") String nombre, 	@JsonProperty(value="traduccionDescripcion") String traduccionDescripcion, @JsonProperty(value= "tiempoPreparacion") double tiempoPreparacion, @JsonProperty(value= "descripcion") String descripcion, @JsonProperty(value= "idCategoria") Long idCategoria, @JsonProperty(value= "idTipoComida") Long idTipoComida){ 
		super();
		this.idProducto= idProducto;
		this.personalizable= personalizable;
		this.nombre= nombre;
		this.traduccionDescripcion= traduccionDescripcion;
		this.tiempoPreparacion= tiempoPreparacion;
		this.descripcion=descripcion;
		this.idCategoria = idCategoria;
		this.idTipoComida = idTipoComida;
	}
	
	
	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public boolean isPersonalizable() {
		return personalizable;
	}

	public void setPersonalizable(boolean personalizable) {
		this.personalizable = personalizable;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTraduccionDescripcion() {
		return traduccionDescripcion;
	}

	public void setTraduccionDescripcion(String traduccionDescripcion) {
		this.traduccionDescripcion = traduccionDescripcion;
	}

	public double getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoPreparacion(double tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Long getIdCategoria() {
		return idCategoria;
	}


	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}


	public Long getIdTipoComida() {
		return idTipoComida;
	}


	public void setIdTipoComida(Long idTipoComida) {
		this.idTipoComida = idTipoComida;
	}
	
	
}
