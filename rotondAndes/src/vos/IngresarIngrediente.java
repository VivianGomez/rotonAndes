package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IngresarIngrediente {
	@JsonProperty(value="idUsuarioRestaurante")
	private Long idUsuarioRestaurante;
	
	
	@JsonProperty(value= "ingrediente")
	private Ingrediente ingrediente;
	
	@JsonProperty(value="idProducto")
	private Long idProducto;
	
	/**
	 * Metodo constructor de la clase IngresarIngrediente
	 * <b>post: </b> Crea la categoria con los valores que entran como parametro
	 */
	public IngresarIngrediente(@JsonProperty(value="idUsuarioRestaurante") Long idUsuarioRestaurante, @JsonProperty(value= "ingrediente") Ingrediente ingrediente,@JsonProperty(value="idProducto") Long idProducto) {
		super();
		this.idUsuarioRestaurante= idUsuarioRestaurante;
		this.ingrediente = ingrediente;
		this.idProducto = idProducto;
	}

	public Long getIdUsuarioRestaurante() {
		return idUsuarioRestaurante;
	}

	public void setIdUsuarioRestaurante(Long idUsuarioRestaurante) {
		this.idUsuarioRestaurante = idUsuarioRestaurante;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	
}
