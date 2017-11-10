package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Tiene_Producto {
	@JsonProperty(value="id_Menu")
	private Long id_Menu;
	
	
	@JsonProperty(value= "id_Producto")
	private Long id_Producto;
	
	@JsonProperty(value= "id_categoria")
	private Long id_categoria;
	
	/**
	 * Metodo constructor de la clase id_Producto
	 * <b>post: </b> Crea la categoria con los valores que entran como parametro
	 */
	public Tiene_Producto(@JsonProperty(value="id_Menu") Long id_Menu,@JsonProperty(value="id_Producto") Long id_Producto,@JsonProperty(value="id_categoria") Long id_categoria) {
		super();
		this.id_Menu= id_Menu;
		this.id_Producto = id_Producto;
		this.id_categoria = id_categoria;
	}

	public Long getId_Menu() {
		return id_Menu;
	}

	public void setId_Menu(Long id_Menu) {
		this.id_Menu = id_Menu;
	}

	public Long getId_Producto() {
		return id_Producto;
	}

	public void setId_Producto(Long id_Producto) {
		this.id_Producto = id_Producto;
	}

	public Long getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(Long id_categoria) {
		this.id_categoria = id_categoria;
	}


	
}
