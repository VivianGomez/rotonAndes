package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IngresoRestaurantesPorAdministrador {

	@JsonProperty(value= "idAdministrador")
	private Long idAdministrador;
	
	@JsonProperty(value= "restaurante")
	private Restaurante restaurante;

	/**
	 * @param idAdministrador
	 * @param restaurante
	 */
	public IngresoRestaurantesPorAdministrador(@JsonProperty(value= "idAdministrador") Long idAdministrador, @JsonProperty(value= "restaurante") Restaurante restaurante) {
		super();
		this.idAdministrador = idAdministrador;
		this.restaurante = restaurante;
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
	 * @return the restaurante
	 */
	public Restaurante getRestaurante() {
		return restaurante;
	}

	/**
	 * @param restaurante the restaurante to set
	 */
	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
	
	
	
}
