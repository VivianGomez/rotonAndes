
package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa un Restaurante
 * @author vn.gomez_kk.penaranda
 */
public class Restaurante {

	//// Atributos

	/**
	 * Nit del restaurante
	 */
	@JsonProperty(value="nit")
	private Long nit;

	/**
	 * Nombre del restaurante
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Especialidad del restaurante
	 */
	@JsonProperty(value="especialidad")
	private String especialidad;

	/**
	 * totalProductosDisponibles del restaurante
	 */
	@JsonProperty(value="totalProductosDisponibles")
	private Integer totalProductosDisponibles;

	/**
	 * tipo de comida del restaurante
	 */
	@JsonProperty(value="tipoComida")
	private String tipoComida;


	/**
	 *URL de la pagina web del restaurante
	 */
	@JsonProperty(value="paginaWebURL")
	private String paginaWebURL;
	

	@JsonProperty(value="idZona")
	private Long idZona;

	/**
	 * Metodo constructor de la clase Restaurante
	 * <b>post: </b> Crea el restaurante con los valores que entran como parametro
	 * @param nit - nit del restaurante.
	 * @param nombre - nombre del restaurante.
	 * @param especialidad - especialidad delrestaurante.
	 * @param totalProductosDisponibles - totalProductosDisponibles del restaurante.
	 * @param tipoComida - tipoComida delrestaurante.
	 * @param paginaWebURL - paginaWebURL del restaurante.
	 */
	public Restaurante(@JsonProperty(value="nit")Long nit, @JsonProperty(value="nombre")String nombre,@JsonProperty(value="especialidad")String especialidad,@JsonProperty(value="totalProductosDisponibles")Integer totalProductosDisponibles,@JsonProperty(value="tipoComida")String tipoComida,@JsonProperty(value="paginaWebURL")String paginaWebURL,@JsonProperty(value="idZona")Long idZona) {
		super();
		this.nit = nit;
		this.nombre= nombre;
		this.especialidad = especialidad;
		this.totalProductosDisponibles = totalProductosDisponibles;
		this.tipoComida = tipoComida;
		this.paginaWebURL = paginaWebURL;
		this.idZona = idZona;
	}

	/**
	 * Metodo getter del atributo nit
	 * @return nit del Restaurante
	 */
	public Long getNit() {
		return nit;
	}
	

	/**
	 * Metodo setter del atributo nit <b>post: </b> El nit del restaurante ha sido
	 * cambiado con el valor que entra como parametro
	 * @param nit - nit del restaurante
	 */
	public void setNit(Long nit) {
		this.nit = nit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public Integer getTotalProductosDisponibles() {
		return totalProductosDisponibles;
	}

	public void setTotalProductosDisponibles(Integer totalProductosDisponibles) {
		this.totalProductosDisponibles = totalProductosDisponibles;
	}

	public String getTipoComida() {
		return tipoComida;
	}

	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}

	public String getPaginaWebURL() {
		return paginaWebURL;
	}

	public void setPaginaWebURL(String paginaWebURL) {
		this.paginaWebURL = paginaWebURL;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	
}
