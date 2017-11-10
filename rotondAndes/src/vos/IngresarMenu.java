package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IngresarMenu {
	@JsonProperty(value="idUsuarioRestaurante")
	private Long idUsuarioRestaurante;
	
	@JsonProperty(value="menu")
	private Menu menu;
	
	/**
	 * Metodo constructor de la clase IngresarIngrediente
	 * <b>post: </b> Crea la categoria con los valores que entran como parametro
	 */
	public IngresarMenu(@JsonProperty(value="idUsuarioRestaurante") Long idUsuarioRestaurante, @JsonProperty(value= "menu") Menu menu) {
		super();
		this.idUsuarioRestaurante= idUsuarioRestaurante;
		this.menu = menu;
	}

	public Long getIdUsuarioRestaurante() {
		return idUsuarioRestaurante;
	}

	public void setIdUsuarioRestaurante(Long idUsuarioRestaurante) {
		this.idUsuarioRestaurante = idUsuarioRestaurante;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
}
