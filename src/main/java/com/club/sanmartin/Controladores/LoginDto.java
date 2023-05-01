package com.club.sanmartin.Controladores;

import lombok.Data;

@Data
public class LoginDto {
    private String dni;
    private String password;
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    
}
