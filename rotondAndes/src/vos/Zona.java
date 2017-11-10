package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Zona {
	@JsonProperty(value="idZona")
	private Long idZona;
	
	
	@JsonProperty(value= "nombre")
	private String nombre;
	
	/**
	 * Metodo constructor de la clase Zona
	 * <b>post: </b> Crea la categoria con los valores que entran como parametro
	 */
	public Zona(@JsonProperty(value="idZona") Long idZona, @JsonProperty(value= "nombre") String nombre) {
		super();
		this.idZona= idZona;
		this.nombre= nombre;
		
	}

	/**
	 * @return the idZona
	 */
	public Long getIdZona() {
		return idZona;
	}

	/**
	 * @param idZona the idZona to set
	 */
	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
