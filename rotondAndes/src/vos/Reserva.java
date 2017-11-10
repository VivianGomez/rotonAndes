
package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa una Reserva
 * @author vn.gomez_kk.penaranda
 */
public class Reserva {

	//// Atributos

	/**
	 * Id de la reserva
	 */
	@JsonProperty(value="idReserva")
	private Long idReserva;

	/**
	 * Numero de comensales de la reserva
	 */
	@JsonProperty(value="numeroComensales")
	private Integer numeroComensales;

	/**
	 * Fecha de la reserva
	 */
	@JsonProperty(value="fecha")
	private Date fecha;
	
	@JsonProperty(value= "idCliente")
	private Long idCliente;
	
	@JsonProperty(value= "idEspacio")
	private Long idEspacio;

	/**
	 * Metodo constructor de la clase Reserva
	 * <b>post: </b> Crea la reserva con los valores que entran como parametro
	 * @param idReserva - Id de la reserva.
	 * @param numeroComensales - Numero de comensales de la reserva. numeroComensales!= null
	 * @param fecha - Fecha de la reserva.
	 */
	public Reserva(@JsonProperty(value="idReserva")Long id, @JsonProperty(value="numeroComensales")Integer numeroComensales,@JsonProperty(value="fecha")Date fecha, @JsonProperty(value= "idCliente") Long idCliente, @JsonProperty(value= "idEspacio") Long idEspacio) {
		super();
		this.idReserva = id;
		this.numeroComensales = numeroComensales;
		this.fecha = fecha;
		this.idCliente= idCliente;
		this.idEspacio= idEspacio;
	}

	/**
	 * Metodo getter del atributo idReserva
	 * @return id de la Reserva
	 */
	public Long getIdReserva() {
		return idReserva;
	}

	/**
	 * Metodo setter del atributo idReserva <b>post: </b> El idReserva de la reserva ha sido
	 * cambiado con el valor que entra como parametro
	 * @param idReserva - Id de la reserva
	 */
	public void setIdReserva(Long idReserva) {
		this.idReserva = idReserva;
	}

	/**
	 * Metodo getter del atributo numeroComensales
	 * @return numero de comensales para la reserva
	 */
	public Integer getNumeroComensales() {
		return numeroComensales;
	}

	/**
	 * Metodo setter del atributo numeroComensales <b>post: </b> El numero de comensales de la reserva ha sido
	 * cambiado con el valor que entra como parametro
	 * @param numeroComensales - Numero de comensales para la reserva
	 */
	public void setNumeroComensales(Integer numeroComensales) {
		this.numeroComensales = numeroComensales;
	}

	/**
	 * Metodo getter del atributo fecha
	 * @return fecha de la Reserva
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Metodo setter del atributo fecha <b>post: </b> La fecha de la reserva ha sido
	 * cambiada con el valor que entra como parametro
	 * @param fecha - fecha de la reserva
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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

}
