package vos;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

public class IngresarPedido {
	
	@JsonProperty(value="idRestaurante")
	private Long idRestaurante;
	
	@JsonProperty(value="idPedido")
	private Long idPedido;
	
	@JsonProperty(value="productos")
	private Set<Long> productos;
	
	/**
	 * Metodo constructor de la clase IngresarIngrediente
	 * <b>post: </b> Crea la categoria con los valores que entran como parametro
	 */
	public IngresarPedido(@JsonProperty(value="idRestaurante") Long idRestaurante, @JsonProperty(value= "idPedido") Long idPedido, @JsonProperty(value="productos") Set<Long> productos) {
		super();
		this.idRestaurante = idRestaurante;
		this.idPedido = idPedido;
		this.productos = productos;

	}


	public Long getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	

	public Long getIdPedido() {
		return idPedido;
	}


	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}


	public Set<Long> getProductos() {
		return productos;
	}

	public void setProductos(Set<Long> productos) {
		this.productos = productos;
	}
}
