package com.sanmartin.club.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanmartin.club.Entidades.Reservas;
import com.sanmartin.club.ErrorService.ErrorServicio;
import com.sanmartin.club.Repository.ReservasRepository;

@Service
public class ReservasService {
	@Autowired
	ReservasRepository reservasRepository;

	@Transactional
	public void validate(String nombre, String actividad) throws ErrorServicio {
		if (nombre.isEmpty() || nombre == null) {
			throw new ErrorServicio("Nombre vacio");
		}
		if (actividad.isEmpty() || actividad == null) {
			throw new ErrorServicio("Sin evento");
		}
	}

	@Transactional
	public void create(String nombre, String actividad) throws ErrorServicio {
		validate(nombre, actividad);
		Reservas reservas = new Reservas();
		reservas.setNombre(nombre);
		reservas.setActividad(actividad);

		reservasRepository.save(reservas);
	}

	@Transactional
	public void edit(String id, String nombre, String actividad) throws ErrorServicio {
		validate(nombre, actividad);
		Optional<Reservas> rta = reservasRepository.findById(id);

		if (rta.isPresent()) {
			Reservas reservas = rta.get();
			reservas.setNombre(nombre);
			reservas.setActividad(actividad);
			reservasRepository.save(reservas);
		}

	}

	@Transactional(readOnly = true)
	public Reservas findById(String id) {
		Optional<Reservas> rta = reservasRepository.findById(id);
		if (rta.isPresent()) {
			Reservas reservas = rta.get();
			return reservas;
		}
		return null;
	}

	@Transactional
	public void delete(String id) {
		reservasRepository.deleteById(id);
	}

	@Transactional
	public List<Reservas> showAll() {
		return reservasRepository.findAll();
	}
}

