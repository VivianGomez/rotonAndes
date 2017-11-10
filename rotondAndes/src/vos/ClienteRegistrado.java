package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ClienteRegistrado {

	@JsonProperty(value= "id")
	private Long id;
	
	@JsonProperty(value= "nombre")
	private String nombre;
	
	@JsonProperty(value= "correoElectronico")
	private String correoElectronico;
	
	@JsonProperty(value= "banco")
	private String banco;

	public ClienteRegistrado(@JsonProperty(value= "id") Long id, @JsonProperty(value= "nombre") String nombre, @JsonProperty(value= "correoElectronico") String correoElectronico, @JsonProperty(value= "banco") String banco) {
		
		this.id = id;
		this.nombre = nombre;
		this.correoElectronico = correoElectronico;
		this.banco = banco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}
	
	
}
