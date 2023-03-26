package com.club.sanmartin.Controladores;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.club.sanmartin.Entidades.Foto;
import com.club.sanmartin.Entidades.Role;
import com.club.sanmartin.Entidades.Taller;

import lombok.Data;
@Data
public class RegisterDto {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String nombre;
	private String apellido;
	private String dni;
	private String password;
	private String mail;
	private Integer telefono;
	private String nombreUsuario;
	private Role roles;
	private Integer numeroAsociado;
	@ManyToMany
	private List<Taller> taller;
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	private String direccion;

	private String sexo;
	
	public List<Taller> getTaller() {
		return taller;
	}
	public void setTaller(List<Taller> taller) {
		this.taller = taller;
	}
	@OneToOne
	private Foto foto;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
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
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public Role getRoles() {
		return roles;
	}
	public void setRoles(Role roles) {
		this.roles = roles;
	}
	
}
