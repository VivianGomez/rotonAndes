package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IngresoClientesPorAdministrador {

	@JsonProperty(value= "idAdministrador")
	private Long idAdministrador;
	
	@JsonProperty(value= "cliente")
	private ClienteRegistrado cliente;

	/**
	 * @param cliente
	 * @param restaurante
	 * @param zona
	 */
	public IngresoClientesPorAdministrador(@JsonProperty(value= "idAdministrador") Long idAdministrador, @JsonProperty(value= "cliente") ClienteRegistrado cliente){
		super();
		this.cliente = cliente;
		this.idAdministrador = idAdministrador;
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
	 * @return the idCliente
	 */
	public ClienteRegistrado getCliente() {
		return cliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setCliente(ClienteRegistrado idCliente) {
		this.cliente = idCliente;
	}
	
	

	
	
	
	
	
}
