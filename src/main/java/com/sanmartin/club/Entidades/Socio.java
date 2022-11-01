package com.sanmartin.club.Entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;





@Entity
public class Socio {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String nombre;
	private String apellido;
	private Integer dni;
	private String password;
	private String mail;
	private Integer telefono;
	private Integer nombreUsuario;
	@ManyToMany
	private List<Taller> taller;

	private Integer numeroAsociado;

	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	private String direccion;

	private String sexo;

	@OneToOne
	private Foto foto;

	public Socio() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(Integer dni) {
		this.nombreUsuario = dni;
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

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public List<Taller> getTaller() {
		return taller;
	}

	public void setTaller(List<Taller> taller) {
		this.taller = taller;
	}

	public Integer getNumeroAsociado() {
		return numeroAsociado;
	}

	public void setNumeroAsociado(Integer numeroAsociado) {
		this.numeroAsociado = numeroAsociado;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	 
	
}