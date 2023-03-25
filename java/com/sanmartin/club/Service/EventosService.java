package com.sanmartin.club.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanmartin.club.Entidades.Cuotas;
import com.sanmartin.club.Entidades.Eventos;
import com.sanmartin.club.Entidades.Taller;
import com.sanmartin.club.ErrorService.ErrorServicio;
import com.sanmartin.club.Repository.CuotasRepository;
import com.sanmartin.club.Repository.EventosRepository;
import com.sanmartin.club.Repository.SocioRepository;
import com.sanmartin.club.Repository.TallerRepository;
@Service
public class EventosService {
	@Autowired
	private EventosRepository eventosRepository;

	@Autowired
	private TallerRepository tRepository;
	@Autowired
	private SocioRepository sRepository;

	@Autowired
	private CuotasRepository cRepository;

	@Transactional
	public void create(String nombre, Integer valorentrada, Taller taller) throws ErrorServicio {

		validate(nombre, valorentrada, taller);

		Eventos eventos = new Eventos();

		Boolean bool1 = checkTallerAvailability(nombre, taller, eventos);

		if (bool1) {

			eventos.setNombre(nombre);
			eventos.setValorEntrada(valorentrada);
			eventos.setTaller(taller);

			eventosRepository.save(eventos);
		} else {

			if (!bool1) {
				throw new ErrorServicio("No puede haber 2 talleres con el mismo evento");
			}

		}
	}

	@Transactional
	public void edit(String id, String nombre, Integer valorEntrada, Taller taller) throws ErrorServicio {

		if (id == null || id.isEmpty()) {
			throw new ErrorServicio("El id del evento a modificar no puede estar vacio");
		}

		validate(nombre, valorEntrada, taller);

		Optional<Eventos> rta = eventosRepository.findById(id);

		if (rta.isPresent()) {
			Eventos eventos = rta.get();

			Boolean bool1 = checkTallerAvailability(nombre, taller, eventos);

			if (bool1) {

				eventos.setNombre(nombre);
				eventos.setValorEntrada(valorEntrada);
				eventos.setTaller(taller);

				eventosRepository.save(eventos);
			} else {

				if (!bool1) {
					throw new ErrorServicio("No puede haber 2 eventos con el mismo nombre");
				}

			}

		} else {
			throw new ErrorServicio("No se pudo encontrar un evento con el id especificado");
		}

	}

	

	@Transactional(readOnly = true)
	public List<Eventos> showAll() throws ErrorServicio {

		List<Eventos> list = eventosRepository.findAll();

		if (list.isEmpty()) {
			throw new ErrorServicio("No hay eventos para mostrar");
		} else {
			return list;
		}
	}

	@Transactional(readOnly = true)
	public Eventos findById(String id) throws ErrorServicio {

		if (id == null || id.isEmpty()) {
			throw new ErrorServicio("El id del evento a buscar no puede estar vacio");
		}

		Optional<Eventos> rta = eventosRepository.findById(id);
		if (rta.isPresent()) {
			return rta.get();
		} else {
			throw new ErrorServicio("No se encontro un evento con ese id");
		}
	}

	@Transactional(readOnly = true)
	public List<Eventos> searchByName(String nombre) throws ErrorServicio {

		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El nombre del evento a buscar no puede estar vacio");
		}

		List<Eventos> list = eventosRepository.searchByName(nombre);

		if (list.isEmpty()) {
			throw new ErrorServicio("No hay eventos para mostrar");
		} else {
			return list;
		}
	}


	public void validate(String nombre, Integer valorEntrada, Taller taller) throws ErrorServicio {

		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El campo de nombre no puede estar vacio");
		}

	}

	public boolean checkTallerAvailability(String nombre, Taller taller, Eventos eventos) {

		if (nombre.equals(eventos.getNombre()) && taller.equals(eventos.getTaller())) {
			return true;
		} else {
			List<Eventos> lista = eventosRepository.searchByName(nombre);

			if (!lista.isEmpty()) {
				List<Taller> listatalleres = new ArrayList<Taller>();

				for (Eventos aux : lista) {
					listatalleres.add(aux.getTaller());
				}

				if (listatalleres.contains(taller)) {
					return false;

				} else {
					return true;
				}
			}

		}
		return true;

	}
}
