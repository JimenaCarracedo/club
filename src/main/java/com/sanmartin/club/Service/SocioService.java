package com.sanmartin.club.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;



import com.sanmartin.club.Entidades.Foto;
import com.sanmartin.club.Entidades.Socio;
import com.sanmartin.club.Entidades.Taller;

import com.sanmartin.club.ErrorService.ErrorServicio;
import com.sanmartin.club.Repository.SocioRepository;


@Service
public class SocioService implements UserDetailsService {
	@Autowired
	private SocioRepository repositorio;

	@Autowired
	private CuotaService cuoService;

	@Autowired
	private FotoService fService;

	@Transactional
	public void create(ModelMap model, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam Integer dni, @RequestParam String password, @RequestParam String mail,
			@RequestParam Integer telefono, @RequestParam List<Taller> taller,
			@RequestParam Integer numeroAsociado,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento, @RequestParam String direccion,
			@RequestParam String sexo, MultipartFile foto, @RequestParam String clave2) throws ErrorServicio {

		validate(nombre, apellido, dni, password, mail, telefono, taller, numeroAsociado, fechaNacimiento, direccion,
				sexo, foto, clave2);

		Socio socio = new Socio();
		boolean bool = validateMail(mail, socio);
		boolean bool2 = validateDni(dni, socio);
		if (bool && bool2) {

			socio.setApellido(apellido);
			socio.setNombre(nombre);
			socio.setDni(dni);
			String encriptada = new BCryptPasswordEncoder().encode(password.toString());
			socio.setPassword(encriptada);
			socio.setMail(mail);
			socio.setTelefono(telefono);
			socio.setTaller(taller);
			socio.setNumeroAsociado(numeroAsociado);
			socio.setFechaNacimiento(fechaNacimiento);
			socio.setDireccion(direccion);
			socio.setSexo(sexo);
			Foto imagen = fService.crear(foto);
			socio.setFoto(imagen);
			socio.setNombreUsuario(dni);
			repositorio.save(socio);
		} else {
			if (!bool) {
				if (!bool2) {
					throw new ErrorServicio("El dni y el mail que esta ingresando ya existen");
				} else {
					throw new ErrorServicio("el mail ya existe");
				}
			}
			if (!bool2) {
				throw new ErrorServicio("el dni ya existe");

			}
		}
	}

	@Transactional
	public void edit(ModelMap model, String id, String nombre, String apellido, Integer dni, String password,
			String mail, Integer telefono, List<Taller> taller, Integer numeroAsociado,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento, String direccion, String sexo,
			MultipartFile foto, String clave2) throws ErrorServicio {
		{

			validate(nombre, apellido, dni, password, mail, telefono, taller, numeroAsociado, fechaNacimiento,
					direccion, sexo, foto, clave2);

			Optional<Socio> rta = repositorio.findById(id);
			if (rta.isPresent()) {

				Socio socio = rta.get();
				boolean bool = validateMail(mail, socio);
				boolean bool2 = validateDni(dni, socio);
				if (bool && bool2) {

					socio.setApellido(apellido);
					socio.setNombre(nombre);
					socio.setDni(dni);
					String encriptada = new BCryptPasswordEncoder().encode(password.toString());
					socio.setPassword(encriptada);
					socio.setMail(mail);
					socio.setTelefono(telefono);
					socio.setTaller(taller);
					socio.setNumeroAsociado(numeroAsociado);
					socio.setFechaNacimiento(fechaNacimiento);
					socio.setDireccion(direccion);
					socio.setSexo(sexo);
					socio.setNombreUsuario(dni);
					Foto imagen = fService.editar(socio.getFoto().getId(), foto);
					socio.setFoto(imagen);

					repositorio.save(socio);
				} else {
					if (!bool) {
						if (!bool2) {
							throw new ErrorServicio("El dni y el mail que esta ingresando ya existen");
						} else {
							throw new ErrorServicio("el mail ya existe");
						}
					}
					if (!bool2) {
						throw new ErrorServicio("el dni ya existe");
					}
				}

			} else {

				throw new ErrorServicio("No se pudo encontrar el usuario");
			}

		}
	}

	public Socio searchById(String id) throws ErrorServicio {

		if (id == null || id.isEmpty()) {
			throw new ErrorServicio("El parametro de id a buscar no puede estar vacio");
		}

		Optional<Socio> rta = repositorio.findById(id);
		if (rta.isPresent()) {

			Socio socio = rta.get();
			return socio;
		} else {

			throw new ErrorServicio("No se pudo encontrar al usuario con el id indicado ");

		}

	}

	

	@Transactional(readOnly = true)
	public List<Socio> showAll() throws ErrorServicio {

		List<Socio> lista = repositorio.findAll();

		if (lista.isEmpty()) {

			throw new ErrorServicio("la lista esta vacia");

		} else {

			return lista;

		}

	}

	@Transactional(readOnly = true)
	public List<Socio> searchByName(String nombre, String apellido) throws ErrorServicio {

		if (nombre.isEmpty() || nombre == null) {
			throw new ErrorServicio(" el nombre no puede estar vacio");
		}

		if (apellido.isEmpty() || apellido == null) {
			throw new ErrorServicio(" el apellido no puede estar vacio");
		}
		List<Socio> lista = repositorio.buscarNombre(nombre, apellido);

		if (lista.isEmpty()) {
			throw new ErrorServicio("no se encontro usuario con ese nombre y apellido");

		} else {

			return lista;
		}

	}

