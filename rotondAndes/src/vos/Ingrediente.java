
package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa un Ingrediente
 * @author vn.gomez_kk.penaranda
 */
public class Ingrediente {

	//// Atributos

	/**
	 * Id del ingrediente
	 */
	@JsonProperty(value="idIngrediente")
	private Long idIngrediente;

	/**
	 * Nombre del ingrediente
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Descripcion del ingrediente
	 */
	@JsonProperty(value="descripcion")
	private String descripcion;
	/**
	 * Traduccion de la descripcion del ingrediente
	 */
	@JsonProperty(value="traduccionDescripcion")
	private String traduccionDescripcion;


	/**
	 * Metodo constructor de la clase Ingrediente
	 * <b>post: </b> Crea el ingrediente con los valores que entran como parametro
	 * @param idIngrediente - idIngrediente del ingrediente.
	 * @param nombre - nombre del ingrediente.
	 * @param descripcion - descripcion delingrediente.
	 * @param traduccionDescripcion - traduccion de la descripcion del ingrediente.
	 */
	public Ingrediente(@JsonProperty(value="idIngrediente")Long idIngrediente, @JsonProperty(value="nombre")String nombre,@JsonProperty(value="descripcion")String descripcion,@JsonProperty(value="traduccionDescripcion")String traduccionDescripcion) {
		super();
		this.idIngrediente = idIngrediente;
		this.nombre= nombre;
		this.descripcion = descripcion;
		this.traduccionDescripcion = traduccionDescripcion;
	}


	public Long getIdIngrediente() {
		return idIngrediente;
	}


	public void setIdIngrediente(Long idIngrediente) {
		this.idIngrediente = idIngrediente;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getTraduccionDescripcion() {
		return traduccionDescripcion;
	}


	public void setTraduccionDescripcion(String traduccionDescripcion) {
		this.traduccionDescripcion = traduccionDescripcion;
	}

}
