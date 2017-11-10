package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IngresoZonaPorAdministrador {

	@JsonProperty(value= "idAdministrador")
	private Long idAdministrador;
	
	@JsonProperty(value= "zona")
	private Zona zona;

	/**
	 * @param idAdministrador
	 * @param zona
	 */
	public IngresoZonaPorAdministrador(@JsonProperty(value= "idAdministrador") Long idAdministrador, @JsonProperty(value= "zona") Zona zona) {
		super();
		this.idAdministrador = idAdministrador;
		this.zona = zona;
	}

	/**
	 * @return the idAdministrador
	 */
	public Long getIdAdministrador() {
		return idAdministrador;
	}

	/**
	 * @param idAdministrador the idAdministrador to set
	 */
	public void setIdAdministrador(Long idAdministrador) {
		this.idAdministrador = idAdministrador;
	}

	/**
	 * @return the zona
	 */
	public Zona getZona() {
		return zona;
	}

	/**
	 * @param zona the zona to set
	 */
	public void setZona(Zona zona) {
		this.zona = zona;
	}
}
