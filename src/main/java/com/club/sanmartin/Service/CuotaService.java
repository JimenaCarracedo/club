package com.club.sanmartin.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.club.sanmartin.Entidades.Cuotas;
import com.club.sanmartin.Entidades.Eventos;
import com.club.sanmartin.Entidades.Foto;
import com.club.sanmartin.Entidades.Socio;
import com.club.sanmartin.Entidades.Taller;
import com.club.sanmartin.ErrorService.ErrorServicio;
import com.club.sanmartin.Repository.CuotasRepository;
import com.club.sanmartin.Repository.SocioRepository;
import com.club.sanmartin.Repository.TallerRepository;


@Service
public class CuotaService {
	@Autowired
	private CuotasRepository cuotasRepository;
	@Autowired
	private SocioRepository socioRepository;
	@Autowired
	private TallerRepository tallerRepository;

	@Autowired
	private FotoService fService;

	public CuotaService() {
	}

	@Transactional
	public void create(List<Eventos> eventos, MultipartFile foto, Date fecha, Socio socio,
			Taller taller) throws ErrorServicio {

		Cuotas nueva = new Cuotas();

		nueva.setTaller(taller);
		nueva.setSocio(socio);
		nueva.setFecha(fecha);

		Foto imagen = fService.crear(foto);
		socio.setFoto(imagen);

		cuotasRepository.save(nueva);
	}

	@Transactional
	public List<Cuotas> showAll() {
		return cuotasRepository.findAll();
	}

	@Transactional
	public void edit(String id, List<Eventos> eventos, MultipartFile foto, Date fecha, Socio socio,
			Taller taller) throws ErrorServicio {
		Cuotas edit = cuotasRepository.findById(id).get();

		validate(eventos, foto, fecha, socio, taller);
		edit.setTaller(taller);
		edit.setSocio(socio);
		edit.setFecha(fecha);

		Foto imagen = fService.crear(foto);
		socio.setFoto(imagen);

		cuotasRepository.save(edit);
	}

	@Transactional
	public void delete(String id) {
		cuotasRepository.deleteById(id);
	}

	@Transactional
	public Optional<Cuotas> findById(String id) {
		return cuotasRepository.findById(id);
	}

	@Transactional
	public List<Cuotas> SearchByUser(String id) {
		
		Optional<Socio> socio = socioRepository.findById(id);
		return cuotasRepository.SearchByUser(socio);
	}

	@Transactional
	public List<Cuotas> SearchByOs(String id) {
		Optional<Taller> taller = tallerRepository.findById(id);
		return cuotasRepository.SearchByTaller(taller);
	}

	@Transactional
	public List<Cuotas> SearchByDate(Date fecha) {
		return cuotasRepository.SearchByDate(fecha);
	}

	public void validate(List<Eventos> eventos , MultipartFile foto, Date fecha, Socio socio,
			Taller taller) throws ErrorServicio {
		if (eventos.isEmpty() == true || eventos == null) {
			throw new ErrorServicio("El evento no se encuentra");
		}
		if (foto == null) {
			throw new ErrorServicio("Sin foto cargada");
		}
		if (fecha == null) {
			throw new ErrorServicio("Sin fecha cargada");
		}
		if (socio == null) {
			throw new ErrorServicio("Socio vacio");
		}
		if (taller == null) {
			throw new ErrorServicio("Taller inexistente");
		}
	}

}

