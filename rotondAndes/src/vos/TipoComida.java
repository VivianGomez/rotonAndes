package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class TipoComida {
	
	
	@JsonProperty(value="idTipoComida")
	private Long idTipoComida;
	
	
	@JsonProperty(value= "clasificacion")
	private String clasificacion;
	/**
	 * Metodo constructor de la clase TipoComida
	 * <b>post: </b> Crea el producto con los valores que entran como parametro
	 */
	public TipoComida(@JsonProperty(value="idTipoComida") Long idTipoComida, @JsonProperty(value= "clasificacion") String clasificacion){
	    super();
		this.idTipoComida= idTipoComida;
		this.clasificacion= clasificacion;
		
	}
	
	/**
	 * @return the idTipoComida
	 */
	public Long getIdTipoComida() {
		return idTipoComida;
	}
	/**
	 * @param idTipoComida the idTipoComida to set
	 */
	public void setIdTipoComida(Long idTipoComida) {
		this.idTipoComida = idTipoComida;
	}
	/**
	 * @return the clasificacion
	 */
	public String getClasificacion() {
		return clasificacion;
	}
	/**
	 * @param clasificacion the clasificacion to set
	 */
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
}
