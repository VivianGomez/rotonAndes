package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Tiene_Pedido {
	
	@JsonProperty(value= "id_restaurante")
	private Long id_restaurante;
	
	@JsonProperty(value="id_pedido")
	private Long id_pedido;

	/**
	 * Metodo constructor de la clase Tiene_Pedido
	 * <b>post: </b> Crea la categoria con los valores que entran como parametro
	 */
	public Tiene_Pedido(@JsonProperty(value="id_restaurante") Long id_restaurante,@JsonProperty(value="id_pedido") Long id_pedido) {
		super();
		this.id_restaurante= id_restaurante;
		this.id_pedido = id_pedido;
	}

	public Long getId_restaurante() {
		return id_restaurante;
	}

	public void setId_restaurante(Long id_restaurante) {
		this.id_restaurante = id_restaurante;
	}

	public Long getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Long id_pedido) {
		this.id_pedido = id_pedido;
	}
	
}
