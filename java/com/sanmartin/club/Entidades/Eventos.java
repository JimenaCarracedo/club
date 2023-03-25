package com.sanmartin.club.Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class Eventos {	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String nombre;
	private Integer valorentrada;

	@OneToOne
	private Taller taller;

	@OneToOne
	private Reservas reservas;

	public Eventos() {

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

	public Integer getValorEntrada() {
		return valorentrada;
	}

	public void setValorEntrada(Integer valorEntrada) {
		this.valorentrada = valorEntrada;
	}

	public Taller getTaller() {
		return taller;
	}

	public void setTaller(Taller taller) {
		this.taller = taller;
	}

	public Reservas getReservas() {
		return reservas;
	}

	public void setReservas(Reservas reservas) {
		this.reservas = reservas;
	}

}

