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
import com.sanmartin.club.Entidades.Socio;
import com.sanmartin.club.Entidades.Taller;
import com.sanmartin.club.ErrorService.ErrorServicio;
import com.sanmartin.club.Repository.CuotasRepository;
import com.sanmartin.club.Repository.EventosRepository;
import com.sanmartin.club.Repository.SocioRepository;
import com.sanmartin.club.Repository.TallerRepository;


@Service
public class TallerService {
	@Autowired
	private TallerRepository tallerRepository;

	@Autowired
	private EventosRepository eventosRepository;

	@Autowired
	private SocioRepository socioRepository;

	@Autowired
	private CuotasRepository cuotaRepository;

	/* CONSTRUCTOR POR DEFECTO */
	public TallerService() {
	}

	/* CRUD ALTA */
	@Transactional
	public void create(String nombre, Integer codigo, Long telefono, String direccion, Double arancel, String mail)
			throws ErrorServicio {


		Taller taller = new Taller();
		taller.setNombre(nombre);
		taller.setCodigo(codigo);
		taller.setTelefono(telefono);
		taller.setDireccion(direccion);
		taller.setArancel(arancel);
		taller.setMail(mail);

		tallerRepository.save(taller);

	}

	/* CRUD ELIMINACION */
	@Transactional
	public void delete(String id) throws ErrorServicio {

		if (id.isEmpty() || id == null) {
			throw new ErrorServicio("El id no puede estar vacio o ser nulo");
		}

		Optional<Taller> respuesta = tallerRepository.findById(id);

		if (respuesta.isPresent()) {
			Taller taller = respuesta.get();
			List<Eventos> eventos = eventosRepository.findAll();
			
			List<Socio> socio = socioRepository.findAll();
			List<Cuotas> cuotas = cuotaRepository.findAll();
			if (eventos != null) {
				for (Eventos aux : eventos) {
					if (aux.getTaller() != null) {
						if (aux.getTaller().equals(taller)) {
							aux.setTaller(null);
						}
					}
				}
			}

			

			if (socio != null) {
				for (Socio aux : socio) {
					if (aux.getTaller() != null) {
						Iterator it = aux.getTaller().iterator();
						while (it.hasNext()) {
							Taller opc = (Taller) it.next();
							if (opc.equals(taller))
								;
							it.remove();
						}
					}
				}
			}

			if (cuotas != null) {
				for (Cuotas aux : cuotas) {
					if (aux.getTaller() != null) {
						if (aux.getTaller().equals(taller)) {
							aux.setTaller(null);
						}
					}
				}
			}

			tallerRepository.delete(taller);
		} else {
			throw new ErrorServicio("El taller no funciona en el club");
		}

	}

	/* CRUD MODIFICACION */
	@Transactional
	public void edit(String id, String nombre, Integer codigo, Long telefono, String direccion, Double arancel,
			String mail) throws ErrorServicio {

		if (id.isEmpty() || id == null) {
			throw new ErrorServicio("El id no puede estar vacio o ser nulo");
		}

		

		Optional<Taller> respuesta = tallerRepository.findById(id);

		if (respuesta.isPresent()) {

			Taller taller = respuesta.get();

			

			taller.setNombre(nombre);
			taller.setCodigo(codigo);
			taller.setTelefono(telefono);
			taller.setDireccion(direccion);
			taller.setArancel(arancel);
			taller.setMail(mail);

			tallerRepository.save(taller);

		}
	}

	/* CRUD DE TODAS LAS OBRAS SOCIALES */
	@Transactional(readOnly = true)
	public List<Taller> showAll() throws ErrorServicio {

		List<Taller> lista = tallerRepository.findAll();

		if (lista.isEmpty()) {
			return lista;
		}

		return lista;
	}

	/* TRAER OS POR ID */
	@Transactional(readOnly = true)
	public Taller findById(String id) throws ErrorServicio {

		if (id == null || id.isEmpty()) {
			throw new ErrorServicio("El taller no funciona en el club");
		}

		Optional<Taller> respuesta = tallerRepository.findById(id);
		if (respuesta.isPresent()) {
			Taller taller = respuesta.get();
			return taller;
		} else {
			throw new ErrorServicio("No se pudo encontrar al socio con el id indicado");
		}
	}

