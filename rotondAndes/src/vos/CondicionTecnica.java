package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CondicionTecnica {
	
	@JsonProperty(value="idCondicion")
	private Long idCondicion;
	
	
	@JsonProperty(value= "condicion")
	private String condicion;
	
	@JsonProperty(value= "idEspacio")
	private Long idEspacio;
	
	/**
	 * Metodo constructor de la clase Condicion
	 * <b>post: </b> Crea la Condicion con los valores que entran como parametro
	 */
	public CondicionTecnica(@JsonProperty(value="idCondicion") Long idCondicion, @JsonProperty(value= "condicion") String condicion, @JsonProperty(value= "idEspacio") Long idEspacio ) {
		super();
		this.idCondicion= idCondicion;
		this.condicion= condicion;
		this.idEspacio= idEspacio;
		
	}

	/**
	 * @return the idCondicion
	 */
	public Long getIdCondicion() {
		return idCondicion;
	}

	/**
	 * @param idCondicion the idCondicion to set
	 */
	public void setIdCondicion(Long idCondicion) {
		this.idCondicion = idCondicion;
	}

	/**
	 * @return the Condicion
	 */
	public String getCondicion() {
		return condicion;
	}

	/**
	 * @param Condicion the Condicion to set
	 */
	public void setCondicion(String Condicion) {
		this.condicion = Condicion;
	}

	public Long getIdEspacio() {
		return idEspacio;
	}

	public void setIdEspacio(Long idEspacio) {
		this.idEspacio = idEspacio;
	}
}
