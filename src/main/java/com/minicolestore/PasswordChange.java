package com.minicolestore;

import java.util.Objects;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PasswordChange {
	
	private long id;
	
	@NotBlank(message="Ingresa una contraseña")
	@Pattern(regexp = "^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.]).*$", message="minimo 8 caracteres que contengan almenos"+
	" .1 caracter en minuscula"+
	" .1 caracter en mayuscula"+
	" .1 caracter especial")
	private String password;
	
	private String passwordConf;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConf() {
		return passwordConf;
	}

	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, password, passwordConf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordChange other = (PasswordChange) obj;
		return id == other.id && Objects.equals(password, other.password)
				&& Objects.equals(passwordConf, other.passwordConf);
	}

	@Override
	public String toString() {
		return "PasswordChange [id=" + id + ", password=" + password + ", passwordConf=" + passwordConf + "]";
	}





}
