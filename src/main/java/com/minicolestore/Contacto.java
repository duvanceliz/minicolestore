package com.minicolestore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="contacto")
public class Contacto {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@NotBlank(message="Ingresa tu nombre")
	@Column(name="nombre")
	private String nombre;
	
	@NotBlank(message="Ingresa tu telefono")
	@Column(name="telefono")
	private String telefono;
	
	@NotBlank(message="Ingresa un email")
	@Email
	@Column(name="email")
	private String email;
	
	@NotBlank(message="Ingresa un comentario")
	@Column(name="comentario")
	private String comentario;
	public Contacto() {
		super();
	}
	public Contacto(String nombre, String telefono, String email, String comentario) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.comentario = comentario;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	@Override
	public String toString() {
		return "Contacto [nombre=" + nombre + ", telefono=" + telefono + ", email=" + email + ", comentario="
				+ comentario + "]";
	}
	
	

}
