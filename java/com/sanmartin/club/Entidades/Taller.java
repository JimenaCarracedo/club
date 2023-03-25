package com.sanmartin.club.Entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class Taller {@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String nombre;
	private Integer codigo;
	private Long telefono;
	private String direccion;
	private Double arancel;
	private String mail;

	@ManyToMany
	private List<Socio> socio;

	public Taller() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Long getTelefono() {
		return telefono;
	}

	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Double getArancel() {
		return arancel;
	}

	public void setArancel(Double arancel) {
		this.arancel = arancel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public List<Socio> getSocio() {
		return socio;
	}

	public void setSocio(List<Socio> socio) {
		this.socio = socio;
	}

	@Override
	public String toString() {
		return "Taller [id=" + id + ", nombre=" + nombre + ", codigo=" + codigo + ", telefono=" + telefono
				+ ", direccion=" + direccion + ", arancel=" + arancel + ", mail=" + mail + ", socio=" + socio + "]";
	}

}
