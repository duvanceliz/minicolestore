package com.minicolestore;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message="Ingresa tu nombre")
	@Size(min=3 , message="Debe tener como minimo 3 caracteres")
	@Column
	private String nombre;
	
	@NotBlank(message="Ingresa tu apellido")
	@Size(min=3 , message="Debe tener como minimo 3 caracteres")
	@Column
	private String apellido;
	
	@NotEmpty(message="Ingresa un correo electronico ")
	@Email
	@Column
	private String email;

	@NotBlank(message="Ingresa un nombre de usuario")
	@Column
	private String username;

	@NotBlank(message="Ingresa una contraseña")
	@Pattern(regexp = "^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.]).*$", message="minimo 8 caracteres que contengan almenos"+
	" .1 caracter en minuscula"+
	" .1 caracter en mayuscula"+
	" .1 caracter especial")
	@Column
	private String password;
	
	@Transient  
	private String confpassword;
	
	@Transient 
	private boolean isAdmin;

	@Column
	private boolean enabled;
	
	@Column
	private Date fechaCreacion;
	
	@Column
	private String codigoVerificacion;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	private Set<Authority> authority;
	
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfpassword() {
		return confpassword;
	}

	public void setConfpassword(String confpassword) {
		this.confpassword = confpassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	public Set<Authority> getAuthority() {
		return authority;
	}

	public void setAuthority(Set<Authority> authority) {
		this.authority = authority;
	}



	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date date) {
		this.fechaCreacion = date;
	}

	public String getCodigoVerificacion() {
		return codigoVerificacion;
	}

	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(apellido, authority, codigoVerificacion, confpassword, email, enabled, fechaCreacion, id,
				isAdmin, nombre, password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(authority, other.authority)
				&& Objects.equals(codigoVerificacion, other.codigoVerificacion)
				&& Objects.equals(confpassword, other.confpassword) && Objects.equals(email, other.email)
				&& enabled == other.enabled && Objects.equals(fechaCreacion, other.fechaCreacion)
				&& Objects.equals(id, other.id) && isAdmin == other.isAdmin && Objects.equals(nombre, other.nombre)
				&& Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", username="
				+ username + ", password=" + password + ", confpassword=" + confpassword + ", isAdmin=" + isAdmin
				+ ", enabled=" + enabled + ", fechaCreacion=" + fechaCreacion + ", codigoVerificacion="
				+ codigoVerificacion + ", authority=" + authority + "]";
	}

	

	
	
}