	/* TRAER USUARIOS DE OS */
	@Transactional(readOnly = true)
	public List<Socio> showSocioTallerByName(String nombre) throws ErrorServicio {

		Taller listaTaller = tallerRepository.buscarTallerNombre(nombre).get(0);
		
		if (listaTaller.getSocio().isEmpty()) {
			throw new ErrorServicio("No tiene socios concurrentes");
		} else {
			return listaTaller.getSocio();
		}

	}

	/* BUSCAR OS VINCULADA A DNI DE USUARIOS */
	@Transactional(readOnly = true)
	public List<Taller> searchByDni(Integer dni) throws ErrorServicio {

		if (dni == null || dni.intValue() == 0) {
			throw new ErrorServicio("El DNI no puede ser nulo ni 0(cero)");
		}

		List<Taller> lista = tallerRepository.buscarOSUsuarioDni(dni);

		if (lista.isEmpty()) {
			throw new ErrorServicio("El socio que busca no se encuentra vinculado a nuestros talleres");
		}

		return lista;

	}

	/* VALIDACION NOMBRE */
	public Boolean checkNombreAvailability(String nombre, Taller taller) {

		if (nombre.equals(taller.getNombre())) {
			return true;
		} else {
			List<Taller> lista = tallerRepository.findAll();

			if (!lista.isEmpty()) {
				List<String> listaNombre = new ArrayList<String>();

				for (Taller aux : lista) {
					listaNombre.add(aux.getNombre());
				}

				if (listaNombre.contains(nombre)) {
					return false;

				} else {
					return true;
				}
			}

		}
		return true;
	}

	/* VALIDACION CODIGO */
	public Boolean checkCodigoAvailability(Integer codigo, Taller taller) {

		if (codigo.equals(taller.getCodigo())) {
			return true;
		} else {
			List<Taller> lista = tallerRepository.findAll();

			if (!lista.isEmpty()) {
				List<Integer> listaCodigo = new ArrayList<Integer>();

				for (Taller aux : lista) {
					listaCodigo.add(aux.getCodigo());
				}

				if (listaCodigo.contains(codigo)) {
					return false;

				} else {
					return true;
				}
			}

		}

		return true;

	}

	/* VALIDACION TELEFONO */
	public Boolean checkTelefonoAvailability(Long telefono, Taller taller) {

		if (telefono.equals(taller.getTelefono())) {
			return true;
		} else {
			List<Taller> lista = tallerRepository.findAll();

			if (!lista.isEmpty()) {
				List<Long> listaTelefono = new ArrayList<Long>();

				for (Taller aux : lista) {
					listaTelefono.add(aux.getTelefono());
				}

				if (listaTelefono.contains(telefono)) {
					return false;

				} else {
					return true;
				}
			}

		}

		return true;

	}

	/* VALIDACION DIRECCION */
	public Boolean checkDireccionAvailability(String direccion, Taller taller) {

		if (direccion.equals(taller.getDireccion())) {
			return true;
		} else {
			List<Taller> lista = tallerRepository.findAll();

			if (!lista.isEmpty()) {
				List<String> listaDireccion = new ArrayList<String>();

				for (Taller aux : lista) {
					listaDireccion.add(aux.getDireccion());
				}

				if (listaDireccion.contains(direccion)) {
					return false;

				} else {
					return true;
				}
			}

		}

		return true;

	}

	/* VALIDACION MAIL */
	public Boolean checkMailAvailability(String mail, Taller taller) {

		if (mail.equals(taller.getMail())) {
			return true;
		} else {
			List<Taller> lista = tallerRepository.findAll();

			if (!lista.isEmpty()) {
				List<String> listaMail = new ArrayList<String>();

				for (Taller aux : lista) {
					listaMail.add(aux.getDireccion());
				}

				if (listaMail.contains(mail)) {
					return false;

				} else {
					return true;
				}
			}

		}

		return true;

	}
}
