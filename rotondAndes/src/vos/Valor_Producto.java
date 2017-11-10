package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Valor_Producto {
	@JsonProperty(value="id_Producto")
	private Long id_Producto;
	
	@JsonProperty(value= "id_Restaurante")
	private Long id_Restaurante;
	
	@JsonProperty(value= "costo")
	private Integer costo;
	
	@JsonProperty(value= "precio")
	private Integer precio;
	
	@JsonProperty(value= "cantidad")
	private Integer cantidad;
	
	/**
	 * Metodo constructor de la clase id_Producto
	 * <b>post: </b> Crea la categoria con los valores que entran como parametro
	 */
	public Valor_Producto(@JsonProperty(value="id_Producto") Long id_Producto,@JsonProperty(value="id_Restaurante") Long id_Restaurante,@JsonProperty(value="costo") Integer costo, @JsonProperty(value="precio") Integer precio,@JsonProperty(value="cantidad") Integer cantidad ) {
		super();
		this.id_Producto= id_Producto;
		this.id_Restaurante = id_Restaurante;
		this.costo = costo;
		this.precio = precio;
		this.cantidad = cantidad;
    }

	public Long getId_Producto() {
		return id_Producto;
	}

	public void setId_Producto(Long id_Producto) {
		this.id_Producto = id_Producto;
	}

	public Long getId_Restaurante() {
		return id_Restaurante;
	}

	public void setId_Restaurante(Long id_Restaurante) {
		this.id_Restaurante = id_Restaurante;
	}

	public Integer getCosto() {
		return costo;
	}

	public void setCosto(Integer costo) {
		this.costo = costo;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}