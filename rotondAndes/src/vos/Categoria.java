package vos;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

public class Categoria {
	
	
	@JsonProperty(value="idCategoria")
	private Long idCategoria;
	
	
	@JsonProperty(value= "categoria")
	private String categoria;
	
//	@JsonProperty(value= "productos")
//	private Set<Producto> productos=new HashSet<Producto>();
	
	
	/**
	 * Metodo constructor de la clase Categoria
	 * <b>post: </b> Crea la categoria con los valores que entran como parametro
	 */
	public Categoria(@JsonProperty(value="idCategoria") Long idCategoria, @JsonProperty(value= "categoria") String categoria) {
		super();
		this.idCategoria= idCategoria;
		this.categoria= categoria;
		
	}

	/**
	 * @return the idCategoria
	 */
	public Long getIdCategoria() {
		return idCategoria;
	}

	/**
	 * @param idCategoria the idCategoria to set
	 */
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
}
