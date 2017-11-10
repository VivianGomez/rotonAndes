package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Pedido {
	@JsonProperty(value="numeroOrden")
	private Long numeroOrden;
	
	
	@JsonProperty(value= "totalAPagar")
	private double totalAPagar;
	
	/**
	 * Metodo constructor de la clase Pedido
	 * <b>post: </b> Crea la totalAPagar con los valores que entran como parametro
	 */
	public Pedido(@JsonProperty(value="numeroOrden") Long numeroOrden, @JsonProperty(value= "totalAPagar") double totalAPagar) {
		super();
		this.numeroOrden= numeroOrden;
		this.totalAPagar= totalAPagar;
		
	}

	/**
	 * @return the numeroOrden
	 */
	public Long getNumeroOrden() {
		return numeroOrden;
	}

	/**
	 * @param numeroOrden the numeroOrden to set
	 */
	public void setNumeroOrden(Long numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	/**
	 * @return the totalAPagar
	 */
	public double getTotalAPagar() {
		return totalAPagar;
	}

	/**
	 * @param totalAPagar the totalAPagar to set
	 */
	public void setTotalAPagar(double totalAPagar) {
		this.totalAPagar = totalAPagar;
	}
}
