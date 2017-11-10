package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IngresarProducto {
	@JsonProperty(value="idUsuarioRestaurante")
	private Long idUsuarioRestaurante;
	
	
	@JsonProperty(value= "producto")
	private Producto producto;
	
	@JsonProperty(value="idMenu")
	private Long idMenu;
	
	/**
	 * Metodo constructor de la clase IngresarIngrediente
	 * <b>post: </b> Crea la categoria con los valores que entran como parametro
	 */
	public IngresarProducto(@JsonProperty(value="idUsuarioRestaurante") Long idUsuarioRestaurante, @JsonProperty(value= "producto") Producto producto,@JsonProperty(value="idMenu") Long idMenu) {
		super();
		this.idUsuarioRestaurante= idUsuarioRestaurante;
		this.producto = producto;
		this.idMenu = idMenu;
	}

	public Long getIdUsuarioRestaurante() {
		return idUsuarioRestaurante;
	}

	public void setIdUsuarioRestaurante(Long idUsuarioRestaurante) {
		this.idUsuarioRestaurante = idUsuarioRestaurante;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Long getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(Long idMenu) {
		this.idMenu = idMenu;
	}

	
	
}
