package com.minicolestore;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity 
public class Producto {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank(message="Ingresa el nombre del producto")
	@Size(min=3 , message="Debe tener como minimo 3 caracteres")
	@Column
	private String nombre;
	
	
	@Column
	private int cantidad;

	@Positive
	@Column
	private float precio;
	
	@NotBlank(message="Ingresa una descripcion")
	@Column
	private String descripcion;
	
	@Column
	private String nombreimg;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreimg() {
		return nombreimg;
	}

	public void setNombreimg(String nombreimg) {
		this.nombreimg = nombreimg;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cantidad, descripcion, id, nombre, nombreimg, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return cantidad == other.cantidad && Objects.equals(descripcion, other.descripcion) && id == other.id
				&& Objects.equals(nombre, other.nombre) && Objects.equals(nombreimg, other.nombreimg)
				&& Float.floatToIntBits(precio) == Float.floatToIntBits(other.precio);
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio
				+ ", descripcion=" + descripcion + ", nombreimg=" + nombreimg + "]";
	}


	
	

}