	@Transactional(readOnly = true)
	public Socio searchBydni(Integer dni) throws ErrorServicio {

		if (dni == null) {
			throw new ErrorServicio("el dni no puede estar vacio  ");
		} else if (dni.toString().length() < 11) {
			throw new ErrorServicio("el formato es incorrecto");

		}

		List<Socio> lista = repositorio.buscarDni(dni);

		if (lista.isEmpty()) {
			throw new ErrorServicio("no existe socio con ese dni");
		} else {
			return lista.get(0);
		}
	}

	@Transactional(readOnly = true)
	public List<Socio> searchByTaller(String nombre) throws ErrorServicio {

		if (nombre.isEmpty() || nombre == null) {
			throw new ErrorServicio(" el nombre no puede estar vacio");

		}
		List<Socio> lista = repositorio.buscarTaller(nombre);

		if (lista.isEmpty()) {
			throw new ErrorServicio("no se encontro ningun socio perteneciente a ese taller");

		} else {
			return lista;
		}

	}

	public void validate(String nombre, String apellido, Integer dni, String password, String mail, Integer telefono,
			List<Taller> taller, Integer numeroAsociado, Date fechaNacimiento, String direccion, String sexo,
			MultipartFile foto, String clave2) throws ErrorServicio {

		if (nombre.isEmpty()) {

			throw new ErrorServicio(" el nombre no puede estar vacio");
		} else if (nombre == null) {
			throw new ErrorServicio("el nombre no puede ser nulo");
		}
		if (apellido.isEmpty()) {
			throw new ErrorServicio(" el apellido no puede estar vacio");
		} else if (apellido == null) {
			throw new ErrorServicio(" el apellido no puede ser nulo");

		}

		if (password.toString().isEmpty()) {
			throw new ErrorServicio(" la clave  no puede estar vacia");

		} else if (password == null) {
			throw new ErrorServicio(" la clave  no puede estar nula");

		}
		if (clave2.isEmpty()) {
			throw new ErrorServicio(" la clave  no puede estar vacia");

		} else if (clave2 == null) {
			throw new ErrorServicio(" la clave  no puede estar nula");

		}

		if (!password.equals(clave2)) {
			throw new ErrorServicio("las claves no pueden ser distintas");
		}

		if (mail.isEmpty()) {
			throw new ErrorServicio(" el mail no puede estar vacio");

		} else if (mail == null) {
			throw new ErrorServicio(" el mail no puede estar nulo");
		}

		if (direccion.isEmpty()) {
			throw new ErrorServicio(" la clave  no puede estar vacia");

		} else if (direccion == null) {
			throw new ErrorServicio(" la clave  no puede estar nula");

		}

		if (telefono == null) {
			throw new ErrorServicio("el tefono no puede estar vacio o ");

		} else if (telefono.toString().length() < 1) {
			throw new ErrorServicio("el tefono no puede ser menor de 11 digitos ");

		}
		if (dni == null) {
			throw new ErrorServicio("el dni no puede estar vacio o ");
		} else if (dni.toString().length() < 1) {
			throw new ErrorServicio("el dni no puede ser menor de 11 digitos ");

		}

		if (foto == null) {
			throw new ErrorServicio("la foto  no puede estar vacia ");

		}
		if (taller.isEmpty()) {
			throw new ErrorServicio(" la seccion obra social  no puede estar vacia");

		}

		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Integer dni = Integer.parseInt(username);
		Socio socio = repositorio.buscarDni(dni).get(0);
		if (socio != null) {
			List<GrantedAuthority> permisos = new ArrayList<>();

			GrantedAuthority permiso1 = new SimpleGrantedAuthority("ROLE_SOCIO");
			permisos.add(permiso1);

			if (socio.getNombre().equalsIgnoreCase("ADMIN")) {
				GrantedAuthority permiso2 = new SimpleGrantedAuthority("ROLE_ADMIN");
				permisos.add(permiso2);

			}

			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("sociosession", socio);

			User user = new User(socio.getDni().toString(), socio.getPassword(), permisos);
			return user;
		} else {
			return null;
		}

	}

	public boolean validateMail(String mail, Socio socio) {
		if(socio.getMail().equals(mail)){
			return true;
		} else {
			List<Socio> lista = repositorio.findAll();

			if (!lista.isEmpty()) {
				List<String> listaMails = new ArrayList<String>();

				for (Socio aux : lista) {
					listaMails.add(aux.getMail());
				}

				if (listaMails.contains(mail)) {
					return false;

				} else {
					return true;
				}
			}

		}
		return true;

	}

	public boolean validateDni(Integer dni, Socio socio) {

		if (dni.equals(socio.getDni())) {
			return true;
		} else {
			List<Socio> lista = repositorio.findAll();

			if (!lista.isEmpty()) {
				List<Integer> listaDni = new ArrayList<Integer>();

				for (Socio aux : lista) {
					listaDni.add(aux.getDni());
				}

				if (listaDni.contains(dni)) {
					return false;

				} else {
					return true;
				}
			}

		}
		return true;

	}
	@Transactional
	public void delete(String id) throws ErrorServicio {

		if (id.isEmpty() || id == null) {
			throw new ErrorServicio(" el id del usuario a modificar no puede estar vacio");
		}

		Optional<Socio> rta = repositorio.findById(id);
		if (rta.isPresent()) {

			Socio socio = rta.get();
			repositorio.delete(socio);
		} else {
			throw new ErrorServicio("No se pudo encontrar el usuario");
		}

	}
	
}
