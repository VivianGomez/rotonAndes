package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Espacio {
	
	@JsonProperty(value="idEspacio")
	private Long idEspacio;
	
	
	@JsonProperty(value= "acondicionamiento")
	private String acondicionamiento;
	
	
	@JsonProperty(value= "tipoEspacio")
	private String tipoEspacio;
	
	@JsonProperty(value="capacidadPublico")
	private Integer capacidadPublico;
	
	@JsonProperty(value= "aptoNecEspeciales")
	private boolean aptoNecEspeciales;
	
	@JsonProperty(value= "idZona")
	private Long idZona;
	/**
	 * Metodo constructor de la clase Espacio
	 * <b>post: </b> Crea el producto con los valores que entran como parametro
	 */
	public Espacio(@JsonProperty(value="idEspacio") Long idEspacio, @JsonProperty(value= "acondicionamiento") String acondicionamiento, @JsonProperty(value= "tipoEspacio") String tipoEspacio, 	@JsonProperty(value="capacidadPublico") Integer capacidadPublico, @JsonProperty(value= "aptoNecEspeciales") boolean aptoNecEspeciales, @JsonProperty(value= "idZona") Long idZona) {
		super();
		this.idEspacio= idEspacio;
		this.acondicionamiento= acondicionamiento;
		this.tipoEspacio= tipoEspacio;
		this.capacidadPublico= capacidadPublico;
		this.aptoNecEspeciales= aptoNecEspeciales;
		this.idZona= idZona;
		
	}
	/**
	 * @return the idEspacio
	 */
	public Long getIdEspacio() {
		return idEspacio;
	}
	/**
	 * @param idEspacio the idEspacio to set
	 */
	public void setIdEspacio(Long idEspacio) {
		this.idEspacio = idEspacio;
	}
	/**
	 * @return the acondicionamiento
	 */
	public String getAcondicionamiento() {
		return acondicionamiento;
	}
	/**
	 * @param acondicionamiento the acondicionamiento to set
	 */
	public void setAcondicionamiento(String acondicionamiento) {
		this.acondicionamiento = acondicionamiento;
	}
	/**
	 * @return the tipoEspacio
	 */
	public String getTipoEspacio() {
		return tipoEspacio;
	}
	/**
	 * @param tipoEspacio the tipoEspacio to set
	 */
	public void setTipoEspacio(String tipoEspacio) {
		this.tipoEspacio = tipoEspacio;
	}
	/**
	 * @return the capacidadPublico
	 */
	public Integer getCapacidadPublico() {
		return capacidadPublico;
	}
	/**
	 * @param capacidadPublico the capacidadPublico to set
	 */
	public void setCapacidadPublico(Integer capacidadPublico) {
		this.capacidadPublico = capacidadPublico;
	}
	/**
	 * @return the aptoNecEspeciales
	 */
	public boolean isAptoNecEspeciales() {
		return aptoNecEspeciales;
	}
	/**
	 * @param aptoNecEspeciales the aptoNecEspeciales to set
	 */
	public void setAptoNecEspeciales(boolean aptoNecEspeciales) {
		this.aptoNecEspeciales = aptoNecEspeciales;
	}
	public Long getIdZona() {
		return idZona;
	}
	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}
}
