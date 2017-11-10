package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingrediente_Producto {
	@JsonProperty(value="id_Ingrediente")
	private Long id_Ingrediente;
	
	
	@JsonProperty(value= "id_Producto")
	private Long id_Producto;
	
	/**
	 * Metodo constructor de la clase id_Producto
	 * <b>post: </b> Crea la categoria con los valores que entran como parametro
	 */
	public Ingrediente_Producto(@JsonProperty(value="id_Ingrediente") Long id_Ingrediente,@JsonProperty(value="id_Producto") Long id_Producto) {
		super();
		this.id_Ingrediente= id_Ingrediente;
		this.id_Producto = id_Producto;
	}

	public Long getId_Ingrediente() {
		return id_Ingrediente;
	}

	public void setId_Ingrediente(Long id_Ingrediente) {
		this.id_Ingrediente = id_Ingrediente;
	}

	public Long getId_Producto() {
		return id_Producto;
	}

	public void setId_Producto(Long id_Producto) {
		this.id_Producto = id_Producto;
	}
	
}
